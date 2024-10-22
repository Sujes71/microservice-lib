package es.zed.config;

import java.util.Collection;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * user security.
 */
@Builder
public class UserSecurity implements UserDetails {

  /**
   * username.
   */
  private final String username;

  /**
   * password.
   */
  private final String password;

  /**
   * authorities.
   */
  private final Collection<? extends GrantedAuthority> authorities;

  /**
   * Constructor.
   *
   * @param username username.
   * @param password password.
   * @param authorities authorities.
   */
  public UserSecurity(String username, String password, Collection<? extends GrantedAuthority> authorities) {
    this.username = username;
    this.password = password;
    this.authorities = authorities;
  }

  /**
   * get authorities.
   *
   * @return collection of authorities.
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  /**
   * Get password.
   *
   * @return pwd.
   */
  @Override
  public String getPassword() {
    return password;
  }

  /**
   * Get username.
   *
   * @return username.
   */
  @Override
  public String getUsername() {
    return username;
  }

  /**
   * Acc is not expired.
   *
   * @return expired.
   */
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  /**
   * Acc non locked.
   *
   * @return locked.
   */
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  /**
   * Credentials non expired.
   *
   * @return expired.
   */
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  /**
   * Is enable.
   *
   * @return enabled.
   */
  @Override
  public boolean isEnabled() {
    return true;
  }
}
