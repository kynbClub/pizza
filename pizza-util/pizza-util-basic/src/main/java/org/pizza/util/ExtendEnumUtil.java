package org.pizza.util;

import cn.hutool.core.util.EnumUtil;

import java.util.LinkedHashMap;

/**
 * @author 高巍
 * @since 2020/10/13 2:22 下午
 */
public class ExtendEnumUtil extends EnumUtil {
    public ExtendEnumUtil() {
    }

    public static <T extends Enum<T>> T get(Class<T> enumClass, String name, RuntimeException Exception) {
        LinkedHashMap<String, T> enumMap = EnumUtil.getEnumMap(enumClass);
        T t = enumMap.get(name);
        if (t == null && null != Exception) {
            throw Exception;
        } else {
            return t;
        }
    }

    public static <T extends Enum<T>> T get(Class<T> enumClass, Enum e, RuntimeException Exception) {
        LinkedHashMap<String, T> enumMap = EnumUtil.getEnumMap(enumClass);
        T t = enumMap.get(e.name());
        if (t == null && null != Exception) {
            throw Exception;
        } else {
            return t;
        }
    }
}
