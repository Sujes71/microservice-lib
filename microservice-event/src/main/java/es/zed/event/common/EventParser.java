package es.zed.event.common;

import com.fasterxml.jackson.databind.JsonNode;
import es.zed.event.common.spring.EventParserManager;

/**
 * The contract to implement by any event parser that is going to be registered into the {@link EventParserManager}.
 *
 * @param <T> the event class this parser handles.
 */
public interface EventParser<T extends Event> {

  /**
   * Returns true if the parser is able to deserialize the given json.
   *
   * @param jsonNode a json.
   * @return true if the given json can be deserialized using this parser.
   */
  boolean wants(JsonNode jsonNode);

  /**
   * Desierialize the given json.
   *
   * @param jsonNode the json to deserialize.
   * @return the event described in the given json.
   */
  T parse(JsonNode jsonNode);

}
