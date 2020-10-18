package com.sf.bean;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class BeanFactory {
    public Set<Resource> classPaths;
    public Class<?> clazz;
    public Map<Class<?>, beanState> beans = new HashMap<>();

    BeanFactory(Class<?> clazz) throws Exception {
        this.clazz = clazz;
        classPaths = scanPath();
    }

    public abstract Set<Resource> scanPath() throws IOException;

    public void preLoad() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        for (Resource path : classPaths) {
            String beanName = path.getBeanClassName();
            loadBean(Class.forName(beanName));
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
    enum statue{
        start,init,run;
    }

    public <T> T getBean(Class<T> c) throws InstantiationException, IllegalAccessException {
        beanState state = beans.get(c);
        if (state == null) {
            state=new beanState(statue.init,null);
        }
        if (state.state!=statue.run){
            return loadBean(c);
        }
        Object obj = state.obj;
        return (T) obj;
    }

    public <T> T loadBean(Class<T> c) throws IllegalAccessException, InstantiationException {
        Constructor<?>[] constructors = c.getDeclaredConstructors();
        if (constructors.length==1){
            Constructor<?> constructor = constructors[0];
            Type[] parameterTypes = constructor.getGenericParameterTypes();
            if (parameterTypes.length==0){
                return c.newInstance();
            }
        }
        System.out.println("预加载");
        return null;
    }
}
