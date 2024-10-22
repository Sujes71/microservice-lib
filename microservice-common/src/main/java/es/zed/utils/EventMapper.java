package es.zed.utils;

/**
 * Event mapper interface.
 * @param <T> generic object.
 * @param <E> generic event.
 */
public interface EventMapper<T, E> {

  /**
   * Build event.
   *
   * @param object generic object.
   * @return event.
   */
  E buildEvent(T object);
}
