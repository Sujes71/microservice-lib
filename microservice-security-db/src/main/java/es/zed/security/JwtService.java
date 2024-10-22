package es.zed.security;

import es.zed.exception.GenericException;
import es.zed.exception.enums.GenericTypeException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
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
   * secret key.
   */
  private final SecretKey secretKey;

  /**
   * Jwt parser.
   */
  private final JwtParser parser;

  /**
   * Constructor.
   *
   * @param secretKey secret key.
   */
  public JwtService(@Value("${secret.key}") String secretKey) {
    this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
    this.parser = Jwts.parserBuilder().setSigningKey(this.secretKey).build();
  }

  /**
   * Generate token.
   *
   * @param username username.
   * @param claims claims.
   * @return token.
   */
  public String generateToken(String username, Map<String, Object> claims) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(username)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
        .signWith(this.secretKey)
        .compact();
  }

  /**
   * Get username from token.
   * @param token token.
   * @return username.
   */
  public String getUserName(String token) {
    try {
      return parser.parseClaimsJws(token).getBody().getSubject();
    } catch (Exception e) {
      throw new GenericException(GenericTypeException.INVALID_TOKEN_EXCEPTION);
    }
  }

  /**
   * Get authorities and roles from token.
   *
   * @param token token.
   * @return authorities and roles.
   */
  public Collection<String> getAuthoritiesAndRoles(String token) {
    try {
      Jws<Claims> claimsJws = parser.parseClaimsJws(token);
      Claims claims = claimsJws.getBody();
      List<String> authorities = claims.get("authorities", List.class);
      List<String> roles = claims.get("roles", List.class);

      Collection<String> combinedAuthorities = new HashSet<>();
      if (authorities != null) {
        combinedAuthorities.addAll(authorities);
      }
      if (roles != null) {
        combinedAuthorities.addAll(roles);
      }

      return combinedAuthorities.isEmpty() ? List.of() : combinedAuthorities;
    } catch (Exception e) {
      throw new GenericException(GenericTypeException.INVALID_TOKEN_EXCEPTION);
    }
  }
}
