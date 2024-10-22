package es.zed.security;

import es.zed.config.PokeAuthentication;
import es.zed.enums.AccessRoleEnum;
import es.zed.exception.GenericException;
import es.zed.exception.enums.GenericTypeException;
import es.zed.utils.CommonsUtils;
import es.zed.utils.DateUtils;
import es.zed.utils.UuidUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.sql.Date;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 * Jwt service.
 */
@Service
@PropertySource("classpath:security-db.properties")
public class JwtService {

  /**
   * Username claim property name in the JWT.
   */
  private static final String CLAIM_USERNAME = "username";
  /**
   * Access role claim property in the JWT.
   */
  private static final String CLAIM_ACCESS_ROLE = "access_role";
  /**
   * List of permissions claim property in the JWT.
   */
  private static final String CLAIM_AUTHORITIES = "authorities";

  /**
   * secret key.
   */
  private final SecretKey secretKey;

  /**
   * JWT expiration amount.
   */

  private final long expirationAmount;

  /**
   * JWT expiration amount.
   */
  private final ChronoUnit expirationAmountUnit;

  /**
   * Constructor.
   *
   * @param secretKey secret key.
   * @param expirationAmount expiration amount.
   * @param expirationAmountUnit expiration amount unit.
   */
  public JwtService(@Value("${secret.key}") String secretKey, @Value("${jwt.expiration.amount}") Long expirationAmount,
      @Value("${jwt.expiration.amount-unit}") ChronoUnit expirationAmountUnit) {
    this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
    this.expirationAmount = expirationAmount;
    this.expirationAmountUnit = expirationAmountUnit;
  }

  /**
   * Generate token.
   *
   * @param token token.
   * @return token.
   */
  public String createJwtFromSpec(final JwtBearerToken token) {
    if (token == null) {
      throw new GenericException(GenericTypeException.INVALID_TOKEN_EXCEPTION);
    }

    final String subject = token.getUserId();
    final ZonedDateTime expiration = DateUtils.zonedDateTimeUtc().plus(expirationAmount, expirationAmountUnit);
    final Map<String, Object> claimsMap = new HashMap<>();

    claimsMap
        .put(CLAIM_USERNAME,
            token.getUserId() == null ? CommonsUtils.EMPTY_STRING : token.getUserId());
    claimsMap
        .put(CLAIM_ACCESS_ROLE,
            token.getAccessRole() == null ? CommonsUtils.EMPTY_STRING : token.getAccessRole());
    claimsMap
        .put(CLAIM_AUTHORITIES, token.getAuthorities());

    return Jwts.builder().setClaims(claimsMap)
        .setId(token.getId() == null ? UuidUtils.newUuid() : token.getId())
        .setExpiration(Date.from(expiration.toInstant()))
        .setSubject(subject)
        .signWith(secretKey).compact();
  }

  /**
   * Poke authentication validation.
   *
   * @param jwt jwt.
   * @return auth.
   */
  public PokeAuthentication validateAuthentication(String jwt) {
    if (jwt == null) {
      throw new NullPointerException("Token string is a required argument");
    }
    try {
      final JwtBearerToken token = extractJwtDetails(jwt);
      return new PokeAuthentication(token);
    } catch (Exception e) {
      throw new GenericException(GenericTypeException.INVALID_TOKEN_EXCEPTION);
    }
  }

  /**
   * Extract claims details from the given JWT.
   *
   * @param token a valid, signed, JWT.
   * @return a new object with the given JWT details.
   */
  private JwtBearerToken extractJwtDetails(final String token) {
    final Claims claims = Jwts.parserBuilder()
        .setSigningKey(secretKey)
        .build()
        .parseClaimsJws(token)
        .getBody();

    final String username = claims.get(CLAIM_USERNAME, String.class);
    final String access_role = claims.get(CLAIM_ACCESS_ROLE, String.class);
    final List<String> authorities = claims.get(CLAIM_AUTHORITIES, List.class);

    return new JwtBearerToken(claims.getId(),
        DateUtils.zonedDateTimeUtc(claims.getExpiration().getTime()),
        claims.getSubject(),
        username,
        AccessRoleEnum.valueOf(access_role),
        authorities);
  }
}
