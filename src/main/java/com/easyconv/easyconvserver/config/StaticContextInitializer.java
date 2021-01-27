package com.easyconv.easyconvserver.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@PropertySource({"classpath:resource.properties"})
@RequiredArgsConstructor
@Component
public class StaticContextInitializer {

    private final Environment environment;

    @PostConstruct
    public void init(){
        Config.setEnvironment(environment);
    }
}
