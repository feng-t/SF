package com.sf.croe.bean;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * bean资源类，表明一个类的所有信息
 */
public class Resource<T> implements Comparable<Resource<T>> {

    private final Class<T> beanClass;
    /**
     * 三种创建方式，单例，多例，即时，默认单例
     */
    private final List<T> cacheBean=Collections.synchronizedList(new ArrayList<>(1));
    /**
     * 构造方法
     */
    private Constructor<?>[] constructors;
    /**
     * 每个构造方法所对应参数的类
     */
    private Map<Constructor<?>,Class<?>[]>parameterTypes=new ConcurrentHashMap<>();
    /**
     * bean的状态
     */
    private int state = State.init;
    /**
     * 注解对应的对象，包括类，方法，参数
     */
    private Map<Annotation,Class<T>> annotationByClass=new ConcurrentHashMap<>();
    private Map<Annotation,List<Field>> annotationByField=new ConcurrentHashMap<>();
    private Map<Annotation,List<Method>> annotationByMethod=new ConcurrentHashMap<>();


    public Resource(String className) throws ClassNotFoundException {
        this((Class<T>) Class.forName(className));
    }

    /**
     * 初始化
     * @param beanClass
     */
    public Resource(Class<T> beanClass) {
        this.beanClass = beanClass;
        this.constructors=beanClass.getConstructors();
        for (Constructor<?> constructor : this.constructors) {
            parameterTypes.put(constructor,constructor.getParameterTypes());
        }
        for (Annotation annotation : Arrays.stream(beanClass.getAnnotations()).collect(Collectors.toList())) {
            annotationByClass.put(annotation,beanClass);
        }
        for (Field field : beanClass.getFields()) {
            for (Annotation annotation : field.getAnnotations()) {
                List<Field> list = annotationByField.getOrDefault(annotation, Collections.synchronizedList(new ArrayList<>()));
                list.add(field);
            }
        }
        for (Method method : beanClass.getMethods()) {
            for (Annotation annotation : method.getAnnotations()) {
                List<Method> methods = annotationByMethod.getOrDefault(annotation, Collections.synchronizedList(new ArrayList<>()));
                methods.add(method);
            }
        }
    }


    public void addBean(T obj){
        this.cacheBean.add(obj);
    }

    public List<T> getCacheBean() {
        return cacheBean;
    }

    public Map<Annotation, Class<T>> getAnnotationByClass() {
        return annotationByClass;
    }

    public Map<Annotation, List<Field>> getAnnotationByField() {
        return annotationByField;
    }

    public Map<Annotation, List<Method>> getAnnotationByMethod() {
        return annotationByMethod;
    }

    public Map<Constructor<?>, Class<?>[]> getParameterTypes() {
        return parameterTypes;
    }

    public synchronized Constructor<?>[] getConstructors() throws ClassNotFoundException {
        if (constructors == null) {
            constructors = getBeanClass().getDeclaredConstructors();
        }
        return constructors;
    }

    public Class<T> getBeanClass() {
        return beanClass;
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
    public <T> T createBean(Class<T> beanClass, Object... parameters) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?>[] paraClass = Stream.of(parameters).map(Object::getClass).toArray(Class[]::new);
        Constructor<T> constructor = beanClass.getDeclaredConstructor(paraClass);
        constructor.setAccessible(true);
        return constructor.newInstance(parameters);
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
     * 小的在前
     * @param o
     * @return
     */
    @Override
    public int compareTo(Resource o) {
        return state > o.state ? 1 : -1;
    }

    /**
     * 状态
     */
    public static class State {
        //优先级最高，表示基础设施
        public static final int basis = 0;
        public static final int init = 1;
        public static final int ready = 1<<1;
        public static final int finish = 1<<2;
    }

}
