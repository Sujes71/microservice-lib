package es.zed.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
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
   * List of authorities.
   */
  private List<String> authorities;

  /**
   * Constructor without authorities.
   * @param id id.
   * @param expiration expiration.
   * @param subject subject.
   */
  public JwtBearerToken(String id, ZonedDateTime expiration, String subject) {
    this.id = id;
    this.expiration = expiration;
    this.subject = subject;
    this.authorities = new LinkedList<>();
  }
}
