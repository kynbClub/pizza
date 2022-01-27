package org.pizza.event.autoconfigure;

import org.pizza.event.repository.EventRepository;
import org.pizza.event.repository.MysqlEventRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author 高巍
 * @since 2020-03-05 9:36 上午
 */
@Configuration
public class JdbcEventRepositoryAutoConfiguration {

    @Bean
    @ConditionalOnClass(JdbcTemplate.class)
    @ConditionalOnMissingBean(EventRepository.class)
    public EventRepository eventRepository(JdbcTemplate jdbcTemplate) {
        return new MysqlEventRepository(jdbcTemplate);
    }



}
