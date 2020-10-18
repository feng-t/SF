package com.sf.bean;

import java.io.IOException;
import java.util.Set;

public abstract class BeanFactory {
    public Set<Resource> classPaths;
    public Class<?>clazz;
    BeanFactory(Class<?>clazz) throws IOException {
        this.clazz=clazz;
        classPaths=scanPath();
    }

    abstract Set<Resource> scanPath() throws IOException;
}
