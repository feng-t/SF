package com.sf.bean;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

public abstract class abstractBeanFactory {
    private Set<String> preBean=new HashSet<>();
    public void scanPaths(Class<?> clazz) throws IOException {
        ClassLoader loader = getClass().getClassLoader();
        String path = clazz.getPackage().getName().replaceAll("\\.", File.separator);
        Enumeration<URL> resources = loader.getResources(path);
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            System.out.println(url.getFile());
        }
    }

}
