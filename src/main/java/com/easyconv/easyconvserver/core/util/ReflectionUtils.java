package com.easyconv.easyconvserver.core.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ReflectionUtils {
    public static <T> List<Field> getAllFields(T t){
        Objects.requireNonNull(t);

        Class<?> clazz = t.getClass();
        List<Field> fields = new ArrayList<>();
        while(clazz != null){
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    public static <T> Field getFieldByName(T t, String fieldName){
        Objects.requireNonNull(t);

        Field field = null;
        for(Field f : getAllFields(t)){
            if (f.getName().equals(fieldName)){
                field = f;
                break;
            }
        }
        if (field != null){
            field.setAccessible(true);
        }
        return field;
    }

    public static <T> T getFieldValue(Object obj, String fieldName){
        Objects.requireNonNull(obj);

        try {
            Field field = getFieldByName(obj, fieldName);
            return (T) field.get(obj);
        } catch (IllegalAccessException e){
            return null;
        }
    }
}
