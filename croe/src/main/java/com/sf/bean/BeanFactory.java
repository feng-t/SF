package com.sf.bean;

import com.sf.annotation.Bean;
import com.sf.annotation.InitCreate;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.stream.Stream;


public class BeanFactory {

    private final Set<Resource> resources = ConcurrentHashMap.newKeySet();
    private final Queue<Resource> preLoad = new PriorityBlockingQueue<>();
    private final Map<Class<?>, Object> beanMap = new ConcurrentHashMap<>();
    private final Map<Class<?>, Resource> preLoadResource = new ConcurrentHashMap<>();


    public BeanFactory(Set<Resource> resourceSet) throws ClassNotFoundException {
        addResource(resourceSet);
    }

    public void addResource(Set<Resource> resourceSet) throws ClassNotFoundException {
        preLoad.addAll(resourceSet);
        resources.addAll(resourceSet);
        for (Resource resource : resources) {
            final Class<?> beanClass = resource.getBeanClass();
            preLoadResource.put(beanClass, resource);
        }
    }
//
//    public void loadAndCreateBean() throws Exception {
//        while (!preLoad.isEmpty()){
//            Resource peek = preLoad.poll();
//            Object bean = loadBean1(peek);
//            if (bean==null){
//                preLoad.offer(peek);
//            }else {
//                beanMap.put(peek.getBeanClass(),bean);
//            }
//        }
//    }
//
//    private Object loadBean1(Resource peek) {
//
//        return null;
//    }


    /**
     * 加载全部预加载bean
     */
    public void loadAllPreBean() throws Exception {
        while (!preLoad.isEmpty()) {
            loadBean(preLoad.poll());
        }
    }

    private Object loadBean(Class<?> preBean) throws Exception {
        return loadBean(preLoadResource.get(preBean));
    }

    private Object loadBean(Resource resource) throws Exception {
        Class<?> beanClass = resource.getBeanClass();
        Object obj = beanMap.get(beanClass);
        if (obj != null) {
            return obj;
        }
        resource.addCount();
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
                    obj = getParameter(resource, types);
                }
            } else {
                final InitCreate create;
                if ((create = beanClass.getAnnotation(InitCreate.class)) != null) {
                    Class<?>[] parameters = create.parameters();
                    obj= getParameter(resource, parameters);
//                    obj = createBean(beanClass, parametersObj);
                }
            }
        } else {
            //父子类
            for (Resource res : resources) {
                final Class<?> resBeanClass = res.getBeanClass();
                if (beanClass.isAssignableFrom(resBeanClass)
                        && resBeanClass != beanClass
                        && !resBeanClass.isInterface()
                        && !resBeanClass.isAnnotation()) {
                    obj = loadBean(res);
                    break;
                }
            }
        }
        if (obj != null) {
            beanMap.put(beanClass, obj);
            addCreateBeanMethod(beanClass, obj);
        }
        return obj;
    }


    private Object getParameter(Resource resource, Class<?>[] types) throws Exception {
        final Object[] parameters = new Object[types.length];
        boolean tar=true;
        for (int i = 0; i < types.length; i++) {
            final Class<?> type = types[i];
            final Object bean = beanMap.get(type);
            final Resource res = preLoadResource.get(type);
            if (res == null) {
                tar=false;
                break;
            }
            final int count = res.getCount();
            if (count >= resource.getCount() && bean == null) {
                throw new Exception(type + "产生循环依赖，无法创建bean");
            }
            parameters[i] = bean != null ? bean : loadBean(type);
        }
        Object obj = beanMap.get(resource.getBeanClass());
        if (obj!=null){
            return obj;
        } else {
            if (tar){
                obj = createBean(resource.getBeanClass(), parameters);
            }else {
                //
                System.err.println("没有创建成功");
                preLoad.add(resource);
            }
        }
        return obj;
    }

    private void addCreateBeanMethod(Class<?> beanClass, Object obj) throws Exception {
        if (obj == null) {
            return;
        }
        final Method[] methods = beanClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getDeclaredAnnotation(Bean.class) != null) {
                final Class<?> type = method.getReturnType();
                if (beanMap.get(type) != null) {
                    return;
                }
                if (type != Void.TYPE) {
                    beanMap.put(type, method.invoke(obj));
                } else {
                    throw new Exception("被@Bean声明的方法必须有返回值");
                }
            }
        }
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
    public <T> T getBean(Class<T> beanClass) throws Exception {
        T o = (T) beanMap.get(beanClass);
        if (o == null) {
            o = (T) loadBean(beanClass);
        }
        return o;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(String beanClass) throws Exception {
        return (T) getBean(Class.forName(beanClass));
    }

    public Object[] getBeans() {
        return beanMap.values().toArray();
    }

    public List<Class<?>> getBeanClass() throws Exception {
        List<Class<?>> list = new ArrayList<>();
        for (Resource resource : resources) {
            Class<?> resourceBeanClass = resource.getBeanClass();
            if (resourceBeanClass != null) {
                list.add(resourceBeanClass);
            }
        }
        return list;
    }

    public <T> List<Class<? extends T>> getBeanClass(Class<T> c) throws Exception {
        List<Class<?>> beanClass = getBeanClass();
        List<Class<? extends T>> classes = new ArrayList<>();
        for (Class<?> aClass : beanClass) {
            if (c.isAssignableFrom(aClass)) {
                classes.add((Class<? extends T>) aClass);
            }
        }
        return classes;
    }
}
