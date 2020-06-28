package com.sf.core.app;

import com.sf.core.BeanFactory;
import com.sf.core.annotation.AutoWired;
import com.sf.core.annotation.fun.Fun;
import com.sf.core.handler.DefaultExceptionHandler;
import com.sf.core.handler.ExceptionHandler;
import com.sf.core.load.DefaultClassLoader;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Application {

    public BeanFactory beanFactory = new BeanFactory();
    public ExceptionHandler eh;

    public static void run(Class<?> aClass, String[] args) throws Exception {
        //TODO 命令模式,处理传过来的参数
        //TODO 加载class
        new Application().run(aClass);
    }

    private void run(Class<?> aClass) throws Exception {
        new DefaultClassLoader(aClass).preloadClass().parsing(this::precessAnnotation);
        if (eh == null) {
            eh = new DefaultExceptionHandler();
        }
        actionBean();
    }

    /**
     * 处理bean
     */
    private void actionBean() {
        for (Class<?> aClass : beanFactory.classList) {
            Object o = beanFactory.bean.get(aClass);
            if (o!=null){
                Method[] methods = aClass.getDeclaredMethods();
                Field[] fields = aClass.getDeclaredFields();
                try {
                    for (Field field : fields) {
                        field.setAccessible(true);
                        if (field.get(o)==null&&field.isAnnotationPresent(AutoWired.class)){
                            field.set(o,beanFactory.bean.get(field.getType()));
                        }
                    }
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(Fun.class)) {
                            method.invoke(o);
                        }
                    }
                } catch (Exception e) {
                    eh.action(e, aClass);
                }
            }
        }
    }

    /**
     * TODO 处理 注解 //先加载到所有的类，然后处理
     *
     * @param aClass
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private void precessAnnotation(Class<?> aClass) throws Exception {
        if (!aClass.isAnnotation() && !aClass.isInterface()) {
            if (ExceptionHandler.class.isAssignableFrom(aClass) && !DefaultClassLoader.class.isAssignableFrom(aClass)) {
                eh = (ExceptionHandler) aClass.newInstance();
            } else {
                beanFactory.bean.put(aClass, aClass.newInstance());
            }
//            if (aClass.isAnnotationPresent(Services.class)) {
//                Services services = aClass.getAnnotation(Services.class);
//                Service[] value = services.value();
//                for (Service service : value) {
//                    int hour = service.hour();
//                    System.out.println(hour);
//                }
//            }
            beanFactory.classList.add(aClass);
        }
    }


}
