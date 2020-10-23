package com.sf.bean;

import com.sf.annotation.InitCreate;

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
    private final Map<Class<?>, Resource> preLoadResource = new ConcurrentHashMap<>();


    public BeanFactory(Set<Resource> resourceSet) throws ClassNotFoundException {
        addResource(resourceSet);
    }

    public void addResource(Set<Resource> resourceSet) throws ClassNotFoundException {
        resources.addAll(resourceSet);
        for (Resource resource : resources) {
            final Class<?> beanClass = resource.getBeanClass();
            preLoadResource.put(beanClass, resource);
        }
    }

    /**
     * 加载全部预加载bean
     */
    public void loadAllPreBean() throws Exception {
        for (Resource resource : resources) {
            loadBean(resource);
        }
    }

    public Object loadBean(Class<?> preBean) throws Exception {
        return loadBean(preLoadResource.get(preBean));
    }

    public Object loadBean(Resource resource) throws Exception {
        resource.addCount();
        Object obj = null;
        Class<?> beanClass = resource.getBeanClass();
        if (beanClass.isAnnotation() || beanClass.isEnum()) {
            return null;
        }
        if (!beanClass.isInterface()) {
            Constructor<?>[] constructors = resource.getConstructors();
            if (constructors.length == 1) {
                Constructor<?> constructor = constructors[0];
                Class<?>[] types = constructor.getParameterTypes();
                if (types.length == 0) {
                    obj = createBean(beanClass);
                } else {
                    obj = createBean(beanClass, getParameter(resource, types));
                }
            } else {
                final InitCreate create;
                if ((create = beanClass.getAnnotation(InitCreate.class)) != null) {
                    Class<?>[] parameters = create.parameters();
                    Object[] parametersObj = getParameter(resource, parameters);
                    obj = createBean(beanClass, parametersObj);
                }
            }
        } else {
            for (Resource res : resources) {
                final Class<?> resBeanClass = res.getBeanClass();
                if (beanClass.isAssignableFrom(resBeanClass)
                        && resBeanClass != beanClass
                        && !resBeanClass.isInterface()
                        && !resBeanClass.isAnnotation()
                ){
                    obj = loadBean(res);
                    break;
                }
            }
        }
        if (obj != null) {
            beanMap.put(beanClass, obj);
        } else {
            //将创建失败的加载到预加载，后面可能会删除
            preloadBeans.offer(resource);
            throw new Exception("创建失败");
        }
        return obj;
    }

    private Object[] getParameter(Resource resource, Class<?>[] types) throws Exception {
        final Object[] parameters = new Object[types.length];
        for (int i = 0; i < types.length; i++) {
            final Class<?> type = types[i];
            final Resource res = preLoadResource.get(type);
            if (res == null) {
                throw new Exception("无法找到bean: " + type);
            }
            final int count = res.getCount();
            final Object bean = getBean(type);
            if (count >= resource.getCount() && bean == null) {
                throw new Exception(type + "产生循环依赖，无法创建bean");
            }
            parameters[i] = bean != null ? bean : loadBean(type);
        }
        return parameters;
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

    public Object[] getBeans() {
        return beanMap.values().toArray();
    }

    public Class<?>[] getBeanClass() {
        Set<Class<?>> set = beanMap.keySet();
        return set.toArray(new Class<?>[0]);
    }
}
