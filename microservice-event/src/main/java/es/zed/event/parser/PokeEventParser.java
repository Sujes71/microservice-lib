package es.zed.event.parser;

import es.zed.common.utils.CustomObjectMapper;
import es.zed.event.common.AbstractEventParser;
import es.zed.event.pokeapi.abstracts.AbstractPokeEvent;
import org.springframework.stereotype.Component;

/**
 * Poke event parser.
 */
@Component
public class PokeEventParser extends AbstractEventParser<AbstractPokeEvent> {

  /**
   * Poke event parser.
   *
   * @param customObjectMapper object mapper.
   */
  public PokeEventParser(CustomObjectMapper customObjectMapper) {
    super(customObjectMapper, AbstractPokeEvent.class, AbstractPokeEvent.CONTEXT);
  }
}

