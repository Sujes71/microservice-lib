package es.zed.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.Map;

/**
 * Customized object mapper.
 */
public class CustomObjectMapper extends ObjectMapper {

  /**
   * Default constructor.
   */
  public CustomObjectMapper() {
    this.registerModule(new JavaTimeModule());
    this.registerModule(new Jdk8Module());
    this.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    this.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
  }

  /**
   * Map url.
   *
   * @param replacements replacements.
   * @param path path.
   * @return String.
   */
  public String mapUrl(Map<String, String> replacements, String path) {
    for (Map.Entry<String, String> entry : replacements.entrySet()) {
      path = path.replace(entry.getKey(), entry.getValue());
    }

    return path;
  }
}
