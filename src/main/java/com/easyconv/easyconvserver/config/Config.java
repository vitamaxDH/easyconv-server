package com.easyconv.easyconvserver.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;


@Slf4j
@Configuration
@RequiredArgsConstructor
@PropertySource("classpath:resource.properties")
public class Config {

    private static Environment environment;

    @Autowired
    public Config(Environment environment) {
        this.environment = environment;
    }

    public static String getProperty(String key){
        return environment.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue){
        return environment.getProperty(key, defaultValue);
    }

}
