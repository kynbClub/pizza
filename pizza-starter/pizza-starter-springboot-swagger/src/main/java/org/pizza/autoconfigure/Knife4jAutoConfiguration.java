
package org.pizza.autoconfigure;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import io.swagger.annotations.ApiOperation;
import org.pizza.properties.SwaggerGlobalResponse;
import org.pizza.properties.SwaggerProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * swagger配置
 *
 * @author gv
 */
@Configuration
@EnableOpenApi
@EnableConfigurationProperties(SwaggerProperties.class)
public class Knife4jAutoConfiguration {

    private static final String DEFAULT_EXCLUDE_PATH = "/error";
    private static final String BASE_PATH = "/**";
    private final OpenApiExtensionResolver openApiExtensionResolver;

    public Knife4jAutoConfiguration(OpenApiExtensionResolver openApiExtensionResolver) {
        this.openApiExtensionResolver = openApiExtensionResolver;
    }

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

        // exclude-path处理
        if (swaggerProperties.getExcludePath().size() == 0) {
            swaggerProperties.getExcludePath().add(DEFAULT_EXCLUDE_PATH);
        }
        //默认全局参数
        List<RequestParameter> requestParameters = new ArrayList<>();
        requestParameters.add(new RequestParameterBuilder().name("longitude").description("经度").in(ParameterType.HEADER).required(false).build());
        requestParameters.add(new RequestParameterBuilder().name("latitude").description("纬度").in(ParameterType.HEADER).required(false).build());
        requestParameters.add(new RequestParameterBuilder().name("deviceId").description("设备唯一标识").in(ParameterType.HEADER).required(false).build());
        requestParameters.add(new RequestParameterBuilder().name("deviceType").description("设备类型").in(ParameterType.HEADER).required(false).build());
        requestParameters.add(new RequestParameterBuilder().name("appVersion").description("应用版本").in(ParameterType.HEADER).required(false).build());
        requestParameters.add(new RequestParameterBuilder().name("apiVersion").description("接口版本").in(ParameterType.HEADER).required(false).build());
        requestParameters.add(new RequestParameterBuilder().name("accessToken").description("访问令牌").in(ParameterType.HEADER).required(false).build());
        requestParameters.add(new RequestParameterBuilder().name("clientType").description("应用类型").in(ParameterType.HEADER).required(false).build());
        String groupName="default";
        return new Docket(DocumentationType.OAS_30)
                .groupName(groupName)
//                .host(swaggerProperties.getHost())
                .globalResponses(HttpMethod.GET, swaggerGlobalResponse.get())
                .globalResponses(HttpMethod.POST, swaggerGlobalResponse.get())
                .globalResponses(HttpMethod.PUT, swaggerGlobalResponse.get())
                .globalResponses(HttpMethod.DELETE, swaggerGlobalResponse.get())
                .globalRequestParameters(requestParameters)
                .apiInfo(apiInfo(swaggerProperties))
                .select()
//                .apis(SwaggerUtil.basePackages(swaggerProperties.getBasePackages()))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Collections.singletonList(securitySchema()))
                .securityContexts(Collections.singletonList(securityContext()))
                .extensions(openApiExtensionResolver.buildExtensions(groupName))
//                .extensions(openApiExtensionResolver.buildSettingExtensions())
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
