/*
 *
 *  Copyright 2017-2019 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */
package org.pizza.common.web.mvc.handler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.UriTemplate;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Optional.*;

/**
 * @author gaowei
 */
public class WebMvcConfigSourcedRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    private final Map<String, HandlerMethod> handlerMethods = new LinkedHashMap<>();
    private final Environment environment;
    private final List<WebMvcRequestHandler> handlers;

    public WebMvcConfigSourcedRequestMappingHandlerMapping(
            Environment environment,
            List<WebMvcRequestHandler> handlers) {
        this.environment = environment;
        this.handlers = handlers;
    }

    @Override
    protected void initHandlerMethods() {
        logger.debug("initialising the handler methods");
        setOrder(Ordered.HIGHEST_PRECEDENCE + 1000);
        handlers.forEach(handler -> {
            Class<?> clazz = handler.getClass();
            if (isHandler(clazz)) {
                for (Method method : clazz.getMethods()) {
                    ConfigSourcedMapping mapper = AnnotationUtils.getAnnotation(method, ConfigSourcedMapping.class);
                    if (mapper != null) {
                        RequestMappingInfo mapping = super.getMappingForMethod(method, clazz);
                        HandlerMethod handlerMethod = super.createHandlerMethod(handler, method);
                        String mappingPath = mappingPath(mapper);
                        if (mappingPath != null) {
                            logger.info(String.format("Mapped URL path [%s] onto method [%s]", mappingPath, handlerMethod.toString()));
                            handlerMethods.put(mappingPath, handlerMethod);
                        } else {
                            for (String path : mapping.getPatternsCondition().getPatterns()) {
                                logger.info(String.format("Mapped URL path [%s] onto method [%s]", path, handlerMethod.toString()));
                                handlerMethods.put(path, handlerMethod);
                            }
                        }
                    }
                }
            }
        });
    }

    private String mappingPath(final ConfigSourcedMapping mapper) {
        final String key = mapper.propertyKey();
        final String target = mapper.value();
        return ofNullable(environment.getProperty(key))
                .map(input -> target.replace(String.format("${%s}", key), input))
                .orElse(null);
    }


    /**
     * The lookup handler method, maps the SEOMapper method to the request URL.
     * <p>If no mapping is found, or if the URL is disabled, it will simply drop through
     * to the standard 404 handling.</p>
     *
     * @param urlPath the path to match.
     * @param request the http servlet request.
     * @return The HandlerMethod if one was found.
     */
    @Override
    protected HandlerMethod lookupHandlerMethod(String urlPath, HttpServletRequest request) {
        logger.debug("looking up handler for path: " + urlPath);
        HandlerMethod handlerMethod = handlerMethods.get(urlPath);
        if (handlerMethod != null) {
            return handlerMethod;
        }
        for (String path : handlerMethods.keySet()) {
            UriTemplate template = new UriTemplate(path);
            if (template.matches(urlPath)) {
                request.setAttribute(
                        HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE,
                        template.match(urlPath));
                return handlerMethods.get(path);
            }
        }
        return null;
    }
}
