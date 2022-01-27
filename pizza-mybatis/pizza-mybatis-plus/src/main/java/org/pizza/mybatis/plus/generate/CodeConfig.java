package org.pizza.mybatis.plus.generate;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 高巍
 * @since 2020-04-08 11:28 上午
 */
@Data
@Accessors(chain = true)
public class CodeConfig {
    /**
     * 数据库驱动
     */
    private String driverName;
    /**
     * 数据库
     */
    private String username;
    /**
     * 数据库
     */
    private String password;
    /**
     * 数据库
     */
    private String url;
    /**
     * 作者
     */
    private String author;
}
