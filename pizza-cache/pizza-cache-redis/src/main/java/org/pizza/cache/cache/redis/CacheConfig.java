package org.pizza.cache.cache.redis;

import java.time.Duration;
import java.util.HashMap;

/**
 * @author 高巍
 * @since 2019年06月30日 14:42
 */
public interface CacheConfig {

    HashMap<String, Duration> config();


}
