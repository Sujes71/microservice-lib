package es.zed.event.spring;

import es.zed.common.service.utils.CustomObjectMapper;
import es.zed.event.common.spring.EventParserManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * A configuration bean of this class is automatically created during spring boot application startup. It is designed to create some beans when this
 * library is added as dependency in the project, without explicit declaration. This makes easier to configure applications that depends on this
 * project beans and configurations.
 */
@Configuration
@ConditionalOnClass(CustomObjectMapper.class)
public class EventsAutoConfiguration {

  /**
   * Event parser manager, it registers all the existing parsers and helps to obtain the proper one for a given event.
   *
   * @return Event parser manager.
   */
  @Bean
  public EventParserManager eventParserManager() {
    return new EventParserManager();
  }

}
