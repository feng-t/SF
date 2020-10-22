package com.sf.bean;

import java.io.IOException;
import java.util.Set;

public class DefaultBeanFactory extends BeanFactory2 {
    public DefaultBeanFactory(Class<?> clazz) throws Exception {
        super(clazz);
    }

    @Override
    public Set<Resource> scanPath() throws IOException {
        FindBeanPath path = new FindBeanPath();
        return path.scanPaths(clazz);
    }

}
