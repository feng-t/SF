package com.sf.bean;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
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
    private State state=State.init;
    private Annotation[] classAnnotations;
    private Map<Method,Annotation[]> methodAnnotations;
    private Map<Field,Annotation[]> fieldAnnotations;


    public Resource(URL url,String className)  {
        this.url=url;
        this.className=className;
        try {
            this.beanClass=getBeanClass();
            this.classAnnotations=this.beanClass.getAnnotations();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取方法的所有注解
     * @param methodName
     * @param parameters
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
    public Annotation[] getMethodAnnotations(String methodName,Class<?>... parameters) throws ClassNotFoundException, NoSuchMethodException {
        Method method = getBeanClass().getMethod(methodName, parameters);
        Annotation[] annotations;
        if ((annotations=methodAnnotations.get(method))==null){
            annotations = method.getAnnotations();
            methodAnnotations.put(method,annotations);
        }
        return annotations;
    }
    public synchronized Constructor<?>[] getConstructors() throws ClassNotFoundException {
        if (constructors==null){
            constructors = getBeanClass().getDeclaredConstructors();
        }
        return constructors;
    }

    @SuppressWarnings("unchecked")
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


    @SuppressWarnings("unchecked")
    public void setObj(Object obj) {
        this.obj = (T) obj;
    }

    public T getObj() {
        return obj;
    }



    /**
     * 小的在前,根据state排序
     * @param o
     * @return
     */
    @Override
    public int compareTo(Resource o) {
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

    public boolean isFinish() {
        return state==State.finish;
    }

    public enum State {
        //默认状态
        init,
        //准备,等待依赖创建--> 就绪状态，如果拿到就绪状态的说明循环依赖
        ready,
        //完成
        finish;

    }
    private void setState(State state) {
        this.state = state;
    }
    public void setInit(){
        setState(State.init);
    }
    public void setReady(){
        setState(State.ready);
    }
    public void setFinish(){
        setState(State.finish);
    }
    public State getState() {
        return state;
    }

}
