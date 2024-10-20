package es.zed.event.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import es.zed.common.utils.CustomObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;

/**
 * Base class for any event parser.
 *
 * @param <T> the event class this parser handles.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public abstract class AbstractEventParser<T extends Event> implements EventParser {

  /**
   * Jackson event mapper.
   */
  private final CustomObjectMapper customObjectMapper;
  /**
   * TThe event class this parser handles.
   */
  private final Class<T> eventClass;
  /**
   * The context which events this parser is able to handle (that must be mapped to the given class).
   */
  private final String context;

  /**
   * Class constructor.
   *
   * @param customObjectMapper object mapper that will be used to serialize/deserialize json.
   * @param eventClass the event class this parser is going to handle.
   * @param context the context which events this parser is able to handle (that must be mapped to the given class).
   */
  public AbstractEventParser(@NonNull final CustomObjectMapper customObjectMapper,
      @NonNull final Class<T> eventClass,
      final String context) {
    this.customObjectMapper = customObjectMapper;
    this.eventClass = eventClass;
    this.context = context;
  }

  @Override
  public boolean wants(JsonNode jsonNode) {
    final JsonNode eventContext = jsonNode.get("context");
    return eventContext != null && context.equals(eventContext.asText());
  }

  @Override
  public T parse(JsonNode jsonNode) {
    try {
      return customObjectMapper.treeToValue(jsonNode, eventClass);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("The given JSON is not a known event",
          e);
    }
  }
}
