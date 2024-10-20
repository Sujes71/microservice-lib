package es.zed.event.parser;

import es.zed.common.utils.CustomObjectMapper;
import es.zed.event.common.AbstractEventParser;
import es.zed.event.pokedb.abstracts.AbstractAbilityEvent;
import org.springframework.stereotype.Component;

/**
 * Poke event parser.
 */
@Component
public class AbilityEventParser extends AbstractEventParser<AbstractAbilityEvent> {

  /**
   * Poke event parser.
   *
   * @param customObjectMapper object mapper.
   */
  public AbilityEventParser(CustomObjectMapper customObjectMapper) {
    super(customObjectMapper, AbstractAbilityEvent.class, AbstractAbilityEvent.CONTEXT);
  }
}

