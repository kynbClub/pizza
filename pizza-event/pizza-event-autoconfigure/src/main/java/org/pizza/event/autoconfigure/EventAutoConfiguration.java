package org.pizza.event.autoconfigure;

import cn.hutool.core.thread.NamedThreadFactory;
import org.pizza.event.publish.EventBus;
import org.pizza.event.publish.EventProcessor;
import org.pizza.event.publish.StoreEventProcessor;
import org.pizza.event.repository.EventRepository;
import org.pizza.event.subscribe.AsyncListenerUncaughtExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.AsyncConfigurer;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author 高巍
 * @since 2020-03-05 8:57 上午
 */
@Configuration(proxyBeanMethods = false)
@Import({JdbcEventRepositoryAutoConfiguration.class})
@Slf4j
public class EventAutoConfiguration {

    @Bean
    public EventBus eventBus(List<EventProcessor> eventProcessors) {
        return new EventBus().addEventProcessors(eventProcessors);
    }

    @Bean
    public StoreEventProcessor storeEventProcessor(@Autowired(required = false) EventRepository eventRepository) {
        return new StoreEventProcessor(eventRepository);
    }

//    如果设置了线程池会默认将所有的事件都异步执行
//    @Bean
//    public SimpleApplicationEventMulticaster applicationEventMulticaster(EventProperties eventProperties) {
//        SimpleApplicationEventMulticaster simpleApplicationEventMulticaster = new SimpleApplicationEventMulticaster();
//        org.pizza.thread.ThreadPoolExecutor eventThreadPoolExecutor = new org.pizza.thread.ThreadPoolExecutor(
//                eventProperties.getCoreSize()
//                , eventProperties.getMaxSize()
//                , eventProperties.getKeepAlive()
//                , TimeUnit.SECONDS
//                , new LinkedBlockingQueue<>(eventProperties.getQueueCapacity())
//                , new NamedThreadFactory(eventProperties.getThreadNamePrefix(), false)
//                , (r, executor) -> log.warn("事件线程池已满，无法完成任务：{}", r.toString()));
//        simpleApplicationEventMulticaster.setTaskExecutor(eventThreadPoolExecutor);
//        simpleApplicationEventMulticaster.setErrorHandler(throwable -> log.error("事件监听器处理出现异常", throwable));
//        return simpleApplicationEventMulticaster;
//    }

//    @Bean
//    public EventProperties eventProperties() {
//        return new EventProperties();
//    }

    @Bean
    public AsyncConfigurer asyncConfigurer(TaskExecutionProperties executionProperties) {
        org.pizza.thread.ThreadPoolExecutor asyncThreadPoolExecutor = new org.pizza.thread.ThreadPoolExecutor(
                executionProperties.getPool().getCoreSize()
                , executionProperties.getPool().getMaxSize()
                , executionProperties.getPool().getKeepAlive().getSeconds()
                , TimeUnit.SECONDS
                , new LinkedBlockingQueue<>(executionProperties.getPool().getQueueCapacity())
                , new NamedThreadFactory(executionProperties.getThreadNamePrefix(), false)
                , (r, executor) -> log.error("事件线程池已满，无法完成任务：{}", r.toString()));
        return new AsyncConfigurer() {
            @Override
            public Executor getAsyncExecutor() {
                return asyncThreadPoolExecutor;
            }

            @Override
            public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
                return new AsyncListenerUncaughtExceptionHandler();
            }
        };
    }
}
