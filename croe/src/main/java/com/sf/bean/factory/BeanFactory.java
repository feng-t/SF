package com.sf.bean.factory;

import com.sf.annotation.AOPHandler;
import com.sf.bean.Resource;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.stream.Stream;

public class BeanFactory {
    private final Map<Class<?>,Resource<?>> bean=new ConcurrentHashMap<>();
    public final Queue<Resource<?>> preLoad = new PriorityBlockingQueue<>();

    public BeanFactory(Set<Resource<?>> resourceSet) throws Exception {
        for (Resource<?> resource : resourceSet) {
            bean.put(resource.getBeanClass(),resource);
            preLoad.add(resource);
        }
        //进行排队
        BeanQueue();
    }

    private void BeanQueue() throws Exception {
        while (!preLoad.isEmpty()) {
            Resource<?> poll = preLoad.poll();
            if (poll.findClassAnnotation(AOPHandler.class)){
                Object o = poll.getBeanClass().newInstance();
                poll.setObj(o);
                bean.put(poll.getBeanClass(),poll);
                continue;
            }
            //
        }
    }


    /**
     * 创建bean步骤
     * 1，解析，分类或排优先级--依赖最高级别的优先级最高
     * 2，等级最高的先创建
     */


    /**
     * 创建bean
     * @param t
     * @param <T>
     * @return
     */
    public <T> T getBean(Class<T> t) throws ClassNotFoundException {
        Resource<?> resource = bean.get(t);
        if (resource==null||resource.getObj()==null){
            return (T) createBean(resource,false);
        }
        return (T) resource.getObj();
    }

    private <T> T createBean(Resource<T> t,boolean findBean) throws ClassNotFoundException {
        if (findBean){
            T bean = getBean(t.getBeanClass());
            if (bean!=null){
                return bean;
            }
        }

        return (T) new Object();
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
}
