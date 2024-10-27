package es.zed.pokeapi;

import es.zed.pokeapi.abstracts.AbstractPokeEvent;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Poke created event.
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PokeCreatedEvent extends AbstractPokeEvent<PokeCreatedEventBody> {

  /**
   * Unique platform event type identifier.
   */
  public static final String TYPE_ID = "event.PokeCreatedEvent";
  /**
   * Event version.
   */
  public static final String VERSION = "1.0";

  /**
   * Constructor.
   */
  public PokeCreatedEvent() {
    super(TYPE_ID, VERSION);
  }

  /**
   * PokeCreatedEvent.
   *
   * @param typeId typeId.
   * @param messageId messageId.
   * @param creationTs creationTs.
   * @param body body.
   */
  @Builder
  public PokeCreatedEvent(final String typeId, final String messageId,
      final Long creationTs, final PokeCreatedEventBody body) {
    super(CONTEXT, typeId, messageId, VERSION, creationTs, body);
  }
}
