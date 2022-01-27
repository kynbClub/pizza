package org.pizza.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 高巍
 * @since 2019年05月05日 16:43
 * 微服务配置参数
 */
@ConfigurationProperties(prefix = "did")
public class IdProperties {

    private String name;

    private long workId = 0;

    private long dataCenterId = 0;

    private long maxWorkId = 32L;

    private Zk zk;
    private Redis redis;

    public static class Zk {
        private boolean enabled = false;
        private String address;
        private int port;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

    }
    public static class Redis {
        private boolean enabled = false;
        private String address;
        private int port;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

    }
    public IdProperties() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getWorkId() {
        return workId;
    }

    public void setWorkId(long workId) {
        this.workId = workId;
    }

    public long getMaxWorkId() {
        return maxWorkId;
    }

    public void setMaxWorkId(long maxWorkId) {
        this.maxWorkId = maxWorkId;
    }

    public long getDataCenterId() {
        return dataCenterId;
    }

    public void setDataCenterId(long dataCenterId) {
        this.dataCenterId = dataCenterId;
    }

    public Zk getZk() {
        return zk;
    }

    public void setZk(Zk zk) {
        this.zk = zk;
    }

    public Redis getRedis() {
        return redis;
    }

    public void setRedis(Redis redis) {
        this.redis = redis;
    }
}
