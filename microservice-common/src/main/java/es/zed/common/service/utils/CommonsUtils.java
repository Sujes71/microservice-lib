package es.zed.common.service.utils;

/**
 * Utilities class for microservice-commons.
 */
public class CommonsUtils {

  /**
   * Constant use for string empty fields.
   */
  public static final String EMPTY_STRING = "";

  /**
   * Parses the given String to Integer.
   *
   * @param str given String.
   * @return the Integer value or null.
   */
  public static Integer parseIntOrNull(String str) {
    try {
      return str != null ? Integer.parseInt(str) : null;
    } catch (NumberFormatException e) {
      return null;
    }
  }

  /**
   * Parses the given Integer to String.
   *
   * @param integer given Integer.
   * @return the String value or null.
   */
  public static String parseStringOrNull(Integer integer) {
    return integer != null ? String.valueOf(integer) : null;
  }
}
