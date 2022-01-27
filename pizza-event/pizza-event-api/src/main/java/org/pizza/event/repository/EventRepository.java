package org.pizza.event.repository;

import org.pizza.event.model.DomainEvent;
import org.pizza.event.model.EventLog;

import java.util.List;
import java.util.Optional;

/**
 * @author 高巍
 * @since 2020/11/20 4:56 下午
 * 事件仓储
 */
public interface EventRepository {
    boolean saveOrUpdate(DomainEvent event);

    Optional<EventLog> get(String eventId);

    void init();

    List<EventLog> page(int size,String type,Long timestamp);
}
