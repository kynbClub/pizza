package org.pizza.valid;

/**
 * @author gaowei
 */
public @interface Enum {
    String code() default "code";

    String message() default "message";
}
