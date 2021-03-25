package com.sf.croe.bean;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 应该存放，变量，方法，注解，构造方法
 */
public class Resource<T> implements Comparable<Resource<T>> {

    private final Class<T> beanClass;
    /**
     * 三种创建方式，单例，多例，即时，默认单例
     */
    private final List<T> cacheBean=Collections.synchronizedList(new ArrayList<>(1));
    private Constructor<?>[] constructors;

    private int state = State.init;


    public Resource(Class<T> beanClass) {
        this.beanClass = beanClass;
    }


    public synchronized Constructor<?>[] getConstructors() throws ClassNotFoundException {
        if (constructors == null) {
            constructors = getBeanClass().getDeclaredConstructors();
        }
        return constructors;
    }

    public Class<T> getBeanClass() throws ClassNotFoundException {
        return beanClass;
    }

    /**
     * 小的在前 TODO 比较方式要改
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(Resource o) {
        return state > o.state ? 1 : -1;
    }

    public Set<Field> findFieldsAnnotation(Class<? extends Annotation> target) throws ClassNotFoundException {
        Field[] fields = getBeanClass().getDeclaredFields();
        Set<Field> set = new HashSet<>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(target)) {
                set.add(field);
            } else if (findAnnotation(field.getDeclaredAnnotations(), target)) {
                set.add(field);
            }
        }
        return set;
    }

    /**
     * 查看class 是否存在注解
     *
     * @param target
     * @return
     */
    public boolean findClassAnnotation(Class<? extends Annotation> target) throws ClassNotFoundException {
        Set<Class<? extends Annotation>> set = new HashSet<>();
        boolean present = getBeanClass().isAnnotationPresent(target);
        if (!present) {
            return findAnnotation(getBeanClass().getDeclaredAnnotations(), target);
        }
        return true;
    }

    private boolean findAnnotation(Annotation[] annotations, Class<? extends Annotation> target) {
        Set<Class<? extends Annotation>> set = new HashSet<>();
        for (Annotation annotation : annotations) {
            if (findAnnotation(set, annotation.annotationType(), target)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 循环查找注解
     *
     * @param set
     * @param var
     * @param target
     * @return
     */
    private boolean findAnnotation(Set<Class<? extends Annotation>> set, Class<? extends Annotation> var, Class<? extends Annotation> target) {
        if (var == target) {
            return true;
        }
        Annotation[] annotations = var.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            Class<? extends Annotation> type = annotation.annotationType();
            if (type == target) {
                return true;
            } else if (!set.contains(type)) {
                set.add(type);
                if (findAnnotation(set, type, target)) {
                    return true;
                }
            }
        }
        return false;
    }


    public boolean contains(int state){
        return (this.state&state)==1;
    }
    public int getState() {
        return state;
    }
    public void addState(int state,boolean cover){
        if (cover){
            this.state=state;
        }else {
            this.state|=state;
        }
    }
    public void addState(int state) {
        addState(state,true);
    }
    public void removeState(int state){
        this.state&=(~state);
    }

    /**
     * 状态
     */
    public static class State {
        public static final int init = 0;
        public static final int ready = 1;
        public static final int finish = 2;
    }

}
