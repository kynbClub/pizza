package org.pizza.thread;

import org.slf4j.MDC;
import org.springframework.core.task.TaskExecutor;

import java.util.UUID;
import java.util.concurrent.*;

/**
 * @author 高巍
 * @since 2020/11/20 4:17 下午
 * 增加链路标记
 */
public class ThreadPoolExecutor extends java.util.concurrent.ThreadPoolExecutor implements TaskExecutor {
    /**
     * 线程开启时，设置日志TraceId
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
     * 清空日志TraceId
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
     * Creates a new {@code ThreadPoolExecutor} with the given initial parameters and default thread factory and
     * rejected execution handler. It may be more convenient to use one of the {@link Executors} factory methods instead
     * of this general purpose constructor.
     *
     * @param corePoolSize the number of threads to keep in the pool, even if they are idle, unless
     *        {@code allowCoreThreadTimeOut} is set
     * @param maximumPoolSize the maximum number of threads to allow in the pool
     * @param keepAliveTime when the number of threads is greater than the core, this is the maximum time that epizzaess
     *        idle threads will wait for new tasks before terminating.
     * @param unit the time unit for the {@code keepAliveTime} argument
     * @param workQueue the queue to use for holding tasks before they are executed. This queue will hold only the
     *        {@code Runnable} tasks submitted by the {@code execute} method.
     * @throws IllegalArgumentException if one of the following holds:<br>
     *         {@code corePoolSize < 0}<br>
     *         {@code keepAliveTime < 0}<br>
     *         {@code maximumPoolSize <= 0}<br>
     *         {@code maximumPoolSize < corePoolSize}
     * @throws NullPointerException if {@code workQueue} is null
     */
    public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                              BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    /**
     * Creates a new {@code ThreadPoolExecutor} with the given initial parameters and default rejected execution
     * handler.
     *
     * @param corePoolSize the number of threads to keep in the pool, even if they are idle, unless
     *        {@code allowCoreThreadTimeOut} is set
     * @param maximumPoolSize the maximum number of threads to allow in the pool
     * @param keepAliveTime when the number of threads is greater than the core, this is the maximum time that epizzaess
     *        idle threads will wait for new tasks before terminating.
     * @param unit the time unit for the {@code keepAliveTime} argument
     * @param workQueue the queue to use for holding tasks before they are executed. This queue will hold only the
     *        {@code Runnable} tasks submitted by the {@code execute} method.
     * @param threadFactory the factory to use when the executor creates a new thread
     * @throws IllegalArgumentException if one of the following holds:<br>
     *         {@code corePoolSize < 0}<br>
     *         {@code keepAliveTime < 0}<br>
     *         {@code maximumPoolSize <= 0}<br>
     *         {@code maximumPoolSize < corePoolSize}
     * @throws NullPointerException if {@code workQueue} or {@code threadFactory} is null
     */
    public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                              BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    /**
     * Creates a new {@code ThreadPoolExecutor} with the given initial parameters and default thread factory.
     *
     * @param corePoolSize the number of threads to keep in the pool, even if they are idle, unless
     *        {@code allowCoreThreadTimeOut} is set
     * @param maximumPoolSize the maximum number of threads to allow in the pool
     * @param keepAliveTime when the number of threads is greater than the core, this is the maximum time that epizzaess
     *        idle threads will wait for new tasks before terminating.
     * @param unit the time unit for the {@code keepAliveTime} argument
     * @param workQueue the queue to use for holding tasks before they are executed. This queue will hold only the
     *        {@code Runnable} tasks submitted by the {@code execute} method.
     * @param handler the handler to use when execution is blocked because the thread bounds and queue capacities are
     *        reached
     * @throws IllegalArgumentException if one of the following holds:<br>
     *         {@code corePoolSize < 0}<br>
     *         {@code keepAliveTime < 0}<br>
     *         {@code maximumPoolSize <= 0}<br>
     *         {@code maximumPoolSize < corePoolSize}
     * @throws NullPointerException if {@code workQueue} or {@code handler} is null
     */
    public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                              BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    /**
     * Creates a new {@code ThreadPoolExecutor} with the given initial parameters.
     *
     * @param corePoolSize the number of threads to keep in the pool, even if they are idle, unless
     *        {@code allowCoreThreadTimeOut} is set
     * @param maximumPoolSize the maximum number of threads to allow in the pool
     * @param keepAliveTime when the number of threads is greater than the core, this is the maximum time that epizzaess
     *        idle threads will wait for new tasks before terminating.
     * @param unit the time unit for the {@code keepAliveTime} argument
     * @param workQueue the queue to use for holding tasks before they are executed. This queue will hold only the
     *        {@code Runnable} tasks submitted by the {@code execute} method.
     * @param threadFactory the factory to use when the executor creates a new thread
     * @param handler the handler to use when execution is blocked because the thread bounds and queue capacities are
     *        reached
     * @throws IllegalArgumentException if one of the following holds:<br>
     *         {@code corePoolSize < 0}<br>
     *         {@code keepAliveTime < 0}<br>
     *         {@code maximumPoolSize <= 0}<br>
     *         {@code maximumPoolSize < corePoolSize}
     * @throws NullPointerException if {@code workQueue} or {@code threadFactory} or {@code handler} is null
     */
    public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                              BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }
}
