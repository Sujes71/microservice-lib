package es.zed.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filter to handle JWT authentication.
 */
public class AuthFilter extends OncePerRequestFilter {

  /**
   * Auth converter.
   */
  private final AuthConverter authConverter;

  /**
   * Auth manager.
   */
  private final AuthenticationManager authManager;

  /**
   * Constructor.
   *
   * @param authConverter converter.
   * @param authManager manager.
   */
  public AuthFilter(AuthConverter authConverter, AuthenticationManager authManager) {
    this.authConverter = authConverter;
    this.authManager = authManager;
  }

  /**
   * Filter.
   *
   * @param request request.
   * @param response response.
   * @param filterChain chain.
   * @throws ServletException ex.
   * @throws IOException ex.
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    PokeAuthentication authentication = (PokeAuthentication) authConverter.convert(request);
    if (authentication != null) {
      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authManager.authenticate(authentication));
    }
    filterChain.doFilter(request, response);
  }
}
