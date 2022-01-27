package org.pizza.support;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.List;

/**
 * @author 高巍
 * @since 2019-12-27 11:15 上午
 */
public class Env implements EnvironmentAware {
    /**
     * 本地环境
     */
    public static final String PROFILE_LOCAL = "local";
    /**
     * 开发环境
     */
    public static final String PROFILE_DEV = "dev";
    /**
     * 测试环境
     */
    public static final String PROFILE_TEST = "test";
    /**
     * 预发布环境
     */
    public static final String PROFILE_PRE = "pre";
    /**
     * 生产环境
     */
    public static final String PROFILE_PROD = "prod";

    private Environment env;
    private List<String> activeProfiles;

    public boolean isDev() {
        return is(PROFILE_DEV);
    }

    public boolean isProd() {
        return is(PROFILE_PROD);
    }

    public boolean isLocal() {
        return is(PROFILE_LOCAL);
    }

    public boolean isTest() {
        return is(PROFILE_TEST);
    }

    public boolean iPre() {
        return is(PROFILE_PRE);
    }

    private boolean is(String env) {
        return activeProfiles.contains(env);
    }


    @Override
    public void setEnvironment(Environment env) {
        this.env = env;
        String[] activeProfiles = this.env.getActiveProfiles();
        List<String> strings = Arrays.asList(activeProfiles);
        this.activeProfiles = strings;
    }
}
