package com.sf.bean.factory;

import com.sf.annotation.Autowired;
import com.sf.annotation.Component;
import com.sf.bean.Resource;

import java.lang.annotation.Annotation;
import java.util.*;

public class DefaultBeanFactory extends ParentBeanFactory{
    private final Map<Class<Annotation>,List<Class<?>>> annotationMap=new HashMap<> ();
    public DefaultBeanFactory(Set<Resource> resourceSet) throws ClassNotFoundException {
        super(resourceSet);
        List<Resource> filter = getFilter(Component.class);
        for (Resource resource : filter) {
            annotationMap.getOrDefault(Component.class,new ArrayList<>()).add(resource.getBeanClass());
        }
    }



    /**
     * 只有声明了 {@link Component} 的类才会被加载,
     * @param resource
     * @return
     * @throws Exception
     */
    @Override
    public Object loadBean(Resource resource) throws Exception {
        return super.loadBean(resource);
    }

    public List<Resource> getFilter(Class<? extends Annotation> annotation) throws ClassNotFoundException {
//        annotation.getAnnotationsByType()

        return null;
    }
    public static boolean findAnnotation(Class<?> c, Class<? extends Annotation> target){
        HashSet<Class<? extends Annotation>> set = new HashSet<>();
        boolean present = c.isAnnotationPresent(target);
        if (!present){
            Annotation[] annotations = c.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if (findAnnotation(set,annotation.annotationType(),target)){
                    return true;
                }
            }
        }
        return present;
    }
    public static int i=0;
    private static boolean findAnnotation(Set<Class<? extends Annotation>> set,Class<? extends Annotation> var,Class<? extends Annotation> target){
        i++;
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


}
