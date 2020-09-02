package com.sf.core.annotation.handler;

import com.sf.core.annotation.AbstractAnnotationHandler;
import com.sf.core.annotation.AutoWired;

import java.lang.reflect.Field;

public class AutoWiredHandler implements AbstractAnnotationHandler {
    @Override
    public void invoke(Class<?> c, Object obj) throws Exception {
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.get(obj)==null&&field.isAnnotationPresent(AutoWired.class)){
                Object o1 = beanFactory.beanMap.get(field.getType());
                if (o1 == null) {
                    throw new Exception("找不到bean " + field.getType());
                }
                field.set(obj, o1);
            }
        }
    }
}
