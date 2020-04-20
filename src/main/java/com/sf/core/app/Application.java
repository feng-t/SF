package com.sf.core.app;

import com.sf.core.annotation.Service;
import com.sf.core.annotation.Services;
import com.sf.core.annotation.fun.Fun;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Application {
    private Map<Class,Object> Objs=new ConcurrentHashMap<>(10);
    public static void run(Class<?> aClass, String[] args) throws Exception {
        //TODO 命令模式,处理传过来的参数
        //TODO 加载class
        new Application().run(aClass);
    }

    private void run(Class<?> aClass) throws Exception {
        MyClassLoader myClassLoader = new MyClassLoader(aClass);
        myClassLoader.parsing(this::precessAnnotation);
    }

    /**
     * TODO 处理 注解
     * @param aClass
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private void precessAnnotation(Class<?> aClass) throws Exception {
        if (!aClass.isAnnotation()){
            Object o =Objs.get(aClass);
            if (o==null){
                o=aClass.newInstance();
                Objs.put(aClass,o);
            }
            if (aClass.isAnnotationPresent(Services.class)){
                Services services = aClass.getAnnotation(Services.class);
                Service[] value = services.value();
                for (Service service : value) {
                    int hour = service.hour();
                    System.out.println(hour);
                }
            }
            Method[] methods = aClass.getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Fun.class)){
                    method.invoke(o);
                }
            }
        }
    }





}
