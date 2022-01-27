package org.pizza.autoconfigure;

import org.pizza.mybatis.plus.support.MyMetaObjectHandler;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Properties;


/**
 * @author gv
 * @since 2018年10月14日 15:45
 * MyMybatisPlusAutoConfiguration
 */
@Configuration
@AutoConfigureAfter({MybatisPlusAutoConfiguration.class})
public class MyMybatisPlusAutoConfiguration {
    /**
     * 分页插件
     */
    @Bean
    @ConditionalOnMissingBean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }


    @Bean
    @ConditionalOnMissingBean
    public MyMetaObjectHandler myMetaObjectHandler() {
        return new MyMetaObjectHandler();
    }
}
