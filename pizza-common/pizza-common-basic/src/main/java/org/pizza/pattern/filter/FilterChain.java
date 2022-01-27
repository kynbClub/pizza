package org.pizza.pattern.filter;

/**
 * @author 高巍
 * @since 2020/12/18 9:18 上午
 */
public class FilterChain<T> {

    private FilterInvoker<T> header;

    public void doFilter(T context){
        header.invoke(context);
    }

    public void setHeader(FilterInvoker<T> header) {
        this.header = header;
    }
}
