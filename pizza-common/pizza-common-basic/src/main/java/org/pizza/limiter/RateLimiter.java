package org.pizza.limiter;

import java.util.concurrent.TimeUnit;

public interface RateLimiter {
    /**
     *
     * @param key 限制的key
     * @return 是否超出
     */
    boolean isExceedRate(String key);

    /**
     *
     * @param key 限制的key
     * @param time 冷却时间
     * @param timeUnit 时间单位
     */
    void limitRate(String key, long time, TimeUnit timeUnit);

    /**
     *
     * @param key 限制的key
     */
    void clean(String key);
}
