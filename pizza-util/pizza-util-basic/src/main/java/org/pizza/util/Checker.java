package org.pizza.util;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author gv
 * @since 2019年6月25日 14:40
 */
public interface Checker {

    @FunctionalInterface
    interface Verifier<X extends Exception> {
        boolean verify() throws X;
    }

    /**
     * 如果 假 则 抛出异常
     *
     * @param verifier
     * @param ExceptionSupplier 异常构造者
     * @param <X>
     * @throws X
     */
    static <X extends Exception, Y extends Exception> void ifNotThrow(Verifier<Y> verifier,
                                                                      Supplier<X> ExceptionSupplier) throws X, Y {
        ifThrow(!verifier.verify(), ExceptionSupplier);
    }

    /**
     * 如果 假 则 抛出异常
     *
     * @param condition
     * @param ExceptionSupplier 异常构造者
     * @param <X>
     * @throws X
     */
    static <X extends Exception> void ifNotThrow(boolean condition, Supplier<X> ExceptionSupplier) throws X {
        ifThrow(!condition, ExceptionSupplier);
    }

    /**
     * 如果 真 则 抛出异常
     *
     * @param verifier          校验
     * @param ExceptionSupplier 异常构造者
     * @param <X>               异常
     * @throws X
     */
    static <X extends Exception, Y extends Exception> void ifThrow(Verifier<Y> verifier, Supplier<X> ExceptionSupplier)
            throws X, Y {
        ifThrow(verifier.verify(), ExceptionSupplier);
    }

    /**
     * 如果 真 则 抛出异常
     *
     * @param verifier          校验
     * @param ExceptionSupplier 异常构造者
     * @param <X>               异常
     * @throws X
     */
    static <X extends Exception, Y extends Exception> void ifNotThrow(Verifier<Y> verifier, Consumer<Boolean> consumer, Supplier<X> ExceptionSupplier)
            throws X, Y {
        ifThrow(!verifier.verify(), consumer, ExceptionSupplier);
    }

    /**
     * 如果 真 则 抛出异常
     *
     * @param condition         条件
     * @param ExceptionSupplier 异常构造者
     * @param <X>               异常
     * @throws X
     */
    static <X extends Exception, Y extends Exception> void ifNotThrow(boolean condition, Consumer<Boolean> consumer, Supplier<X> ExceptionSupplier)
            throws X, Y {
        ifThrow(!condition, consumer, ExceptionSupplier);
    }

    /**
     * 如果 真 则 抛出异常
     *
     * @param condition         条件
     * @param ExceptionSupplier 异常构造者
     * @throws X
     */
    static <X extends Exception> void ifThrow(boolean condition, Supplier<X> ExceptionSupplier) throws X {
        if (condition) {
            throw ExceptionSupplier.get();
        }
    }

    /**
     * 如果 真 则 抛出异常
     *
     * @param condition         条件
     * @param ExceptionSupplier 异常构造者
     * @throws X
     */
    static <X extends Exception> void ifThrow(boolean condition, Consumer<Boolean> consumer, Supplier<X> ExceptionSupplier) throws X {
        if (condition) {
            consumer.accept(true);
            throw ExceptionSupplier.get();
        }
    }

    /**
     * 如果为空 则 抛出异常 兼容 Optional
     *
     * @param data
     * @param ExceptionSupplier
     * @param <X>
     * @throws X
     */
    static <X extends Exception> void ifNullThrow(Object data, Supplier<X> ExceptionSupplier) throws X {
        if (data == null || (data instanceof Optional) && (!((Optional) data).isPresent())) {
            throw ExceptionSupplier.get();
        }
    }

    /**
     * 如果为空 则 抛出异常 兼容 Optional
     *
     * @param data
     * @param ExceptionSupplier
     * @param <X>
     * @throws X
     */
    static <X extends Exception> void ifNullThrow(Object data, Consumer<Boolean> consumer, Supplier<X> ExceptionSupplier) throws X {
        if (data == null || (data instanceof Optional) && (!((Optional) data).isPresent())) {
            consumer.accept(true);
            throw ExceptionSupplier.get();
        }
    }

    /**
     * 如果为空 则 抛出异常
     *
     * @param data
     * @param ExceptionSupplier
     * @param <X>
     * @throws X
     */
    static <X extends Exception> void ifEmptyThrow(Object data, Supplier<X> ExceptionSupplier) throws X {
        if (data == null
                || (data instanceof String && StrUtil.isEmpty((CharSequence) data))
                || (data instanceof Collection && CollUtil.isEmpty((Collection<?>) data))
                || (data instanceof Map && CollUtil.isEmpty((Collection<?>) data))) {
            throw ExceptionSupplier.get();
        }
    }

    /**
     * true 则执行
     *
     * @param target
     * @param predicate
     * @param consumer
     * @param <T>
     * @return
     */
    static <T> boolean ifThen(T target, Predicate<T> predicate, Consumer<? super T> consumer) {
        if (predicate.test(target)) {
            consumer.accept(target);
            return true;
        }
        return false;
    }

    /**
     * true 则执行
     *
     * @param condition
     * @param consumer
     * @return
     */
    static boolean ifThen(Boolean condition, Consumer<Boolean> consumer) {
        if (condition) {
            consumer.accept(true);
            return true;
        }
        return false;
    }

    /**
     * false则执行
     *
     * @param target
     * @param predicate
     * @param consumer
     * @param <T>
     * @return
     */
    static <T> boolean ifNotThen(T target, Predicate<T> predicate, Consumer<? super T> consumer) {
        if (!predicate.test(target)) {
            consumer.accept(target);
            return true;
        }
        return false;
    }

    /**
     * 非空则执行
     *
     * @param target
     * @param consumer
     * @param <T>
     * @return
     */
    static <T> boolean ifPresent(T target, Consumer<? super T> consumer) {
        return ifThen(target, Objects::nonNull, consumer);
    }

    /**
     * 空则执行
     *
     * @param target
     * @param consumer
     * @param <T>
     * @return
     */
    static <T> boolean ifNullThen(T target, Consumer<? super T> consumer) {
        return ifThen(target, Objects::isNull, consumer);
    }

    /**
     * 是否不同
     *
     * @param target
     * @param expect
     * @param consumer
     * @param <T>
     * @return
     */
    static <T> boolean ifNotEqualsThen(T target, Object expect, Consumer<? super T> consumer) {
        return ifThen(target, t -> !Objects.equals(t, expect), consumer);
    }

    /**
     * 是否相同
     *
     * @param target
     * @param expect
     * @param consumer
     * @param <T>
     * @return
     */
    static <T> boolean ifEqualsThen(T target, Object expect, Consumer<? super T> consumer) {
        return ifThen(target, t -> Objects.equals(t, expect), consumer);
    }

    /**
     * 如果为空
     *
     * @param target
     * @param consumer
     * @param <T>
     * @return
     */
    static <T> boolean ifEmptyThen(T target, Consumer<? super T> consumer) {
        return ifThen(target,
                t -> Objects.isNull(t) || (t instanceof String && StrUtil.isEmpty((CharSequence) t))
                        || (t instanceof Collection && CollUtil.isEmpty((Collection<?>) t))
                        || (t instanceof Map && CollUtil.isEmpty((Collection<?>) t)),
                consumer);
    }

    /**
     * 非空白字符串则执行
     *
     * @param target
     * @param consumer
     * @return
     */
    static boolean ifStringNotEmpty(String target, Consumer<String> consumer) {
        return ifThen(target, StrUtil::isNotEmpty, consumer);
    }

    /**
     * 非空白字符串则执行
     *
     * @param target
     * @param consumer
     * @return
     */
    static boolean ifStringNotBlank(String target, Consumer<String> consumer) {
        return ifThen(target, StrUtil::isNotBlank, consumer);
    }

    /**
     * 如果是日期数组， 且0 为开始时间 1 为结束时间
     *
     * @param dateBetween
     * @param consumer
     * @return
     */
    static boolean ifDateBetween(List<Date> dateBetween, Consumer<List<Date>> consumer) {
        return ifThen(dateBetween, dates -> !CollUtil.isEmpty(dates) && dates.size() == 2, consumer);
    }

}
