package com.sf.croe.bean.factory;

import com.sf.croe.bean.Resource;

import java.util.Set;

public class DefaultFactoryBean<T> extends BeanBuilder<T> {

    protected DefaultFactoryBean(Set<Resource<?>> resources) {
        super(resources);

    }






    @Override
    public T createBean(Class<T> tClass) {
        return null;
    }


    private void handlerBean(Resource<T>resource){
        if (!resource.contains(Resource.State.finish)){
            try {
                Class<T> beanClass = resource.getBeanClass();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
