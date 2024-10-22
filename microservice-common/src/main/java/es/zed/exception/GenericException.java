package es.zed.exception;

import es.zed.exception.enums.GenericTypeException;

/**
 * Generic exception.
 */
public class GenericException extends PokemonException {


  /**
   * Constructor.
   *
   * @param genericTypeException type exception.
   */
  public GenericException(GenericTypeException genericTypeException) {
    super(genericTypeException.getCode(), genericTypeException.getHttpStatus(), genericTypeException.getMessage(), null);
  }
}
