package org.pizza.id.api;

/**
 * @author 高巍
 * @since 2019年06月12日 13:10
 */
public interface IdGenerator {
    long nextId(Class clazz);

    String nextId(String var1, Class var2);
}
