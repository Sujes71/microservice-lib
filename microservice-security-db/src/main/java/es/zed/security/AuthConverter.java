package es.zed.security;

import es.zed.exception.GenericException;
import es.zed.exception.enums.GenericTypeException;
import io.micrometer.common.util.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
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
    return Mono.justOrEmpty(exchange.getRequest().getHeaders())
        .filter(h -> h.containsKey(HttpHeaders.AUTHORIZATION))
        .switchIfEmpty(Mono.error(new GenericException(GenericTypeException.INVALID_TOKEN_EXCEPTION)))
        .map(this::getTokenFromRequest)
        .map(jwtService::validateAuthentication);
  }

  /**
   * Token from request.
   *
   * @param httpHeaders headers.
   * @return token.
   */
  private String getTokenFromRequest(HttpHeaders httpHeaders) {
    String token = httpHeaders.getFirst(HttpHeaders.AUTHORIZATION);
    if (token == null) {
      return Strings.EMPTY;
    }
    return StringUtils.isEmpty(token) ? Strings.EMPTY : token.replace("Bearer ", "");
  }
}
