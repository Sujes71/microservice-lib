package es.zed.config;

import es.zed.enums.AccessRoleEnum;
import es.zed.security.JwtBearerToken;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Poke authentication.
 */
public class PokeAuthentication extends AbstractAuthenticationToken implements IPokeAuthentication {

  /**
   * Jwt bearer token.
   */
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
    authorities.add(new SimpleGrantedAuthority(jwt.getAccessRole().name()));
    if (Objects.nonNull(jwt.getAuthorities())) {
      for (String authority : jwt.getAuthorities()) {
        authorities.add(new SimpleGrantedAuthority(authority));
      }
    }
    return authorities;
  }

  /**
   * Get username.
   *
   * @return username.
   */
  @Override
  public String getUsername() {
    return jwtBearerToken.getUserId();
  }

  /**
   * Get access role.
   *
   * @return access role.
   */
  @Override
  public AccessRoleEnum getAccessRole() {
    return this.jwtBearerToken.getAccessRole();
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
    return this.jwtBearerToken.getUserId();
  }

}
