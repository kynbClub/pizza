package org.pizza.event.subscribe;

import org.pizza.event.model.ListenerMethodInvocation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

/**
 * @author 高巍
 * @since 2020/11/23 11:17 上午
 */
@Slf4j
public class AsyncListenerUncaughtExceptionHandler implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
        Class<?> declaringClass = method.getDeclaringClass();
        String simpleName = declaringClass.getSimpleName();
        ListenerMethodInvocation listenerMethodInvocation = new ListenerMethodInvocation();
        listenerMethodInvocation.setArgs(objects);
        listenerMethodInvocation.setMethodName(method.getName());
        listenerMethodInvocation.setParameterTypes(method.getParameterTypes());
        listenerMethodInvocation.setTargetClass(declaringClass);
        if (log.isErrorEnabled()) {
            log.error("事件监听器:{}，方法:{}", simpleName, method, throwable);
        }
        //TODO 持久化？
//        ReflectionUtils.invokeMethod(proxy, method, args, parameterTypes);
    }
}
