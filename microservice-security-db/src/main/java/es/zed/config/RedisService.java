package es.zed.config;

import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.stereotype.Service;

/**
 * Redis service.
 */
@Service
public class RedisService {

  /**
   * Redis ops.
   */
  private final ReactiveValueOperations<String, String> redisOps;


  /**
   * Constructor.
   *
   * @param redisOps redisOps.
   */
  public RedisService(ReactiveValueOperations<String, String> redisOps) {
    this.redisOps = redisOps;
  }

  /**
   * Get reactive value operations.
   *
   * @return redisOps.
   */
  public ReactiveValueOperations<String, String> getValueOperations() {
    return redisOps;
  }
}
