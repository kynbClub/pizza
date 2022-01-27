package org.pizza.common.web.annotation;


import java.lang.annotation.*;

/**
 * @author 高巍
 * @since 2020-04-07 12:54 下午
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Logging {

    /**
     * 日志描述
     *
     * @return {String}
     */
    String value() default "日志记录";

    /**
     * 是否异步
     *
     * @return {boolean}
     */
    boolean async() default false;


}
