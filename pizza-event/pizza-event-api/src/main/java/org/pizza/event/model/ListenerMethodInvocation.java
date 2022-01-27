package org.pizza.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 高巍
 * @since 2020/11/23 11:18 上午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListenerMethodInvocation implements Serializable {

    private Class targetClass;

    private String methodName;

    private Class[] parameterTypes;

    private Object[] args;
}
