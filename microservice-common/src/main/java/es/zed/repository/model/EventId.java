package es.zed.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * EventId entity, represents the objects stored in the EventId collection.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventId {

  /**
   * The event identifier.
   */
  @Id
  private String id;

  /**
   * Context.
   */
  private String context;

  /**
   * EventType.
   */
  private String eventType;

  /**
   * Manager.
   */
  private String manager;

}
