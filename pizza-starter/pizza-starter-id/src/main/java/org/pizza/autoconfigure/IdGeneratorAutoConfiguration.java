package org.pizza.autoconfigure;

import org.pizza.id.api.IdGenerator;
import org.pizza.id.snowflake.SnowflakeIdGenerator;
import org.pizza.properties.IdProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 高巍
 * @since  2020/10/13 3:40 下午
 */
@Configuration
@EnableConfigurationProperties({IdProperties.class})
public class IdGeneratorAutoConfiguration {
    private final IdProperties idProperties;
    public IdGeneratorAutoConfiguration(IdProperties idProperties) {
        this.idProperties = idProperties;
    }

    @Bean
    public IdGenerator idGenerator() {
        this.idProperties.setWorkId(idProperties.getWorkId());
        return new SnowflakeIdGenerator(this.idProperties.getWorkId(), this.idProperties.getDataCenterId());
    }

}
