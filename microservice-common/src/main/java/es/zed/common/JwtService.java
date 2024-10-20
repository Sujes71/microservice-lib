package es.zed.common;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

  private final SecretKey secretKey;

  public JwtService(SecretKey secretKey) {
    this.secretKey = secretKey;
  }

  public String generateToken(Map<String, Object> claims) {
    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hora de validez
        .signWith(secretKey)
        .compact();
  }

  // Método para validar el token JWT
  public Claims validateToken(String token) {
    return Jwts.parser()
        .setSigningKey(secretKey)  // Usa el SecretKey directamente
        .parseClaimsJws(token)
        .getBody();
  }
}