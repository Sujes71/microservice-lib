package es.zed.pokeapi;

import es.zed.pokeapi.abstracts.AbstractPokeEvent;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Poke updated event.
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PokeUpdatedEvent extends AbstractPokeEvent<PokeUpdatedEventBody> {

  /**
   * Unique platform event type identifier.
   */
  public static final String TYPE_ID = "event.PokeUpdatedEvent";
  /**
   * Event version.
   */
  public static final String VERSION = "1.0";

  /**
   * Constructor.
   */
  public PokeUpdatedEvent() {
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
  public PokeUpdatedEvent(final String typeId, final String messageId,
      final Long creationTs, final PokeUpdatedEventBody body) {
    super(CONTEXT, typeId, messageId, VERSION, creationTs, body);
  }
}
