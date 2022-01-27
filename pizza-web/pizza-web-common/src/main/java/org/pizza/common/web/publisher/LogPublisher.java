package org.pizza.common.web.publisher;


import org.pizza.common.web.event.LogEvent;

/**
 * @author 高巍
 * @since 2020-04-07 3:25 下午
 */
public interface LogPublisher {
    /**
     * 发布事件
     * @param event
     */
    void publishEvent(LogEvent event);
}
