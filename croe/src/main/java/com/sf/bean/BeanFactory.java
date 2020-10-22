package com.sf.bean;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.stream.Stream;


public class BeanFactory {
    private final Set<Resource> resources = ConcurrentHashMap.newKeySet();

    private final Queue<Resource> preloadBeans = new PriorityBlockingQueue<>();
    private final Map<Class<?>, Object> beanMap = new ConcurrentHashMap<>();
    private final Map<Class<?>,Resource> preLoadResource = new ConcurrentHashMap<>();


    public BeanFactory(Set<Resource> resourceSet) throws ClassNotFoundException {
        resources.addAll(resourceSet);
        for (Resource resource : resources) {
            final Class<?> beanClass = resource.getBeanClass();
            preLoadResource.put(beanClass,resource);
        }
    }


    /**
     * 加载全部预加载bean
     */
    public void loadAllPreBean() throws Exception{
        for (Resource resource : resources) {
            loadBean(resource);
        }
    }

    public Object loadBean(Class<?> preBean)throws Exception{
       return loadBean(preLoadResource.get(preBean));
    }
    public Object loadBean(Resource resource) throws Exception{
        resource.addCount();
        Object obj = null;
        Class<?> beanClass = resource.getBeanClass();
        Constructor<?>[] constructors = resource.getConstructors();
        if (constructors.length == 1) {
            Constructor<?> constructor = constructors[0];
            Class<?>[] types = constructor.getParameterTypes();
            if (types.length == 0) {
                obj=createBean(beanClass);
            }else {
                final Object[] parameters = new Object[types.length];
                for (int i = 0; i < types.length; i++) {
                    final Class<?> type = types[i];
                    final Resource res = preLoadResource.get(type);
                    final int count = res.getCount();
                    if (count>=resource.getCount()&&getBean(type)==null){
                        throw new Exception(type+"产生循环依赖，无法创建bean");
                    }
                    final Object bean = getBean(type);
                    parameters[i]=bean==null?bean:loadBean(type);
                }
                obj=createBean(beanClass,parameters);
            }
        }
        //TODO 多个构造函数的情况
        if (obj!=null){
            beanMap.put(beanClass,obj);
        }else {
            //将创建失败的加载到预加载，后面可能会删除
            preloadBeans.offer(resource);
        }
        return obj;
    }
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

    /**
     * @param beanClass bean的class
     * @param <T>       返回类型
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> beanClass) {
        return (T) beanMap.get(beanClass);
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(String beanClass) throws ClassNotFoundException {
        return (T) getBean(Class.forName(beanClass));
    }
}
