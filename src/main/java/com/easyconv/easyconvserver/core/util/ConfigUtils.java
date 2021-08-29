package com.easyconv.easyconvserver.core.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

@Slf4j
public class ConfigUtils {

    private static Environment environment() {
        return BeanContext.getBean(Environment.class);
    }

    public static String getProperty(String key) {
        return environment().getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return environment().getProperty(key, defaultValue);
    }

}
