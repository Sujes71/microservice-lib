package es.zed.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import es.zed.enums.AccessRoleEnum;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Bearer token.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class JwtBearerToken {

  /**
   * Token id.
   */
  private String id;

  /**
   * Token expiration date.
   */
  private ZonedDateTime expiration;

  /**
   * The name of the user this token was created for.
   */
  private String subject;

  /**
   * The username of the user.
   */
  private String userId;

  /**
   * The access role of the user. Could be:
   * private_user
   * public_user
   * manager_user
   */
  private AccessRoleEnum accessRole;

  /**
   * List of authorities.
   */
  private List<String> authorities;

  /**
   * Constructor without authorities.
   * @param id id.
   * @param expiration expiration.
   * @param subject subject.
   * @param userId userId.
   * @param accessRole accesRole.
   */
  public JwtBearerToken(String id, ZonedDateTime expiration, String subject, String userId, AccessRoleEnum accessRole) {
    this.id = id;
    this.expiration = expiration;
    this.subject = subject;
    this.userId = userId;
    this.accessRole = accessRole;
    this.authorities = new LinkedList<>();
  }
}
