package com.easyconv.easyconvserver.core.util;

import org.springframework.context.ApplicationContext;

public class BeanContext {

    private static ApplicationContext context;

    public static void init(ApplicationContext context){
        BeanContext.context = context;
    }

    public static <T> T getBean(Class<T> clazz){
        return context.getBean(clazz);
    }

    public static <T> T getBean(String className){
        return (T) context.getBean(className);
    }

    public static <T> T getBean(String className, Class<T> clazz){
        return context.getBean(className, clazz);
    }

}
