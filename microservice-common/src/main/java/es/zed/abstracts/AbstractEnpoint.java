package es.zed.abstracts;

import es.zed.exception.GenericException;
import es.zed.exception.enums.GenericTypeException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

/**
 * Abstract endpoint.
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractEnpoint {

  /**
   * Rest template.
   */
  private final RestTemplate restTemplate;

  /**
   * Create a URI object from the given Minstait relative endpoint path.
   *
   * @param uriPath Minstait relative endpoint path.
   * @return URI object with absolute endpoint path.
   */
  protected URI createUri(final String uriPath) {
    try {
      return new URI(uriPath);
    } catch (URISyntaxException e) {
      throw new GenericException(GenericTypeException.INVALID_URI_EXCEPTION) {
      };
    }
  }

  /**
   * Performs the operation encapsulated in the supplier instance to handle connection exceptions.
   *
   * @param supplier an operation encapsulated a the supplier instance.
   * @param <T> the result type of the operation.
   * @return the result of the operation if no exception has been thrown.
   */
  protected <T> T handleConnectionException(Supplier<T> supplier) {
    try {
      return supplier.get();
    } catch (ResourceAccessException ex) {
      throw new GenericException(GenericTypeException.RESOURCE_ACCESS_EXCEPTION) {
      };
    }
  }

  /**
   * Extract the response data from the given {@link ResponseEntity}.
   *
   * @param responseEntity {@link ResponseEntity}.
   * @param <T> Response type.
   * @return response data.
   */
  protected <T> T extractResponseData(final ResponseEntity<T> responseEntity) {
    return responseEntity.getBody();
  }

  /**
   * Add default headers.
   *
   * @param auth auth.
   * @return headers.
   */
  protected HttpHeaders addDefaultHeaders(final String auth) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("Authorization", auth);
    return httpHeaders;
  }

  /**
   * Generic method for api calls.
   *
   * @param <T> generic.
   * @param url url.
   * @param httpMethod httpMethod.
   * @param httpHeaders httpHeaders.
   * @param body body.
   * @param responseClass responseClass.
   * @return T generic.
   */
  protected <T> T doCall(final String url, final HttpMethod httpMethod, final HttpHeaders httpHeaders, final Object body,
      final Class<T> responseClass) {
    return handleConnectionException(
        () -> {
          log.info("Do call {}, method {}", url, httpMethod);

          return extractResponseData(restTemplate.exchange(new RequestEntity<>(body, httpHeaders, httpMethod, createUri(url)), responseClass));
        }
    );
  }
}
