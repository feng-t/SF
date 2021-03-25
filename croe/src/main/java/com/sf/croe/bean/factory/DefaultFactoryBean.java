package com.sf.croe.bean.factory;

import java.util.Set;

public class DefaultFactoryBean<T> extends BeanBuilder<T> {

    public DefaultFactoryBean(Set<String> resources) throws ClassNotFoundException {
        super(resources);
    }


    @Override
    public T createBean(Class<T> tClass) {
        return null;
    }



}
