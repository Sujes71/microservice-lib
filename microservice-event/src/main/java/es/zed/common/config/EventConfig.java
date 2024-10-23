package es.zed.common.config;

import es.zed.common.parser.PokeEventParser;
import es.zed.utils.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Event config.
 */
@Configuration
public class EventConfig {

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
