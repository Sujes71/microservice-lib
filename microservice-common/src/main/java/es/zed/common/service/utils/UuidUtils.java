package es.zed.common.service.utils;

import java.util.UUID;

/**
 * UUID utilities.
 */
public class UuidUtils {

  /**
   * Create a new Universal Unique Identifier.
   *
   * @return Universal Unique Identifier.
   */
  public static String newUuid() {
    return UUID.randomUUID().toString();
  }

}
