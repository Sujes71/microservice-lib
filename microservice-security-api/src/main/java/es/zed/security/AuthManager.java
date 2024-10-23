package es.zed.security;

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
   * Authenticate.
   *
   * @param authentication authentication.
   * @return authentication.
   * @throws AuthenticationException if authentication fails.
   */
  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    if (authentication instanceof PokeAuthentication) {
      return authentication;
    } else {
      throw new AuthenticationException("Invalid authentication type") {
      };
    }
  }
}
