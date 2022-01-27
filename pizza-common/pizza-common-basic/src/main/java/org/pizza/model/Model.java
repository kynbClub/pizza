package org.pizza.model;

import java.io.Serializable;

/**
 * @author 高巍
 * @since  2020-04-07 12:54 下午
 * 领域模型
 */
public interface Model extends Serializable {
    /**
     * 检查属性
     * @param isThrow 是否抛出异常
     * @return 结果
     */
    default boolean check(boolean isThrow) {
        return true;
    }


}
