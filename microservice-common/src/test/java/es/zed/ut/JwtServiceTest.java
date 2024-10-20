package es.zed.ut;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import es.zed.common.JwtService;
import io.jsonwebtoken.Claims;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = es.zed.Application.class)
class JwtServiceTest {

  @Autowired
  private JwtService jwtService;

  @Test
  void testGenerateToken() {
    Map<String, Object> claims = new HashMap<>();
    claims.put("sub", "user123");
    claims.put("role", "admin");

    String token = jwtService.generateToken(claims);
    assertNotNull(token);

    System.out.println(token);

    Claims extractedClaims = jwtService.validateToken(token);
    assertNotNull(extractedClaims);
  }
}
