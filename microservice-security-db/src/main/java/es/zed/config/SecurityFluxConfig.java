package es.zed.config;

import es.zed.security.AuthConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

/**
 * Security config for WebFlux.
 */
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityFluxConfig {

  /**
   * Filter config.
   *
   * @param http http.
   * @param authConverter converter.
   * @param authManager manager.
   * @return chain.
   */
  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, AuthConverter authConverter, AuthManager authManager) {
    AuthenticationWebFilter jwtFilter = new AuthenticationWebFilter(authManager);
    jwtFilter.setServerAuthenticationConverter(authConverter);

    http
        .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
        .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
        .csrf(ServerHttpSecurity.CsrfSpec::disable)
        .cors(ServerHttpSecurity.CorsSpec::disable)
        .authorizeExchange(exchange -> exchange
            .pathMatchers(HttpMethod.POST, "/api/login").permitAll()
            .anyExchange().authenticated())
        .addFilterAt(jwtFilter, SecurityWebFiltersOrder.AUTHORIZATION);

    return http.build();
  }
}
