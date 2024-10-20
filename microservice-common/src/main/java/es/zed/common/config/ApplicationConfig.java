package es.zed.common.config;

import es.zed.common.utils.CustomObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

/**
 * Application config.
 */
@Configuration
@PropertySource("classpath:common-config.properties")
public class ApplicationConfig {

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
}
