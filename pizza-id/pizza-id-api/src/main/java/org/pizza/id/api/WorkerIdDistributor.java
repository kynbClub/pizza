package org.pizza.id.api;

/**
 * @author 高巍
 * @since 2020/10/13 3:31 下午
 */
public interface WorkerIdDistributor {
    boolean init();

    long distribute();
}
