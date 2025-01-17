package es.zed.pokeapi.abstracts;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import es.zed.common.AbstractEvent;
import es.zed.common.AbstractEventBody;
import es.zed.pokeapi.PokeCreatedEvent;
import es.zed.pokeapi.PokeUpdatedEvent;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Class that describes the pokemon of the events generated by the PokeApi micro-service.
 *
 * @param <B> the event body type.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type_id")
@JsonSubTypes({
    @JsonSubTypes.Type(value = PokeCreatedEvent.class, name = PokeCreatedEvent.TYPE_ID),
    @JsonSubTypes.Type(value = PokeUpdatedEvent.class, name = PokeUpdatedEvent.TYPE_ID),
})
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class AbstractPokeEvent<B extends AbstractEventBody> extends AbstractEvent<B> {

  /**
   * Constant value for the context field, unique for users micro-service context.
   */
  public static final String CONTEXT = "POKEAPI";

  /**
   * Abstract poke event.
   *
   * @param typeId typeId.
   * @param version version.
   */
  public AbstractPokeEvent(final String typeId, final String version) {
    super(CONTEXT, typeId);
    setVersion(version);
  }

  /**
   * Abstract poke event.
   *
   * @param context context.
   * @param typeId typeId.
   * @param messageId messageId.
   * @param version version.
   * @param creationTs creationTs.
   * @param body body.
   */
  public AbstractPokeEvent(String context, String typeId,
      String messageId, String version, Long creationTs,  B body) {
    super(context, typeId, messageId, version, creationTs, body);
  }
}
