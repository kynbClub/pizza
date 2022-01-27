package org.pizza.cache.cache.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

/**
 * @author 高巍
 * @since 2019年06月30日 14:42
 */
public class MethodKeyGenerator implements KeyGenerator {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public Object generate(Object target, Method method, Object... params) {
        StringBuilder sb = new StringBuilder();
        sb.append(target.getClass().getSimpleName()).append(".");
        sb.append(method.getName()).append("[");
        int index = 0;
        for (Object obj : params) {
            try {
                if (index > 0) {
                    sb.append(",");
                }
                sb.append(mapper.writeValueAsString(obj));
                index++;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
