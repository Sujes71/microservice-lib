package es.zed;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Jwt service.
 */
@Service
public class JwtService {

  /**
   * secret key.
   */
  private final Key secretKey;

  /**
   * Constructor.
   *
   * @param secretKey secret key.
   */
  public JwtService(@Value("${secret.key}") String secretKey) {
    this.secretKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());
  }

  /**
   * Generate token.
   *
   * @return token.
   */
  public String generateToken() {
    return Jwts.builder()
        .setClaims(null)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
        .signWith(secretKey)
        .compact();
  }

  /**
   * Validate token.
   *
   * @param token token.
   * @return claims.
   */
  public Claims validateToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(secretKey)
        .build()
        .parseClaimsJws(token)
        .getBody();
  }
}
