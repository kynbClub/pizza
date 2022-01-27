package org.pizza.pattern.filter;

/**
 * @author 高巍
 * @since 2020/12/18 9:17 上午
 */
public interface Filter<T> {
    void doFilter(T context, FilterInvoker<T> nextFilter);
}
