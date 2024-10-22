package es.zed.parser;

import es.zed.pokedb.abstracts.AbstractAbilityEvent;
import es.zed.utils.CustomObjectMapper;
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

