package es.zed.security;

import es.zed.exception.GenericException;
import es.zed.exception.enums.GenericTypeException;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.stereotype.Component;

/**
 * Convert header values into authentication token.
 */
@Component
public class AuthConverter implements AuthenticationConverter {

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
   * Auth converter for non-reactive environments.
   *
   * @param request HTTP servlet request.
   * @return authentication.
   */
  @Override
  public Authentication convert(HttpServletRequest request) {
    HttpHeaders headers = getHeadersFromRequest(request);

    if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
      throw new GenericException(GenericTypeException.INVALID_TOKEN_EXCEPTION);
    }

    String token = getTokenFromRequest(headers);

    return jwtService.validateAuthentication(token);
  }

  /**
   * Obtiene los headers desde la solicitud.
   *
   * @param request HTTP servlet request.
   * @return HttpHeaders con todos los headers.
   */
  private HttpHeaders getHeadersFromRequest(HttpServletRequest request) {
    HttpHeaders headers = new HttpHeaders();
    Collections.list(request.getHeaderNames()).forEach(headerName ->
        headers.put(headerName, Collections.list(request.getHeaders(headerName))));
    return headers;
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
