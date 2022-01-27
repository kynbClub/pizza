package org.pizza.pattern.filter;

import cn.hutool.extra.spring.SpringUtil;

/**
 * @author 高巍
 * @since 2020/12/18 9:19 上午
 * 责任链工厂
 */
public class FilterChainFactory<T> {

    public static<T> FilterChain<T> buildFilterChain(Class... filterClsList) {
        FilterInvoker<T> last = new FilterInvoker<T>(){};
        FilterChain<T> filterChain = new FilterChain<T>();
        for(int i = filterClsList.length - 1; i >= 0; i--){
            FilterInvoker<T> next = last;
            Filter<T> filter = (Filter) SpringUtil.getBean(filterClsList[i]);
            last = new FilterInvoker<T>(){
                @Override
                public void invoke(T context) {
                    filter.doFilter(context, next);
                }
            };
        }
        filterChain.setHeader(last);
        return filterChain;
    }
}
