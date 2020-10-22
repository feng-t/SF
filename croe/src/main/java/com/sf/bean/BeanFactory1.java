package com.sf.bean;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.stream.Stream;


public class BeanFactory1 {
    private final Set<Resource> resources=ConcurrentHashMap.newKeySet();
    private final Queue<Resource> preloadBeans=new PriorityBlockingQueue<>();
    private final Map<Class<?>,?> beanMap=new ConcurrentHashMap<>();
    public BeanFactory1(Set<Resource>resourceSet){
        resources.addAll(resourceSet);
    }

    public static void main(String[] args) {
        final BeanFactory1 factory = new BeanFactory1(new HashSet<>());
        for (int i = 0; i < 30; i++) {
            factory.preloadBeans.add(new Resource(null,"test"+i));
        }
        final Resource peek = factory.preloadBeans.poll();
        System.out.println();
    }
    /**
     * 加载全部预加载bean
     */
    public void loadAllPreBean(){

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
        return constructor.newInstance(parameters);
    }

    /**
     * @param beanClass bean的class
     * @param <T> 返回类型
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T>beanClass){
        return (T) beanMap.get(beanClass);
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(String beanClass) throws ClassNotFoundException {
        return (T) getBean(Class.forName(beanClass));
    }
}
