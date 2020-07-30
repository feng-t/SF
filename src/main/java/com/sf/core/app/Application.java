package com.sf.core.app;

import com.sf.core.BeanFactory;
import com.sf.core.annotation.*;
import com.sf.core.annotation.fun.Fun;
import com.sf.core.handler.DefaultExceptionHandler;
import com.sf.core.handler.ExceptionHandler;
import com.sf.core.load.DefaultClassLoader;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class Application {
    public List<AbstractAnnotationHandler> annohandlers=new ArrayList<>();
    public BeanFactory beanFactory = new BeanFactory();
    public ExceptionHandler eh;

    public static void run(Class<?> aClass, String[] args){
        //TODO 处理传过来的参数
        Application app = new Application();
        try {
            app.run(aClass);
        }catch (Exception e){
            if (app.eh == null) {
                app.eh = new DefaultExceptionHandler();
            }
            app.eh.action(e,aClass);
        }
    }

    private void run(Class<?> aClass) throws Exception {
        new DefaultClassLoader(aClass).preloadClass().parsing(this::precessAnnotation);
        if (eh == null) {
            eh = new DefaultExceptionHandler();
        }
        addAnnoHandler();
        actionBean();
    }

    private void addAnnoHandler() throws IllegalAccessException, InstantiationException {
        Set<Class<?>> set = beanFactory.classList;
        for (Class<?> aClass : set) {
            if (AbstractAnnotationHandler.class.isAssignableFrom(aClass)&&!aClass.isInterface()){
                annohandlers.add((AbstractAnnotationHandler) aClass.newInstance());
                set.remove(aClass);
            }
        }
    }

    /**
     * 处理bean
     */
    private void actionBean() {
        Class<?> c=null;
        try {
            for (Class<?> aClass : beanFactory.classList) {
                c = aClass;
                Object o = beanFactory.bean.get(aClass);
                if (o != null) {
                    if (annohandlers.isEmpty()) {
                        DefaultAnnotationHandler handler = new DefaultAnnotationHandler();
                        handler.action(aClass, o);
                    }else {
                        for (AbstractAnnotationHandler handler : annohandlers) {
                            handler.action(aClass, o);
                        }
                    }
                }
            }
        } catch (Exception e) {
            eh.action(e, c);
        }
    }

    /**
     * 处理注解
     * @param c
     * @param obj
     * @throws Exception
     */
    private void handlerAnnotation(Class<?> c, Object obj) throws Exception {
        Method[] methods = c.getDeclaredMethods();
        Field[] fields = c.getDeclaredFields();
        if (c.isAnnotationPresent(Services.class)) {
            Services services = c.getAnnotation(Services.class);
            Service[] value = services.value();
            for (Service service : value) {
                int hour = service.hour();
                System.out.println(hour);
            }
        }
        //处理变量的注解
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.get(obj) == null && field.isAnnotationPresent(AutoWired.class)) {
                Object o1 = beanFactory.bean.get(field.getType());
                if (o1 == null) {
                    throw new Exception("找不到bean " + field.getType());
                }
                field.set(obj, o1);
            }
        }
        //处理方法的注解
        for (Method method : methods) {
            if (method.isAnnotationPresent(Fun.class)) {
                method.invoke(obj);
            }

        }
    }

    /**
     * @param aClass
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private void precessAnnotation(Class<?> aClass) throws Exception {
        if (!aClass.isAnnotation() && !aClass.isInterface()) {
            if (ExceptionHandler.class.isAssignableFrom(aClass)) {
                eh = (ExceptionHandler) aClass.newInstance();
            } else {
                beanFactory.bean.put(aClass, aClass.newInstance());
            }
            beanFactory.classList.add(aClass);
        }
    }

    public static void main(String[] args) throws Exception {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();//.getContextClassLoader();
        InputStream in = loader.getResourceAsStream("application.properties");
        Properties properties = new Properties();
        properties.load(in);
        String test = properties.getProperty("test2");
        Class<?> name = Class.forName(test);
        System.out.println(name);
    }
}
