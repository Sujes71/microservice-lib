package es.zed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * move-money-service main Application.
 */
@SpringBootApplication
public class Application {

  /**
   * Microservice startup entrypoint.
   *
   * @param args command line arguments.
   */
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}