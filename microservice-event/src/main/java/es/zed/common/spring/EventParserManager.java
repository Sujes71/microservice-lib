package es.zed.common.spring;

import com.fasterxml.jackson.databind.JsonNode;
import es.zed.common.EventParser;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * This components maintains a Event Parser registry and publishes a method (see {@link #findParser(JsonNode)} to retrieve the proper parser for a
 * given json.
 */
@Component
@Slf4j
public class EventParserManager {

  /**
   * The known event parsers.
   */
  private final List<EventParser> eventParsers;

  public EventParserManager(List<EventParser> eventParsers) {
    this.eventParsers = eventParsers;
  }

  /**
   * Get the parser that can be used to deserialize the given json.
   *
   * @param jsonNode the json to parse.
   * @return the first event parser that can deserialize the given json.
   */
  public EventParser findParser(JsonNode jsonNode) {
    if (eventParsers != null) {
      for (EventParser parser : eventParsers) {
        if (parser.wants(jsonNode)) {
          return parser;
        }
      }
    }
    return null;
  }

  /**
   * Get all the known parsers.
   *
   * @return all the parsers registeres at this instance.
   */
  public List<EventParser> allParsers() {
    return eventParsers == null ? Collections.emptyList()
        : Collections.unmodifiableList(eventParsers);
  }
}
