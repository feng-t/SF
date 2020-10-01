package com.sf.test;

import com.sf.test1.SPITest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

public class ServiceLoaderTest {

    /**
     * The location to look for factories.
     * <p>Can be present in multiple JAR files.
     */
    public static final String FACTORIES_RESOURCE_LOCATION = "META-INF/spring.factories";

    public static void main(String[] args) throws IOException {
//        ServiceLoader<TestBeanFactory> serviceLoader = ServiceLoader.load(TestBeanFactory.class);
//        serviceLoader.forEach(TestBeanFactory::test);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = null;
        try {
            Enumeration<URL> urls = (classLoader != null ?
                    classLoader.getResources(FACTORIES_RESOURCE_LOCATION) :
                    ClassLoader.getSystemResources(FACTORIES_RESOURCE_LOCATION));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                inputStream = url.openStream();
                Properties prop = new Properties();
                prop.load(inputStream);
                String property = prop.getProperty("com.sf.test1.SPITest");
                Class<?> aClass =  Class.forName(property);
                SPITest test = (SPITest) aClass.newInstance();
                test.test();
            }
        } catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}
