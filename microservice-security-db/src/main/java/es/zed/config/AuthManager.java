package es.zed.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Auth manager.
 */
@Slf4j
@Component
public class AuthManager implements ReactiveAuthenticationManager {

  /**
   * Authenticate.
   *
   * @param authentication authentication.
   * @return authentication.
   */
  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    return Mono.justOrEmpty(authentication)
        .cast(PokeAuthentication.class)
        .flatMap(Mono::just);
  }
}
