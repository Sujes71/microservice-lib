package es.zed.config;

import com.mongodb.MongoWriteException;
import com.rabbitmq.client.Channel;
import es.zed.common.AbstractEvent;
import es.zed.controller.AbstractAmqpController;
import es.zed.repository.EventIdRepository;
import es.zed.repository.model.EventId;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.context.annotation.PropertySource;

/**
 * AmqpController to Message listener adapter. This class is used to wrap the MessagingController an create a listener adapter that can be added to a
 * RabbitMQ listener container. This links the events reception to the controller invocation.
 */
@Slf4j
@PropertySource("classpath:amqp-config.properties")
public class AmqpControllerMessageListenerAdapter implements ChannelAwareMessageListener {

  /**
   * The name of the event consumer method in the controller.
   */
  private static final String CONSUME_METHOD = "consume";
  /**
   * The name of the publish method in the controller.
   */
  private static final String PUBLISH_METHOD = "publish";
  /**
   * The wrapped controller.
   */
  private final AbstractAmqpController<? extends AbstractEvent<?>> controller;
  /**
   * The consume methods that were found in the controller.
   */
  private final Map<String, Method> messageConsumerMethods = new HashMap<>();
  /**
   * The message converter used to serialize and deserialize events.
   */
  private final AmqpMessageConverter messageConverter;

  /**
   * EventId repository.
   */
  private final EventIdRepository eventIdRepository;

  /**
   * AmqpControllerMessageListenerAdapter constructor.
   *
   * @param controller the platform events controller to adapt.
   * @param messageConverter the platform events converter.
   * @param eventIdRepository event id repository.
   */
  public AmqpControllerMessageListenerAdapter(
      AbstractAmqpController<? extends AbstractEvent<?>> controller,
      final AmqpMessageConverter messageConverter, final EventIdRepository eventIdRepository) {
    this.controller = controller;
    this.messageConverter = messageConverter;
    this.eventIdRepository = eventIdRepository;
    initialize();
  }

  @Override
  public void onMessage(Message message, Channel channel) throws IOException {
    final AbstractEvent<?> event = (AbstractEvent) messageConverter.fromMessage(message);
    final Method method = messageConsumerMethods.get(event.getTypeId());
    log.info("Processing event: {}", event.getMessageId());
    if (method == null) {
      log.error("The MessagingController do not support events of type {}, message Id: {}", event.getTypeId(), event.getMessageId());
      channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
    } else {
      checkAlreadyProcessed(event);
      try {
        method.invoke(controller, event);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
      } catch (IllegalAccessException | InvocationTargetException e) {
        final Throwable cause = e.getCause();
        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        eventIdRepository.deleteOne(new EventId(event.getMessageId(), event.getContext(), event.getTypeId()));
        // Handle exception, do not rethrow
        log.error("Handling PokemonException consuming event {}, type {}: {}", event.getMessageId(), event.getTypeId(), cause.getMessage());
      }
    }
  }


  /**
   * Initialize the adapter. That is, cache consumer methods at the controller. This includes parameter validation (that must be a concrete event).
   * Also, publish method is validated.
   */
  private void initialize() {
    // For each public (consume) method
    for (Method method : controller.getClass().getMethods()) {
      if (Modifier.isPublic(method.getModifiers()) && method.getDeclaringClass() != Object.class) {
        final String methodName = method.getName();
        if (CONSUME_METHOD.equals(methodName)) {
          verifyAndRegisterConsumeMethod(method);
        } else if (PUBLISH_METHOD.equals(methodName)) {
          verifyPublishMethod(method);
        }
      }
    }
    if (messageConsumerMethods.size() == 0) {
      log.info("The MessagingController do not contains any consume event method");
    } else {
      log.info("The MessagingController consume events of type: {}", messageConsumerMethods.keySet().stream().collect(Collectors.joining(",")));
    }
  }

  /**
   * Register a new consume method. Method argument is validated (must be a concrete event).
   *
   * @param method the method to add.
   */
  private void verifyAndRegisterConsumeMethod(final Method method) {
    verifyConsumeMethod(method);

    try {
      final String typeId = ((AbstractEvent) method.getParameterTypes()[0].getDeclaredConstructor().newInstance()).getTypeId();
      messageConsumerMethods.put(typeId, method);
    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
      throw new IllegalArgumentException(
          "MessagingController " + CONSUME_METHOD + " method parameter type is not valid, could not create an example instance");
    }
  }

  /**
   * Verifies that consume method has the right signature to be able to consume messages in {@link AbstractAmqpController}.
   *
   * @param method method to verify.
   */
  private void verifyConsumeMethod(final Method method) {
    final String methodName = method.getName();

    if (method.getParameterCount() > 1) {
      throw new IllegalArgumentException("MessagingController ".concat(methodName).concat(" method may only have one parameter"));
    }

    final Class<?> eventClass = method.getParameterTypes()[0];

    if (!AbstractEvent.class.isAssignableFrom(eventClass)) {
      throw new IllegalArgumentException("MessagingController ".concat(methodName)
          .concat(" method parameter type must be a concrete event type extending AbstractEvent, found: " + eventClass.getName()));
    }
  }

  /**
   * Verify the given publish method that should be the one at {@link AbstractAmqpController}.
   *
   * @param method the given publish method.
   */
  private void verifyPublishMethod(final Method method) {
    try {
      if (!method.equals(AbstractAmqpController.class.getMethod("publish", AbstractEvent.class)) && !method
          .equals(AbstractAmqpController.class.getMethod("publish", AbstractEvent.class, Integer.class))) {
        throw new IllegalArgumentException("MessagingController may only content a " + PUBLISH_METHOD
            + " and it is already declared in the parent class, found one with parameters" + Arrays.asList(method.getParameterTypes()));
      }
    } catch (NoSuchMethodException e) {
      throw new IllegalArgumentException("Internal error while verifying " + PUBLISH_METHOD, e);
    }
  }


  /**
   * Checks if the event has been already processed.
   *
   * @param event the event to check
   * @return EventId
   */
  private boolean checkAlreadyProcessed(final AbstractEvent<?> event) {
    try {
      eventIdRepository.save(new EventId(event.getMessageId(), event.getContext(), event.getTypeId()));
      return true;
    } catch (MongoWriteException e) {
      if (e.getMessage().contains("E11000")) {
        throw new EventAlreadyProcessedException("Event with ID " + event.getMessageId() + " already processed", null);
      } else {
        throw e;
      }
    }
  }

  /**
   * Internal exception thrown when detected that an event has been already processed.
   */
  private static class EventAlreadyProcessedException extends RuntimeException {

    /**
     * Constructor.
     *
     * @param message exception message.
     * @param cause exception cause.
     */
    public EventAlreadyProcessedException(String message, Throwable cause) {
      super(message, cause);
    }
  }
}
