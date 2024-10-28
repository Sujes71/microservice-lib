package es.zed.security;

import es.zed.config.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * Auth manager.
 */
@Slf4j
@Component
public class AuthManager implements AuthenticationManager {

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
   * @throws AuthenticationException if authentication fails.
   */
  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    if (authentication instanceof PokeAuthentication) {
      final String subject = (String) authentication.getPrincipal();
      final String tokenId = (String) authentication.getCredentials();
      final String storedToken = redisService.getValueOperations().get(subject);

      if (storedToken != null && storedToken.equals(tokenId)) {
        authentication.setAuthenticated(Boolean.TRUE);
        return authentication;
      } else {
        throw new AuthenticationException("Invalid token") {};
      }
    } else {
      throw new AuthenticationException("Invalid authentication type") {};
    }
  }
}
