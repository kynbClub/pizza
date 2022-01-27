package org.pizza.event.model;

import lombok.Data;

import java.util.Date;

/**
 * @author 高巍
 * @since 2020/11/21 9:53 上午
 */
@Data
public class EventLog {
    private Long id;
    private Long timestamp;
    private String type;
    private String data;
    private Date createTime;
    private Date updateTime;
}
