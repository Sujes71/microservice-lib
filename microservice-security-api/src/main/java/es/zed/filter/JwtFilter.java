package es.zed.filter;

import es.zed.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Jwt filter.
 */
@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

  /**
   * jwt service.
   */
  private final JwtService jwtService;

  /**
   * Constructor.
   *
   * @param jwtService service.
   */
  public JwtFilter(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  /**
   * Do filter internal.
   *
   * @param request req.
   * @param response response.
   * @param chain chain.
   * @throws ServletException ex.
   * @throws IOException ex.
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    String authorizationHeader = request.getHeader("Authorization");

    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      String token = authorizationHeader.substring(7);
      try {
        Claims claims = jwtService.validateToken(token);
        if (claims != null) {
          UsernamePasswordAuthenticationToken authentication =
              new UsernamePasswordAuthenticationToken(null, null, Collections.emptyList());
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      } catch (Exception e) {
        log.info("Token error: {}", e.getMessage());
      }
    }

    chain.doFilter(request, response);
  }
}