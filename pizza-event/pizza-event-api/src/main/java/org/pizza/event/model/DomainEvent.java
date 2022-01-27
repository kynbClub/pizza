package org.pizza.event.model;


import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * @author 高巍
 * @since 2020/11/20 4:22 下午
 */
public abstract class DomainEvent implements Serializable {

    private final Long timestamp = System.currentTimeMillis();

    @JSONField(serialize = false)
    public abstract boolean isStore();

    public Long getTimestamp() {
        return timestamp;
    }
}
