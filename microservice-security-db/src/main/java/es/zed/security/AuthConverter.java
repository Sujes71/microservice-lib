package es.zed.security;

import es.zed.config.JwtBearerToken;
import es.zed.config.UserSecurity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Convert header values into authentication token.
 */
@Component
public class AuthConverter implements ServerAuthenticationConverter {

  /**
   * jwt service.
   */
  private final JwtService jwtService;

  /**
   * Constructor.
   *
   * @param jwtService jwt service.
   */
  public AuthConverter(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  /**
   * Auth converter.
   *
   * @param exchange exchange.
   * @return authentication.
   */
  @Override
  public Mono<Authentication> convert(ServerWebExchange exchange) {
    if (exchange.getRequest().getURI().getPath().equals("/api/login")) {
      return Mono.empty();
    }

    return Mono.justOrEmpty(
            exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION)
        )
        .filter(s -> s.startsWith("Bearer "))
        .map(s -> s.substring(7))
        .flatMap(token -> extractUserFromToken(token)
            .map(us -> new JwtBearerToken(us.getUsername(), null, us.getAuthorities())));
  }

  /**
   * Extract user from token.
   *
   * @param token token.
   * @return user.
   */
  private Mono<UserSecurity> extractUserFromToken(String token) {
    String username = this.jwtService.getUserName(token);
    Collection<String> authorities = this.jwtService.getAuthoritiesAndRoles(token);

    List<GrantedAuthority> allAuthorities = new ArrayList<>();
    for (String authority : authorities) {
      allAuthorities.add(new SimpleGrantedAuthority(authority));
    }

    return Mono.just(UserSecurity.builder()
        .username(username)
        .password(null)
        .authorities(allAuthorities).build());
  }

}
