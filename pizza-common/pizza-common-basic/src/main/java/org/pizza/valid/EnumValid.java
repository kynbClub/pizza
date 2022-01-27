package org.pizza.valid;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author 高巍
 * @since 2019-12-26 9:41 上午
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EnumValidator.class})
@Documented
public @interface EnumValid {
    /**
     * 允许的值范围
     *
     * @return
     */
    Enum[] values() default {};

    /**
     * 错误消息
     *
     * @return
     */
    String message() default "不在枚举要求范围内";

    String name();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 限定的目标枚举类
     *
     * @return
     */
    Class<? extends EnumValidI>[] target() default {};

}