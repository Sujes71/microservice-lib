package es.zed.config;

import es.zed.enums.AccessRoleEnum;
import org.springframework.security.core.Authentication;

/**
 * IPoke authentication.
 */
public interface IPokeAuthentication extends Authentication {

  /**
   * Get username.
   *
   * @return username.
   */
  String getUsername();

  /**
   * Get access role.
   *
   * @return role.
   */
  AccessRoleEnum getAccessRole();

}
