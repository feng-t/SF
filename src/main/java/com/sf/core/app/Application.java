package com.sf.core.app;

import com.sf.core.BeanFactory;
import com.sf.core.annotation.AbstractAnnotationHandler;
import com.sf.core.annotation.AutoWired;
import com.sf.core.annotation.Service;
import com.sf.core.annotation.Services;
import com.sf.core.annotation.fun.Fun;
import com.sf.core.handler.DefaultExceptionHandler;
import com.sf.core.handler.ExceptionHandler;
import com.sf.core.load.DefaultClassLoader;

import java.lang.reflect.*;
import java.util.*;

public class Application {
    public List<AbstractAnnotationHandler> annohandlers = new ArrayList<>();
    public static BeanFactory beanFactory = new BeanFactory();
    public ExceptionHandler eh;

    public static void run(Class<?> aClass, String[] args) {
        //TODO 处理传过来的参数
        Application app = new Application();
        try {
            app.run(aClass);
        } catch (Exception e) {
            if (app.eh == null) {
                app.eh = new DefaultExceptionHandler();
            }
            app.eh.action(e, aClass);
        }
    }

    private void run(Class<?> aClass) throws Exception {
        new DefaultClassLoader(aClass, "com.sf.core").preloadClass().parsing(this::precessAnnotation);
        if (eh == null) {
            eh = new DefaultExceptionHandler();
        }
        addAnnHandler();
        actionBean();
    }

    private void addAnnHandler() throws IllegalAccessException, InstantiationException {
        Set<Class<?>> set = beanFactory.classList;
        Iterator<Class<?>> it = set.iterator();
        while (it.hasNext()) {
            Class<?> aClass = it.next();
            if (AbstractAnnotationHandler.class.isAssignableFrom(aClass) && !aClass.isInterface()) {
                annohandlers.add((AbstractAnnotationHandler) aClass.newInstance());
                it.remove();
            }
        }
    }

    /**
     * 处理bean
     */
    private void actionBean() {
        Class<?> c = null;
        try {
            for (Class<?> aClass : beanFactory.classList) {
                c = aClass;
                Object o = beanFactory.bean.get(aClass);
                if (o != null) {
                    if (annohandlers.isEmpty()) {
                        AbstractAnnotationHandler h = new AbstractAnnotationHandler() {
                        };
                        h.action(c, o);
                    } else {
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
     *
     * @param c
     * @param obj
     * @throws Exception
     */
    @Deprecated
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
     */
    private void precessAnnotation(Class<?> aClass) {
        if (!aClass.isAnnotation() && !aClass.isInterface() && !Modifier.isAbstract(aClass.getModifiers())) {
            try {
                if (ExceptionHandler.class.isAssignableFrom(aClass)) {
                    eh = (ExceptionHandler) aClass.newInstance();
                }
                beanFactory.bean.put(aClass, aClass.newInstance());
                beanFactory.classList.add(aClass);
            } catch (Exception ignore) {
            }
        }
    }


    public static void main(String[] args) throws Exception {
        Class<DefaultClassLoader> loaderClass = DefaultClassLoader.class;


        Constructor<?>[] constructors = loaderClass.getConstructors();
        for (Constructor<?> constructor : constructors) {
            Type[] types = constructor.getGenericParameterTypes();
        }
        Constructor<DefaultClassLoader> constructor = loaderClass.getConstructor();
        System.out.println();


//        ClassLoader loader = Thread.currentThread().getContextClassLoader();//.getContextClassLoader();
//        InputStream in = loader.getResourceAsStream("application.properties");
//        Properties properties = new Properties();
//        properties.load(in);
//        String test = properties.getProperty("test2");
//        Class<?> name = Class.forName(test);
//        System.out.println(name);
    }
}
