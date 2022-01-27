package org.pizza.limiter;


import java.util.concurrent.TimeUnit;

/**
 * @author 高巍
 * @since 2020/12/18 4:43 下午
 */
public interface CountLimiter {

    /**
     * @param key 限制的key
     * @param limitCount
     * @return
     */
    boolean isExceedCount(String key, int limitCount);

    /**
     * 获取剩余锁定时间
     * @param key
     * @return
     */
    long lockTime(String key);
    /**
     * 增加次数，在指定监听时间内，超过限制次数则上锁
     *
     * @param key 限制的key
     * @param limitCount 限制次数
     * @param watchTime 监听事件
     * @param lockTime 锁定时间
     * @param timeUnit 时间单位
     */
    void increment(String key, int limitCount, long watchTime, long lockTime, TimeUnit timeUnit);

    /**
     * 清空限制
     *
     * @param key 限制的key
     */
    void clean(String key);

}
