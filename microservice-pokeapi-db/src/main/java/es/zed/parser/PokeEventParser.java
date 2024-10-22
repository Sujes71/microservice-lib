package es.zed.parser;

import es.zed.pokeapi.abstracts.AbstractPokeEvent;
import es.zed.utils.CustomObjectMapper;
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

