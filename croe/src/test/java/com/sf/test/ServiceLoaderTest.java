package com.sf.test;

import com.sf.bean.TestBeanFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.ServiceLoader;

public class ServiceLoaderTest {

    /**
     * The location to look for factories.
     * <p>Can be present in multiple JAR files.
     */
    public static final String FACTORIES_RESOURCE_LOCATION = "META-INF/spring.factories";
    public static void main(String[] args) {
//        ServiceLoader<TestBeanFactory> serviceLoader = ServiceLoader.load(TestBeanFactory.class);
//        serviceLoader.forEach(TestBeanFactory::test);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            Enumeration<URL> urls = (classLoader != null ?
                    classLoader.getResources(FACTORIES_RESOURCE_LOCATION) :
                    ClassLoader.getSystemResources(FACTORIES_RESOURCE_LOCATION));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
