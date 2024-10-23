package es.zed.config;

import es.zed.security.AuthConverter;
import es.zed.security.AuthFilter;
import es.zed.security.AuthManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security config for traditional Spring Security.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  /**
   * Auth converter.
   */
  private final AuthConverter authConverter;

  /**
   * Auth manager.
   */
  private final AuthManager authManager;

  /**
   * Constructor.
   *
   * @param authConverter auth converter.
   * @param authManager   auth manager.
   */
  public SecurityConfig(AuthConverter authConverter, AuthManager authManager) {
    this.authConverter = authConverter;
    this.authManager = authManager;
  }

  /**
   * Filter chain.
   *
   * @param http http.
   * @return chain.
   * @throws Exception ex.
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .cors(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
            .anyRequest().authenticated()
        )
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    http.addFilterBefore(new AuthFilter(authConverter, authManager), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}