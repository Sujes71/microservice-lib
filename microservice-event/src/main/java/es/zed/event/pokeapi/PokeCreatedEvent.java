package es.zed.event.pokeapi;

import es.zed.event.pokeapi.abstracts.AbstractPokeEvent;
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
   * @param origin origin.
   * @param typeId typeId.
   * @param messageId messageId.
   * @param creationTs creationTs.
   * @param pokemonId pokemonId.
   * @param body body.
   */
  @Builder
  public PokeCreatedEvent(final String origin, final String typeId, final String messageId,
      final Long creationTs, final String pokemonId, final PokeCreatedEventBody body) {
    super(origin, CONTEXT, typeId, messageId, VERSION, creationTs, pokemonId, body);
  }
}
