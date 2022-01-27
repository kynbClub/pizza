package org.pizza.event.subscribe;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 高巍
 * @since 2020/12/15 10:00 上午
 */
@Slf4j
public abstract class AbstractEventHandler<E> {

    public abstract void handle(E event);

}
