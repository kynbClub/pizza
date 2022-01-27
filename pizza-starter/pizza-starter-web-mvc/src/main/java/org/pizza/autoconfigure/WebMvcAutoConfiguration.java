package org.pizza.autoconfigure;

import org.pizza.common.web.mvc.handler.WebMvcConfigSourcedRequestMappingHandlerMapping;
import org.pizza.common.web.mvc.handler.WebMvcRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.pizza.common.web.mvc.exception.ExceptionAdvice;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.HandlerMapping;


/**
 * @author gv
 * @since 2018年10月14日 15:45
 * RestAutoConfiguration
 */
@Configuration
@AutoConfigureAfter({RestTemplateAutoConfiguration.class})
@Import(ExceptionAdvice.class)
public class WebMvcAutoConfiguration {

    /**
     * 默认导入实现
     *
     * @param restTemplateBuilder
     * @return
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .additionalMessageConverters(new FormHttpMessageConverter())
                .setConnectTimeout(Duration.ofSeconds(3))
                .setReadTimeout(Duration.ofSeconds(20))
                .build();
    }

//    @Bean
//    public CorsFilter corsFilter() {
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        final CorsConfiguration config = new CorsConfiguration();
//        // 允许cookies跨域
//        config.setAllowCredentials(true);
//        // 允许向该服务器提交请求的URI，*表示全部允许，在SpringMVC中，如果设成*，会自动转成当前请求头中的Origin
//        config.addAllowedOrigin("*");
//        // 允许访问的头信息,*表示全部
//        config.addAllowedHeader("*");
//        // 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
//        config.setMaxAge(18000L);
//        // 允许提交请求的方法，*表示全部允许
//        config.addAllowedMethod("OPTIONS");
//        config.addAllowedMethod("HEAD");
//        // 允许Get的请求方法
//        config.addAllowedMethod("GET");
//        config.addAllowedMethod("PUT");
//        config.addAllowedMethod("POST");
//        config.addAllowedMethod("DELETE");
//        config.addAllowedMethod("PATCH");
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }

    @Bean
    public HandlerMapping configSourcedRequestMappingHandlerMapping(Environment environment, @Autowired(required = false) List<WebMvcRequestHandler> handlers) {
        if (null == handlers) {
            handlers = new ArrayList<>();
        }
        return new WebMvcConfigSourcedRequestMappingHandlerMapping(environment, handlers);
    }
}
