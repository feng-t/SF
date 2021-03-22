package com.sf.bean.factory;

import com.sf.bean.Resource;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class DefaultBeanFactory extends BeanFactory {

    public DefaultBeanFactory(Set<Resource<?>> resourceSet) throws Exception {
        super(resourceSet);
        //进行排队
    }

    @Override
    public Object getBean(Class<?> targetClass) throws CreateBeanError {
        Resource<?> resource = bean.get(targetClass);
        if (resource == null) {
            throw new CreateBeanError("resource is null");
        }
        if (resource.getState()== Resource.State.ready){
            throw new CreateBeanError("产生循环依赖");
        }
        if (resource.getObj() == null) {
            try {
                return createBean(resource);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<Class<?>> getBeanClass() {
        return null;
    }

    @Override
    public List<Class<?>> getBeanClass(Class<?> c) {
        return null;
    }

    @Override
    public void addResource(Set<Resource<?>> set) {

    }


    public <T> T createBean(Resource<T> resource) throws ClassNotFoundException {
        Constructor<?>[] constructors = resource.getConstructors();
        resource.setReady();
        T obj = null;
        //普通创建方法
        if (constructors.length == 1) {

            Class<?>[] types = constructors[0].getParameterTypes();
            Object[] parameters = new Object[types.length];
            try {
                for (int i = 0; i < types.length; i++) {
                    parameters[i] = getBean(types[i]);
                }
                obj = createBean(resource.getBeanClass(), parameters);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | CreateBeanError e) {
                e.printStackTrace();
            }
        }
        if (obj!=null){
            resource.setObj(obj);
            resource.setFinish();
        }
        return obj;
    }


//
//
//     创建bean步骤
//     1，解析，分类或排优先级--依赖最高级别的优先级最高
//     2，等级最高的先创建
//


    /**
     * 创建bean
     *
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
