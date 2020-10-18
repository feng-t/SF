package com.sf.bean;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class BeanFactory {
    public Set<Resource> classPaths;
    public Class<?>clazz;
    public Map<String,beanState> beans=new HashMap<>();
    BeanFactory(Class<?>clazz) throws Exception {
        this.clazz=clazz;
        classPaths=scanPath();
    }

    public abstract Set<Resource> scanPath() throws IOException;

    public void preLoad() throws ClassNotFoundException {
        for (Resource path : classPaths) {
            String beanName = path.getBeanClassName();
            Class<?> name = Class.forName(beanName);
            Constructor<?>[] constructors = name.getDeclaredConstructors();
            System.out.println("预加载");
//            if (constructors.length==1){
//
//            }

        }
    }
    static class beanState{
        public int state;
        public Object obj;
        beanState(int state,Object obj){
            this.state=state;
            this.obj=obj;
        }
    }
}
