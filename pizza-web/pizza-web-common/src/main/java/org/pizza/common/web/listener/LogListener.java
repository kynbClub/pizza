package org.pizza.common.web.listener;


import org.pizza.common.web.event.LogEvent;

/**
 * @author 高巍
 * @since 2020-04-08 8:27 上午
 */
public interface LogListener {

    /**
     * 处理事件
     * @param event
     */
    void process(LogEvent event);
}
