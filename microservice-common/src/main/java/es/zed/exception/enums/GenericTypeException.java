package es.zed.exception.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Generic type exception.
 */
@Getter
@RequiredArgsConstructor
public enum GenericTypeException {

  /**
   * Invalid uri exception.
   */
  INVALID_URI_EXCEPTION(ExceptionCode.C500000.name(), HttpStatus.INTERNAL_SERVER_ERROR, "Invalid uri"),

  /**
   * Resource access exception.
   */
  RESOURCE_ACCESS_EXCEPTION(ExceptionCode.C500001.name(), HttpStatus.INTERNAL_SERVER_ERROR, "Error accessing resource"),

  /**
   * Unexpected body response.
   */
  UNEXPECTED_BODY_RESPONSE(ExceptionCode.C500002.name(), HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected body response"),

  /**
   * Invalid token exception.
   */
  INVALID_TOKEN_EXCEPTION(ExceptionCode.C401000.name(), HttpStatus.UNAUTHORIZED, "Invalid token");



  /**
   * Code.
   */
  private final String code;
  /**
   * Http status.
   */
  private final HttpStatus httpStatus;
  /**
   * Message.
   */
  private final String message;
}
