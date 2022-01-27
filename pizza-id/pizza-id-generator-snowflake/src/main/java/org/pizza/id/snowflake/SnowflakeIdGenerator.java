package org.pizza.id.snowflake;

import org.pizza.id.api.IdGenerator;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author gv
 * @since 2019年3月22日 15:45
 * id generate
 */
@Slf4j
public class SnowflakeIdGenerator implements IdGenerator {
    /**
     * 定义map，配置实体类对应的IdWorker
     */
    private static final ConcurrentMap<Class, SnowflakeIdWorker> ID_WORK_MAP = new ConcurrentHashMap();
    /**
     * 通过
     */
    private long workerId;
    private long dataCenterId;

    public SnowflakeIdGenerator(long workerId, long dataCenterId) {
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    public SnowflakeIdGenerator() {
        this.workerId = 0L;
        this.dataCenterId = 0L;
    }

    @Override
    public long nextId(Class clazz) {
        SnowflakeIdWorker snowflakeIdWorker = ID_WORK_MAP.get(clazz);
        //double check
        if (snowflakeIdWorker == null) {
            synchronized (ID_WORK_MAP) {
                snowflakeIdWorker = ID_WORK_MAP.get(clazz);
                if (snowflakeIdWorker == null) {
                    snowflakeIdWorker = new SnowflakeIdWorker(workerId, dataCenterId);
                    ID_WORK_MAP.put(clazz, snowflakeIdWorker);
                }
            }
        }
        return snowflakeIdWorker.nextId();
    }

    @Override
    public String nextId(String prefix, Class clazz) {
        return prefix + this.nextId(clazz);
    }
}