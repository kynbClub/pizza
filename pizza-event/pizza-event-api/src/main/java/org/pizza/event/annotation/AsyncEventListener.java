package org.pizza.event.annotation;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.AliasFor;
import org.springframework.scheduling.annotation.Async;

import java.lang.annotation.*;

/**
 * @author 高巍
 * @since 2020/11/21 2:39 下午
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Async
@EventListener
public @interface AsyncEventListener {
    @AliasFor(annotation = EventListener.class)
    Class<?>[] value() default {};

    @AliasFor(annotation = EventListener.class)
    Class<?>[] classes() default {};

    @AliasFor(annotation = EventListener.class)
    String condition() default "";
}
