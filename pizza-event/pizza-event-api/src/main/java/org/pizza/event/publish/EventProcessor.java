package org.pizza.event.publish;

import org.pizza.event.model.DomainEvent;

/**
 * @author 高巍
 * @since 2020/11/21 10:34 上午
 */
public interface EventProcessor {
    void before(DomainEvent event);
    void after(DomainEvent event);
}
