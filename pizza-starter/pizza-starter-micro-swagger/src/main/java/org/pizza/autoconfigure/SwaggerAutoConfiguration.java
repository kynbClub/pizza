
package org.pizza.autoconfigure;

import io.swagger.annotations.ApiOperation;
import org.pizza.properties.SwaggerGlobalResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMethod;
import org.pizza.properties.SwaggerProperties;
//import org.pizza.util.SwaggerUtil;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * swagger配置
 *
 * @author gv
 */
@Configuration
@EnableKnife4j
@EnableSwagger2WebMvc
@EnableConfigurationProperties(SwaggerProperties.class)
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerAutoConfiguration {

    private static final String DEFAULT_EXCLUDE_PATH = "/error";
    private static final String BASE_PATH = "/**";

    @Bean
    @ConditionalOnMissingBean
    public SwaggerProperties swaggerProperties() {
        return new SwaggerProperties();
    }

    @Bean
    public Docket api(SwaggerProperties swaggerProperties, SwaggerGlobalResponse swaggerGlobalResponse) {
        // base-path处理
        if (swaggerProperties.getBasePath().size() == 0) {
            swaggerProperties.getBasePath().add(BASE_PATH);
        }
        //noinspection unchecked
//        List<Predicate<String>> basePath = new ArrayList();
//        swaggerProperties.getBasePath().forEach(path -> basePath.add(PathSelectors.ant(path)));

        // exclude-path处理
        if (swaggerProperties.getExcludePath().size() == 0) {
            swaggerProperties.getExcludePath().add(DEFAULT_EXCLUDE_PATH);
        }
//        List<Predicate<String>> excludePath = new ArrayList<>();
//        swaggerProperties.getExcludePath().forEach(path -> excludePath.add(PathSelectors.ant(path)));
        //noinspection Guava
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("默认接口")
                .globalResponses(HttpMethod.GET, swaggerGlobalResponse.get())
                .globalResponses(HttpMethod.POST, swaggerGlobalResponse.get())
                .globalResponses(HttpMethod.PUT, swaggerGlobalResponse.get())
                .globalResponses(HttpMethod.DELETE, swaggerGlobalResponse.get())
                .host(swaggerProperties.getHost())
                .apiInfo(apiInfo(swaggerProperties)).select()
//                .apis(SwaggerUtil.basePackages(swaggerProperties.getBasePackages()))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
//                .paths(Predicates.and(Predicates.not(Predicates.or(excludePath)), Predicates.or(basePath)))
                .build()
                .securitySchemes(Collections.singletonList(securitySchema()))
                .securityContexts(Collections.singletonList(securityContext()))
                .pathMapping("/");
    }

    /**
     * 配置默认的全局鉴权策略的开关，通过正则表达式进行匹配；默认匹配所有URL
     *
     * @return
     */
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(swaggerProperties().getAuthorization().getAuthRegex()))
                .build();
    }

    /**
     * 默认的全局鉴权策略
     *
     * @return
     */
    private List<SecurityReference> defaultAuth() {
        ArrayList<AuthorizationScope> authorizationScopeList = new ArrayList<>();
        swaggerProperties().getAuthorization().getAuthorizationScopeList().forEach(authorizationScope -> authorizationScopeList.add(new AuthorizationScope(authorizationScope.getScope(), authorizationScope.getDescription())));
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[authorizationScopeList.size()];
        return Collections.singletonList(SecurityReference.builder()
                .reference(swaggerProperties().getAuthorization().getName())
                .scopes(authorizationScopeList.toArray(authorizationScopes))
                .build());
    }


    private OAuth securitySchema() {
        ArrayList<AuthorizationScope> authorizationScopeList = new ArrayList<>();
        swaggerProperties().getAuthorization().getAuthorizationScopeList().forEach(authorizationScope -> authorizationScopeList.add(new AuthorizationScope(authorizationScope.getScope(), authorizationScope.getDescription())));
        ArrayList<GrantType> grantTypes = new ArrayList<>();
        swaggerProperties().getAuthorization().getTokenUrlList().forEach(tokenUrl -> grantTypes.add(new ResourceOwnerPasswordCredentialsGrant(tokenUrl)));
        return new OAuth(swaggerProperties().getAuthorization().getName(), authorizationScopeList, grantTypes);
    }

    private ApiInfo apiInfo(SwaggerProperties swaggerProperties) {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .license(swaggerProperties.getLicense())
                .licenseUrl(swaggerProperties.getLicenseUrl())
                .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
                .contact(new Contact(swaggerProperties.getContact().getName(), swaggerProperties.getContact().getUrl(), swaggerProperties.getContact().getEmail()))
                .version(swaggerProperties.getVersion())
                .build();
    }

}
