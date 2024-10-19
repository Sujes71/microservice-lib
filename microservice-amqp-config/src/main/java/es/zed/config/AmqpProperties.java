package es.zed.config;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Properties to configure AMPQ.
 */

@Getter
@Setter
@PropertySource("classpath:amqp-config.properties")
@Configuration
public class AmqpProperties {

  /**
   * Topic exchange to publish to.
   */
  @Value("${producer.exchange.name}")
  private String producerExchangeName;

  /**
   * Topic exchange to publish, durable property.
   */
  @Value("${consumer.exchange.durable}")
  private Boolean consumerExchangeDurable;

  /**
   * Topic exchange to publish, autodelete property.
   */
  @Value("${consumer.exchange.autodelete}")
  private Boolean consumerExchangeAutodelete;

  /**
   * The name of the queue this service binds to the exchange.
   */
  @Value("${consumer.queue.name}")
  private String queueName;
  /**
   * If false do not requeue messages that are rejected (they must be sent to the dead-letter exchange).
   */
  @Value("${consumer.queue.defaultRequeueRejected}")
  private Boolean defaultRequeueRejected;
  /**
   * Max number of concurrent consumers per queue.
   */
  @Value("${consumer.queue.maxConcurrentConsumers}")
  private Integer maxConcurrentConsumers;
  /**
   * Topic exchange to bind the queue to.
   */
  @Value("${consumer.exchange.name}")
  private String consumerExchangeName;
  /**
   * Topic exchange to bind the queue to.
   */
  @Value("#{'${consumer.binding.routingKeys}'.split(',')}")
  private List<String> consumerRoutingKey;

  /**
   * Setting listener acknowledge mode.
   */
  @Value("${listener.simple.acknowledgeMode}")
  private String listenerSimpleAcknowledgeMode;

}
