package org.pizza.cache.autoconfigure;

import org.pizza.cache.cache.redis.CacheConfig;
import org.pizza.cache.cache.redis.MethodKeyGenerator;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 高巍
 * @since 2019年06月30日 14:42
 */
@Configuration
@EnableCaching
public class RedisCacheConfigurer extends CachingConfigurerSupport implements ApplicationContextAware {

    private ConfigurableApplicationContext configurableApplicationContext;

    @Bean
    @ConditionalOnBean(CacheConfig.class)
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
        Map<String, CacheConfig> beansOfType = configurableApplicationContext.getBeansOfType(CacheConfig.class);
        HashMap<String, Duration> configMap = new HashMap<>();
        beansOfType.values().forEach(config -> configMap.putAll(config.config()));
        //初始化缓存配置
        Map<String, RedisCacheConfiguration> initialCacheConfigurations = new HashMap<>();
        configMap.forEach((k, v) -> initialCacheConfigurations.put(k, createRedisCacheConfigurationWithTTL(v)));
        return new RedisCacheManager(redisCacheWriter, createRedisCacheConfigurationWithTTL(Duration.ofMinutes(60)), initialCacheConfigurations);
    }

//    @Bean
//    @Override
//    public KeyGenerator keyGenerator() {
//        return new MethodKeyGenerator();
//    }

    /**
     * 默认缓存配置
     *
     * @param ttl 超时时间
     * @return
     */
    private RedisCacheConfiguration createRedisCacheConfigurationWithTTL(Duration ttl) {
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair.fromSerializer(genericJackson2JsonRedisSerializer);
        return RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair).entryTtl(ttl);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
    }
}
