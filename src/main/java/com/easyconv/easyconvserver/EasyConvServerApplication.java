package com.easyconv.easyconvserver;

import com.easyconv.easyconvserver.core.util.BeanContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@RequiredArgsConstructor
public class EasyConvServerApplication implements InitializingBean {

    private final ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(EasyConvServerApplication.class, args);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        BeanContext.init(context);
    }

}
