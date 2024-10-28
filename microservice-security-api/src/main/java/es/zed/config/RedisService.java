package es.zed.config;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

/**
 * Redis service.
 */
@Service
public class RedisService {

  /**
   * redisOps.
   */
  private final ValueOperations<String, String> redisOps;

  /**
   * Constructor.
   *
   * @param redisTemplate template.
   */
  public RedisService(RedisTemplate<String, String> redisTemplate) {
    this.redisOps = redisTemplate.opsForValue();
  }

  /**
   * Get value operations.
   *
   * @return redisOps.
   */
  public ValueOperations<String, String> getValueOperations() {
    return redisOps;
  }
}
