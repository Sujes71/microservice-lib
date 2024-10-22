package es.zed.pokedb;

import es.zed.pokedb.abstracts.AbstractAbilityEvent;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Poke created event.
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AbilityCreatedEvent extends AbstractAbilityEvent<AbilityCreatedEventBody> {

  /**
   * Unique platform event type identifier.
   */
  public static final String TYPE_ID = "event.AbilityCreatedEvent";
  /**
   * Event version.
   */
  public static final String VERSION = "1.0";

  /**
   * Constructor.
   */
  public AbilityCreatedEvent() {
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
  public AbilityCreatedEvent(final String origin, final String typeId, final String messageId,
      final Long creationTs, final String pokemonId, final AbilityCreatedEventBody body) {
    super(origin, CONTEXT, typeId, messageId, VERSION, creationTs, pokemonId, body);
  }
}
