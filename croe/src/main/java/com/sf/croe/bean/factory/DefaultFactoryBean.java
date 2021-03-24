package com.sf.croe.bean.factory;

public class DefaultFactoryBean<T> extends BeanBuilder<T> {
    @Override
    public T createBean(Class<T> tClass) {
        return null;
    }
}
