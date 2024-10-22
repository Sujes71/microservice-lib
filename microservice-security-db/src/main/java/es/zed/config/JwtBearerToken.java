package es.zed.config;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * Bearer token.
 */
public class JwtBearerToken extends AbstractAuthenticationToken {

  /**
   * authenticated user.
   */
  private final String principal;

  /**
   * credentials.
   */
  private final String credentials;

  /**
   * Constructor.
   *
   * @param principal user.
   * @param credentials credentials.
   * @param authorities authorities.
   */
  public JwtBearerToken(String principal, String credentials, Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.principal = principal;
    this.credentials = credentials;
  }

  /**
   * Get credentials.
   *
   * @return credentials.
   */
  @Override
  public String getCredentials() {
    return this.credentials;
  }

  /**
   * Principal.
   *
   * @return principal.
   */
  @Override
  public String getPrincipal() {
    return this.principal;
  }
}
