package org.pizza.event.publish;

import org.pizza.event.model.DomainEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 高巍
 * @since 2020/11/20 5:08 下午
 */
@Slf4j
public class EventBus implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;

    private Set<EventProcessor> processors = new LinkedHashSet<>();


    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    public void publishEvent(DomainEvent domainEvent) {
        processors.forEach(p -> p.before(domainEvent));
        publisher.publishEvent(domainEvent);
        processors.forEach(p -> p.after(domainEvent));
    }

    public EventBus addEventProcessor(EventProcessor eventProcessor) {
        processors.add(eventProcessor);
        return this;
    }

    public EventBus addEventProcessors(List<EventProcessor> eventProcessors) {
        processors.addAll(eventProcessors);
        return this;
    }

}
