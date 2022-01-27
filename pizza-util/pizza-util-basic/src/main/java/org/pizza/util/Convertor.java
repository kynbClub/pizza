package org.pizza.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author 高巍
 * @since 2020/11/17 2:42 下午
 */
public class Convertor extends Convert {

    /**
     * 自定义方式，转换List中不为Null的对象
     * @param from
     * @param function
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> List<T> convert(List<S> from, Function<S,T> function) {
        return from.stream().filter(Objects::nonNull).map(function).filter(Objects::nonNull).collect(Collectors.toList());
    }
    /**
     * 转换List中不为Null的对象
     *
     * @param from 源集合
     * @param to   目标类
     * @param <S>  源数据类型
     * @param <T>  目标数据类型
     * @return
     */
    public static <S, T> List<T> convert(List<S> from, Class<T> to) {
        return from.stream().filter(Objects::nonNull).map(element -> BeanUtil.copyProperties(element, to)).collect(Collectors.toList());
    }

    /**
     * 转换List中指定条件的对象
     *
     * @param from      源集合
     * @param to        目标类
     * @param predicate 条件
     * @param <T>
     * @param <S>
     * @return
     */
    public static <S, T> List<T> convert(List<S> from, Class<T> to, Predicate<S> predicate) {
        return from.stream().filter(predicate).map(element -> BeanUtil.copyProperties(element, to)).collect(Collectors.toList());
    }


}
