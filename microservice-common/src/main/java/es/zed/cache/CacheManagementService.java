package es.zed.cache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

/**
 * Cache management service.
 */
@Service
public class CacheManagementService {

  /**
   * Clear cache.
   *
   * @param key key.
   */
  @CacheEvict(value = "PokeCache", key = "#key")
  public void clearCache(String key) {
  }
}
