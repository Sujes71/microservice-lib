package es.zed.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Poke authentication.
 */
public class PokeAuthentication extends AbstractAuthenticationToken implements IPokeAuthentication {

  /**
   * Jwt bearer token.
   */
  @Getter
  private final JwtBearerToken jwtBearerToken;

  /**
   * Constructor.
   *
   * @param jwtBearerToken token.
   */
  public PokeAuthentication(JwtBearerToken jwtBearerToken) {
    super(getAuthoritiesFromJwt(jwtBearerToken));
    this.jwtBearerToken = jwtBearerToken;
    this.setAuthenticated(true);
    this.setDetails(jwtBearerToken);
  }

  private static List<SimpleGrantedAuthority> getAuthoritiesFromJwt(JwtBearerToken jwt) {
    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    if (Objects.nonNull(jwt.getAuthorities())) {
      for (String authority : jwt.getAuthorities()) {
        authorities.add(new SimpleGrantedAuthority(authority));
      }
    }
    return authorities;
  }

  /**
   * Get credentials.
   *
   * @return null.
   */
  @Override
  public Object getCredentials() {
    return null;
  }

  /**
   * Get principal.
   *
   * @return principal.
   */
  @Override
  public Object getPrincipal() {
    return this.jwtBearerToken.getSubject();
  }

}
