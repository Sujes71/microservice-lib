package es.zed.config;

import es.zed.common.AbstractEvent;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * Abstract kafka controller.
 * @param <T> generic.
 */
public abstract class AbstractKafkaController<T extends AbstractEvent<?>> {

  /**
   * Kafka template.
   */
  private final KafkaTemplate<String, T> kafkaTemplate;

  /**
   * Constructor.
   *
   * @param template template.
   */
  protected AbstractKafkaController(KafkaTemplate<String, T> template) {
    this.kafkaTemplate = template;
  }

  /**
   * Publish.
   *
   * @param event event.
   */
  public void publish(T event) {
    this.kafkaTemplate.send(event.getTypeId(), event.getMessageId(), event);
  }
}
