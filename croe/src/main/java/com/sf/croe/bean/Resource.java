package com.sf.croe.bean;

import java.lang.reflect.Constructor;

public class Resource<T> {
    public Class<T> tClass;
    public Constructor<T>[] constructors;

    public Resource(){

    }
}
