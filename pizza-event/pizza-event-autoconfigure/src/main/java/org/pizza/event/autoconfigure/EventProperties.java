package org.pizza.event.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * @author 高巍
 * @since 2020/11/21 3:45 下午
 */
@ConfigurationProperties(prefix = "event.pool")
public class EventProperties {
    private String threadNamePrefix = "event-pool-";
    private int queueCapacity = 2000;
    private int coreSize = Runtime.getRuntime().availableProcessors();
    private int maxSize = 100;
    private int keepAlive = 60;

    public String getThreadNamePrefix() {
        return threadNamePrefix;
    }

    public void setThreadNamePrefix(String threadNamePrefix) {
        this.threadNamePrefix = threadNamePrefix;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public int getCoreSize() {
        return coreSize;
    }

    public void setCoreSize(int coreSize) {
        this.coreSize = coreSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }


    public int getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(int keepAlive) {
        this.keepAlive = keepAlive;
    }
}
