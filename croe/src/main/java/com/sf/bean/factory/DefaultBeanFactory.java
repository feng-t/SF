package com.sf.bean.factory;

import com.sf.annotation.AOPHandler;
import com.sf.annotation.EnableAOP;
import com.sf.bean.Resource;
import com.sf.bean.asm.ASMBuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.stream.Stream;

public abstract class DefaultBeanFactory extends BeanFactory{

    public DefaultBeanFactory(Set<Resource<?>> resourceSet) throws Exception {
        super(resourceSet);
        //进行排队
        BeanQueue();
    }

    private void BeanQueue() throws Exception {
        while (!preLoad.isEmpty()) {
            Resource<?> poll = preLoad.poll();
            resourceHandler(poll);
            //
        }
    }

    /**
     *
     * @param resource
     */
    private <T> void resourceHandler(Resource<T> resource) throws Exception {
        if (createBean(resource)!=null){
            return;
        }
        if (resource.findClassAnnotation(AOPHandler.class)){
            resource.setObj(resource.getBeanClass().newInstance());
            annotationMap.put(AOPHandler.class,resource);
            return;
        }
        if (resource.findClassAnnotation(EnableAOP.class)){
            //需要使用aop
            ASMBuilder.createObj(resource);
        }

    }

    abstract <T> T createBean(Resource<T> resource);

//
//
//     创建bean步骤
//     1，解析，分类或排优先级--依赖最高级别的优先级最高
//     2，等级最高的先创建
//


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
