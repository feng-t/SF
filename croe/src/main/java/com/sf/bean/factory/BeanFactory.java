package com.sf.bean.factory;

import com.sf.bean.Resource;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BeanFactory {
    public final Set<Resource> resources = ConcurrentHashMap.newKeySet();


    public BeanFactory(Set<Resource> resourceSet){
        resources.addAll(resourceSet);
        //进行分析
    }



    /**
     * 查看class是否存在注解，只针对类上面的注解
     * @param c
     * @param target
     * @return
     */
    public boolean findAnnotation(Class<?> c, Class<? extends Annotation> target){
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
}
