package es.zed.common.service.exception;

import org.springframework.http.HttpStatus;

/**
 * This is the parent exception for any exception thrown corresponding to an handled error condition.
 *
 * <p>This exceptions must be handled in an event execution contexts (the event will NOT be redirected to the dead-letter-queue) and must cause a
 * rich * error response (with reason details) in REST operation contexts.
 */
public abstract class PokemonException extends RuntimeException {

  /**
   * The HTTP status to return if the exception is thrown in a REST operation context.
   */
  private final HttpStatus httpStatus;

  /**
   * Error code.
   */
  private final String code;

  /**
   * Constructor.
   *
   * @param code exception code. It is defined as a string, not an enumeration, to be extensible by micro-services.
   * @param httpStatus the corresponding http status in a REST operation context.
   * @param message error message.
   * @param cause error cause, if any.
   */
  protected PokemonException(final String code, final HttpStatus httpStatus, final String message, final Throwable cause) {
    super(message, cause);
    this.code = code;
    this.httpStatus = httpStatus;
  }
}

