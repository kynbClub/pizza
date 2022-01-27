package org.pizza.event.publish;

import com.alibaba.fastjson.JSON;
import org.pizza.event.model.DomainEvent;
import org.pizza.event.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 高巍
 * @since 2020/11/21 10:41 上午
 */
@Slf4j
public class StoreEventProcessor implements EventProcessor {
    private final EventRepository eventRepository;

    public StoreEventProcessor(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void before(DomainEvent event) {
        if (event.isStore()) {
            if (eventRepository != null) {
                eventRepository.saveOrUpdate(event);
            } else {
                log.warn("EventRepository not found , DomainEvent:{}", JSON.toJSONString(event));
            }
        }
    }

    @Override
    public void after(DomainEvent event) {

    }
}
