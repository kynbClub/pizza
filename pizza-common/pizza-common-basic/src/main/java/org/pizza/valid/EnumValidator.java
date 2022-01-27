package org.pizza.valid;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author 高巍
 * @since  2019-12-26 9:41 上午
 */
public class EnumValidator implements ConstraintValidator<EnumValid, Object> {

    private static final String CODE_METHOD = "code";
    private static final String MESSAGE_METHOD = "message";
    private static final String DEFAULT_MESSAGE = "%s 值 %s 不在枚举范围内:%s";
    /**
     * 枚举类
     */
    private Class<?>[] classes;

    /**
     * 枚举类
     */
    private Enum[] values;
    /**
     * 参数名
     */
    private String name;

    @Override
    public void initialize(EnumValid annotation) {
        classes = annotation.target();
        values = annotation.values();
        name = annotation.name();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        //未赋值不做验证
        if (value == null) {
            return true;
        }
        //允许的值范围
        Set<TempEnum> allowValues = new HashSet<>();
        if (classes.length > 0) {
            for (Class<?> cl : classes) {
                try {
                    if (EnumValidI.class.isAssignableFrom(cl)) {
                        //枚举数组
                        Object[] objs = cl.getEnumConstants();
                        Method codeMethod = cl.getMethod(CODE_METHOD);
                        Method messageMethod = cl.getMethod(MESSAGE_METHOD);
                        for (Object obj : objs) {
                            String code = (String) codeMethod.invoke(obj);
                            String msg = (String) messageMethod.invoke(obj);
                            allowValues.add(new TempEnum(code, msg));
                        }
                    }
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        if (values != null && values.length > 0) {
            for (Enum anEnum : values) {
                allowValues.add(new TempEnum(anEnum.code(), anEnum.message()));
            }
        }
        boolean contains = allowValues.contains(new TempEnum(value.toString(), ""));
        if (contains) {
            return true;
        }

        this.failed(context, String.format(DEFAULT_MESSAGE, name, value, allowValues.toString()));
        return false;

    }

    private void failed(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }

    @Data
    @AllArgsConstructor
    private static class TempEnum {
        String code;
        String message;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            TempEnum tempEnum = (TempEnum) o;
            return Objects.equals(code, tempEnum.code);
        }

        @Override
        public int hashCode() {
            return Objects.hash(code);
        }

        @Override
        public String toString() {
            return "{" + code + "," + message + "}";
        }
    }
}
