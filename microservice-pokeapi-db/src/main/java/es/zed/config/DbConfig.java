package es.zed.config;

import es.zed.parser.PokeEventParser;
import es.zed.utils.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Config.
 */
@Configuration
public class DbConfig {

  /**
   * Global json object mapper bean. It is already configured to (de)serialize java8 date and datetime formats as ISO 8601 string date.
   *
   * @return the {@link CustomObjectMapper}.
   */
  @Bean
  public CustomObjectMapper customObjectMapper() {
    return new CustomObjectMapper();
  }

  /**
   * Configuration restTemplate.
   *
   * @return restTemplate.
   */
  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

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
