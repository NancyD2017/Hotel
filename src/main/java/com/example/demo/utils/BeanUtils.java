package com.example.demo.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;

@UtilityClass
public class BeanUtils {
    @SneakyThrows
    public void copyNonNullProperties(Object source, Object destination){
        Class<?> clazz = source.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for(Field field: fields){
            field.setAccessible(true);
            Object value = field.get(source);

            if (value != null){
                Field destField = destination.getClass().getDeclaredField(field.getName());
                destField.setAccessible(true);
                destField.set(destination, value);
            }
        }
    }
}
