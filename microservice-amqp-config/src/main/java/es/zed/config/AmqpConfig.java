package es.zed.config;

import es.zed.common.spring.EventParserManager;
import es.zed.controller.AbstractAmqpController;
import es.zed.repository.EventIdRepository;
import es.zed.utils.CustomObjectMapper;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.ErrorHandler;

/**
 * Amqp config.
 */
@Slf4j
@Configuration
@ConditionalOnClass({AmqpProperties.class, CustomObjectMapper.class, TopicExchange.class, Queue.class, AmqpAdmin.class,
    ConnectionFactory.class, AmqpMessageConverter.class, AbstractAmqpController.class, EventParserManager.class})
@Import(AmqpProperties.class)
public class AmqpConfig {

  /**
   * Amqp properties.
   */
  private final AmqpProperties properties;

  /**
   * Amqp admin.
   */
  private final AmqpAdmin amqpAdmin;

  /**
   * Constructor.
   *
   * @param properties properties.
   * @param amqpAdmin amqp admin.
   */
  public AmqpConfig(AmqpProperties properties, AmqpAdmin amqpAdmin) {
    this.properties = properties;
    this.amqpAdmin = amqpAdmin;
  }

  /**
   * Events in queue bean.
   *
   * @return Events in queue bean.
   */
  @Bean(name = "queue")
  public Queue queue() {
    return QueueBuilder.durable(properties.getQueueName()).build();

  }

  /**
   * Configure exchange.
   *
   * @return exchange.
   */
  @Bean
  TopicExchange exchange() {
    return new TopicExchange(properties.getConsumerExchangeName());
  }

  /**
   * Configure bidings.
   *
   * @param queue queue.
   * @param exchange exchange.
   * @return List of bindings.
   */
  @Bean
  public List<Binding> bindings(Queue queue, TopicExchange exchange) {
    List<String> routingKeys = properties.getConsumerRoutingKey();
    List<Binding> bindings = new ArrayList<>();

    for (String routingKey : routingKeys) {
      Binding binding = BindingBuilder.bind(queue).to(exchange).with(routingKey);
      bindings.add(binding);
      amqpAdmin.declareBinding(binding);
    }

    return bindings;
  }

  /**
   * AMQP controller message listener adapter.
   *
   * @param abstractAmqpController the controller where consume methods will be created.
   * @param amqpMessageConverter the AMQP message converter (for platform event messages).
   * @param eventIdRepository event id repository.
   * @return AMQP controller message listener adapter {@link AmqpControllerMessageListenerAdapter}.
   */
  @Bean
  public AmqpControllerMessageListenerAdapter amqpControllerMessageListenerAdapter(@Autowired final AbstractAmqpController abstractAmqpController,
      @Autowired final AmqpMessageConverter amqpMessageConverter, @Autowired final EventIdRepository eventIdRepository) {
    return new AmqpControllerMessageListenerAdapter(abstractAmqpController, amqpMessageConverter, eventIdRepository);
  }

  /**
   * Rabbit template to use to send request results/responses.
   *
   * @param connectionFactory rabbitmq connection factory.
   * @param customObjectMapper Jackson object mapper to read/write json.
   * @param eventParserManager Caminos business event parser manager.
   * @return the rabbit template to use to send request results/responses the rabbit template to use to send request results/responses.
   */
  @Bean
  public RabbitTemplate requestTemplate(@Autowired ConnectionFactory connectionFactory,
      @Autowired CustomObjectMapper customObjectMapper,
      @Autowired EventParserManager eventParserManager) {
    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(new AmqpMessageConverter(eventParserManager, customObjectMapper));
    return rabbitTemplate;
  }

  /**
   * Create the platform messages event converter.
   *
   * @param customObjectMapper the configured jackson object mapper.
   * @param eventParserManager the platform events parser manager (holds all registered parsers).
   * @return the platform event messages converter.
   */
  @Bean(name = "amqpMessageConverter")
  public AmqpMessageConverter amqpMessageConverter(
      @Autowired CustomObjectMapper customObjectMapper,
      @Autowired EventParserManager eventParserManager) {
    return new AmqpMessageConverter(eventParserManager, customObjectMapper);
  }

  /**
   * Listener container for the external systems events.
   *
   * @param connectionFactory rabbitmq connection factory.
   * @param amqpControllerMessageListenerAdapter AMQP controller message listener adapter {@link AmqpControllerMessageListenerAdapter}.
   * @param simpleRabbitListenerContainerFactory factory for the container that initializes it with sensible defaults.
   * @return the queue listener container.
   */
  @Bean
  public SimpleMessageListenerContainer requestsListenerContainer(
      @Autowired final ConnectionFactory connectionFactory,
      @Autowired final AmqpControllerMessageListenerAdapter amqpControllerMessageListenerAdapter,
      @Autowired final SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory) {
    final SimpleMessageListenerContainer container = simpleRabbitListenerContainerFactory.createListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.setQueueNames(properties.getQueueName());
    container.setMessageListener(amqpControllerMessageListenerAdapter);
    container.setErrorHandler(new SimpleErrorHandler());
    container.setDefaultRequeueRejected(properties.getDefaultRequeueRejected());
    container.setMaxConcurrentConsumers(properties.getMaxConcurrentConsumers());
    container.setAcknowledgeMode(AcknowledgeMode.valueOf(properties.getListenerSimpleAcknowledgeMode()));

    return container;
  }

  /**
   * Event IDs repository.
   *
   * @param mongoTemplate Mongo template.
   * @return Event IDs repository.
   */
  @Bean
  public EventIdRepository eventIdRepository(@Autowired final MongoTemplate mongoTemplate) {
    return new EventIdRepository(mongoTemplate);
  }

  /**
   * Simple error handler that only logs the errors thrown.
   */
  public static class SimpleErrorHandler implements ErrorHandler {

    @Override
    public void handleError(Throwable t) {
      log.error(t.getMessage(), t);
    }
  }

}
