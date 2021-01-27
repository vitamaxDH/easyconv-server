package com.easyconv.easyconvserver.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;


@Slf4j
@PropertySource("classpath:resource.properties")
public class Config {

    private static Environment environment;

    public Config() {}

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
