package com.sf.bean;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 应该存放，变量，方法，注解，构造方法
 */
public class Resource<T> implements Comparable<Resource<T>>{
    private final URL url;
    private final String className;
    private Class<T> beanClass;
    private T obj;
    private Constructor<?>[] constructors;
    private final int minParameterNum;
    private final int maxParameterNum;
    private final AtomicInteger count=new AtomicInteger(0);
    private State state=State.init;


    public Resource(URL url,String className)  {
        this(url,className,0,0);
    }
    public Resource(URL url,String className,int maxParameterNum,int minParameterNum) {
        this.url=url;
        this.className=className;
        this.maxParameterNum=maxParameterNum;
        this.minParameterNum=minParameterNum;
    }


    public synchronized Constructor<?>[] getConstructors() throws ClassNotFoundException {
        if (constructors==null){
            constructors = getBeanClass().getDeclaredConstructors();
        }
        return constructors;
    }

    public synchronized Class<T> getBeanClass() throws ClassNotFoundException {
        if (beanClass==null){
            beanClass = (Class<T>) Class.forName(className);
        }
        return beanClass;
    }
    public String getBeanClassName() {
        return className;
    }

    public URL getUrl() {
        return url;
    }

    public int addCount(){
        return count.addAndGet(1);
    }
    public int getCount() {
        return count.get();
    }


    public void setObj(Object obj) {
        this.obj = (T) obj;
    }

    public T getObj() {
        return obj;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    /**
     * 小的在前
     * @param o
     * @return
     */
    @Override
    public int compareTo(Resource o) {
        if (state.ordinal()==o.state.ordinal()){
            return minParameterNum>o.minParameterNum?1:-1;
        }
        return state.ordinal()>o.state.ordinal()?1:-1;
    }

    public Set<Field> findFieldsAnnotation(Class<? extends Annotation> target) throws ClassNotFoundException {
        Field[] fields = getBeanClass().getDeclaredFields();
        Set<Field> set = new HashSet<>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(target)){
                set.add(field);
            }else if (findAnnotation(field.getDeclaredAnnotations(),target)){
                set.add(field);
            }
        }
        return set;
    }
    /**
     * 查看class 是否存在注解
     * @param target
     * @return
     */
    public boolean findClassAnnotation(Class<? extends Annotation> target) throws ClassNotFoundException {
        Set<Class<? extends Annotation>> set = new HashSet<>();
        boolean present = getBeanClass().isAnnotationPresent(target);
        if (!present){
            return findAnnotation(getBeanClass().getDeclaredAnnotations(),target);
        }
        return true;
    }

    private boolean findAnnotation(Annotation[] annotations,Class<? extends Annotation> target){
        Set<Class<? extends Annotation>> set = new HashSet<>();
        for (Annotation annotation : annotations) {
            if (findAnnotation(set,annotation.annotationType(),target)){
                return true;
            }
        }
        return false;
    }
    /**
     * 循环查找注解
     * @param set
     * @param var
     * @param target
     * @return
     */
    private boolean findAnnotation(Set<Class<? extends Annotation>> set, Class<? extends Annotation> var, Class<? extends Annotation> target){
        if (var==target){
            return true;
        }
        Annotation[] annotations = var.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            Class<? extends Annotation> type = annotation.annotationType();
            if (type==target){
                return true;
            }else if (!set.contains(type)){
                set.add(type);
                if (findAnnotation(set,type,target)){
                    return true;
                }
            }
        }
        return false;
    }

    public enum State {
        //默认状态
        init,
        //准备--> 就绪状态，如果拿到就绪状态的说明循环依赖
        ready,
        //完成
        finish
    }
}
