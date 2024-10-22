package es.zed.utils;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * Utils for creating and transforming dates and times.
 */
public class DateUtils {

  /**
   * Return the actual date and time UTC relative.
   *
   * @return actual date and time UTC relative.
   */
  public static ZonedDateTime zonedDateTimeUtc() {
    return ZonedDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
  }

  /**
   * Return the date and time UTC relative for the given milliseconds from epoch.
   *
   * @param millisFromEpoc milliseconds from EPOCH.
   * @return date and time UTC relative for the given milliseconds from epoch.
   */
  public static ZonedDateTime zonedDateTimeUtc(final long millisFromEpoc) {
    return ZonedDateTime.ofInstant(Instant.ofEpochMilli(millisFromEpoc), ZoneOffset.UTC);
  }
}
