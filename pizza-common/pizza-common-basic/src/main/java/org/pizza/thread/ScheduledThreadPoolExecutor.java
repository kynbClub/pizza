package org.pizza.thread;


import org.slf4j.MDC;

import java.util.UUID;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;

/**
 * @author 高巍
 * @since 2020/11/20 4:17 下午
 */
public class ScheduledThreadPoolExecutor extends java.util.concurrent.ScheduledThreadPoolExecutor {

    /**
     * 线程开启时，设置日志seq
     *
     * @param t
     * @param r
     */
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        MDC.put("X-B3-TraceId", UUID.randomUUID().toString().split("-")[0]);
    }

    /**
     * 清空日志seq
     *
     * @param r
     * @param t
     */
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        MDC.clear();
    }

    /**
     * Creates a new {@code ScheduledThreadPoolExecutor} with the given core pool size.
     *
     * @param corePoolSize the number of threads to keep in the pool, even if they are idle, unless
     *        {@code allowCoreThreadTimeOut} is set
     * @throws IllegalArgumentException if {@code corePoolSize < 0}
     */
    public ScheduledThreadPoolExecutor(int corePoolSize) {
        super(corePoolSize);
    }

    /**
     * Creates a new {@code ScheduledThreadPoolExecutor} with the given initial parameters.
     *
     * @param corePoolSize the number of threads to keep in the pool, even if they are idle, unless
     *        {@code allowCoreThreadTimeOut} is set
     * @param threadFactory the factory to use when the executor creates a new thread
     * @throws IllegalArgumentException if {@code corePoolSize < 0}
     * @throws NullPointerException if {@code threadFactory} is null
     */
    public ScheduledThreadPoolExecutor(int corePoolSize, ThreadFactory threadFactory) {
        super(corePoolSize, threadFactory);
    }

    /**
     * Creates a new ScheduledThreadPoolExecutor with the given initial parameters.
     *
     * @param corePoolSize the number of threads to keep in the pool, even if they are idle, unless
     *        {@code allowCoreThreadTimeOut} is set
     * @param handler the handler to use when execution is blocked because the thread bounds and queue capacities are
     *        reached
     * @throws IllegalArgumentException if {@code corePoolSize < 0}
     * @throws NullPointerException if {@code handler} is null
     */
    public ScheduledThreadPoolExecutor(int corePoolSize, RejectedExecutionHandler handler) {
        super(corePoolSize, handler);
    }

    /**
     * Creates a new ScheduledThreadPoolExecutor with the given initial parameters.
     *
     * @param corePoolSize the number of threads to keep in the pool, even if they are idle, unless
     *        {@code allowCoreThreadTimeOut} is set
     * @param threadFactory the factory to use when the executor creates a new thread
     * @param handler the handler to use when execution is blocked because the thread bounds and queue capacities are
     *        reached
     * @throws IllegalArgumentException if {@code corePoolSize < 0}
     * @throws NullPointerException if {@code threadFactory} or {@code handler} is null
     */
    public ScheduledThreadPoolExecutor(int corePoolSize, ThreadFactory threadFactory,
                                       RejectedExecutionHandler handler) {
        super(corePoolSize, threadFactory, handler);
    }
}
