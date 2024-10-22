package es.zed.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Login request dto.
 */
@Data
@NoArgsConstructor
public class LoginRequestDto {

  /**
   * username.
   */
  private String username;

  /**
   * pwd.
   */
  private String password;
}
