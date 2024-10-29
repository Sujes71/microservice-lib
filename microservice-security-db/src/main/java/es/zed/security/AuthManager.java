package es.zed.security;

import es.zed.config.RedisService;
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
   * Redis service.
   */
  private final RedisService redisService;

  /**
   * Constructor.
   *
   * @param redisService service.
   */
  public AuthManager(RedisService redisService) {
    this.redisService = redisService;
  }

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
        .flatMap(auth -> {
          String subject = (String) auth.getPrincipal();
          String tokenId = (String) auth.getCredentials();

          return this.redisService.getValueOperations().get(subject)
              .flatMap(storedToken -> {
                if (tokenId.equals(storedToken)) {
                  auth.setAuthenticated(Boolean.TRUE);
                  return Mono.just(auth);
                } else {
                  log.warn("Invalid token for subject {}", subject);
                  return Mono.just(auth);
                }
              })
              .switchIfEmpty(Mono.defer(() -> {
                log.warn("No token found for subject: {}", subject);
                return Mono.just(auth);
              }));
        });
  }
}