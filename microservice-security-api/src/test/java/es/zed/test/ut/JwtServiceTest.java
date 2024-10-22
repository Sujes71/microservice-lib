package es.zed.test.ut;

import es.zed.Application;
import es.zed.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Application.class)
public class JwtServiceTest {

  @Autowired
  private JwtService jwtService;

  @Test
  public void generateToken() {
    System.out.println(jwtService.generateToken());
  }
}
