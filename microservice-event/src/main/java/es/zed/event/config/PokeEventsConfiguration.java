package es.zed.event.config;

import es.zed.common.service.utils.CustomObjectMapper;
import es.zed.event.parser.PokeEventParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Poke events configuration.
 */
@Configuration
@ConditionalOnClass(CustomObjectMapper.class)
public class PokeEventsConfiguration {

  /**
   * Users micro-service events parser.
   *
   * @param customObjectMapper the jackson parser used to transform JSON.
   * @return Poke events parser.
   */
  @Bean
  public PokeEventParser usersEventParser(@Autowired CustomObjectMapper customObjectMapper) {
    return new PokeEventParser(customObjectMapper);
  }
}
