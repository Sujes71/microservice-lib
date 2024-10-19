package es.zed.common.service.exception.enums;

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
  INVALID_URI_EXCEPTION(ExceptionCode.C500000001.name(), HttpStatus.INTERNAL_SERVER_ERROR, "Invalid uri."),

  /**
   * Resource access exception.
   */
  RESOURCE_ACCESS_EXCEPTION(ExceptionCode.C500000002.name(), HttpStatus.INTERNAL_SERVER_ERROR, "Error accessing resource."),

  /**
   * Unexpected body response.
   */
  UNEXPECTED_BODY_RESPONSE(ExceptionCode.C500000003.name(), HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected body response."),

  /**
   * Invalid uri exception.
   */
  INVALID_CALL_EXCEPTION(ExceptionCode.C400000001.name(), HttpStatus.BAD_REQUEST, "Invalid call exception."),

  /**
   * Resource access exception.
   */
  INVALID_CREDENTIAL_EXCEPTION(ExceptionCode.C401000001.name(), HttpStatus.UNAUTHORIZED, "Invalid authentication exception.");


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
