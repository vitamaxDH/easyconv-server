package com.easyconv.easyconvserver.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;


@Slf4j
public class Config {

    private static Environment environment;

    private Config(){}

    public static void setEnvironment(Environment environment) {
        Config.environment = environment;
    }

    public static String getProperty(String key){
        return environment.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue){
        return environment.getProperty(key, defaultValue);
    }

}
