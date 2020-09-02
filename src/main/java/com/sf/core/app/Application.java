package com.sf.core.app;

import com.sf.core.BeanFactory;
import com.sf.core.annotation.AutoWired;
import com.sf.core.annotation.Service;
import com.sf.core.annotation.Services;
import com.sf.core.annotation.fun.Fun;
import com.sf.core.load.DefaultClassLoader;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class Application {

    private Application(){}

    public static Application getApp() {
        return instance.singleton.app;
    }

    public BeanFactory beanFactory = new BeanFactory();

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public static void run(Class<?> aClass, String[] args) throws Exception {
        //TODO 处理传过来的参数
        Application app = getApp();
        app.run(aClass);

    }

    private void run(Class<?> aClass) throws Exception {
        new DefaultClassLoader(aClass, "com.sf.core").preloadClass().parsing(beanFactory::precessAnnotation);
        beanFactory.addAnnHandler();
        beanFactory.actionBean();
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


    private enum instance{
        singleton;
        public Application app;

        public Application getApp() {
            if (app==null){
                app=new Application();
            }
            return app;
        }
    }
}
