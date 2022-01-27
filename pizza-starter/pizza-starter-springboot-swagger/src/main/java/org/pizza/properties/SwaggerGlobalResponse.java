package org.pizza.properties;

import springfox.documentation.service.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 高巍
 * @since 2021/1/11 4:14 下午
 */
public interface SwaggerGlobalResponse {

    default List<Response> get() {
        return new ArrayList<>();
    }

}
