package org.pizza.pattern.filter;

/**
 * @author 高巍
 * @since 2020/12/18 9:17 上午
 */
public interface FilterInvoker<T> {
    default void invoke(T context){};
}
