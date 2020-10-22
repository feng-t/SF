package com.sf.bean;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BeanFactory2 {
    public final Set<Resource> classPaths;
    public final Class<?> clazz;
    private final Map<Class<?>, beanState> beans = new ConcurrentHashMap<>();

    BeanFactory2(Class<?> clazz) throws Exception {
        this.clazz = clazz;
        classPaths = scanPath();
    }

    public abstract Set<Resource> scanPath() throws IOException;

    public void preLoad() throws Exception {
        for (Resource path : classPaths) {
            String beanName = path.getBeanClassName();
            Class<?> preferBean = Class.forName(beanName);
            if (!preferBean.isAnnotation()){
                loadBean(preferBean);
            }
        }
    }

    static class beanState {
        public statue state;
        public Object obj;

        beanState(statue state, Object obj) {
            this.state = state;
            this.obj = obj;
        }
    }

    enum statue {
        start, init, run
    }

    public <T> T getBean(Class<T> c) throws Exception {
        beanState state = getObj(c);
        if (state.state == statue.start) {
            return loadBean(c);
        }
        Object obj = state.obj;
        return (T) obj;
    }

    private <T> T loadBean(Class<T> c) throws Exception {
        if (c.isAnnotation()){
            throw new Exception("无法创建注解");
        }
        if (Modifier.isAbstract(c.getModifiers())){
            //抽象类

        }
        if (c.isInterface()){
            //接口
        }
        //TODO 无法解决子类，接口
        beanState state = getObj(c);
        if (state.obj == null && state.state == statue.start) {
            state.state = statue.init;
            T obj = null;
            Constructor<?>[] constructors = c.getDeclaredConstructors();
            if (constructors.length == 1) {
                Constructor<?> constructor = constructors[0];
                Class<?>[] types = constructor.getParameterTypes();
                if (types.length == 0) {
                    obj = c.newInstance();
                } else {
                    Object[] parameters = new Object[types.length];
                    for (int i = 0; i < types.length; i++) {
                        parameters[i] = loadBean(types[i]);
                    }
                    constructor.setAccessible(true);
                    obj = (T) constructor.newInstance(parameters);
                }
            }
            if (obj != null) {
                state.obj = obj;
                state.state = statue.run;
            }
            return obj;
        } else if (state.state == statue.init) {
            throw new Exception("循环依赖 " + c.getName());
        } else {
            return (T) state.obj;
        }
    }

    /**
     * 创建bean
     * @param beanClass
     * @param parameters
     * @param <T>
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    public <T> T createBean(Class<T>beanClass,Object... parameters) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?>[] classes = new Class<?>[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            classes[i]=parameters.getClass();
        }
        Constructor<T> constructor = beanClass.getDeclaredConstructor(classes);
        return constructor.newInstance(parameters);
    }

    public beanState getObj(Class<?> c) {
        beanState state = beans.get(c);
        if (state == null) {
            synchronized (this) {
                state = new beanState(statue.start, null);
            }
            beans.put(c, state);
        }
        return state;
    }

}
