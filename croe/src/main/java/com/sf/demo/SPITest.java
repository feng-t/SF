package com.sf.demo;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

public class SPITest {
    /**
     * The location to look for factories.
     * <p>Can be present in multiple JAR files.
     */
    public static final String FACTORIES_RESOURCE_LOCATION = "META-INF/services.factories";
    public static void main(String[] args) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream=null;
        try {
            Enumeration<URL> urls = (classLoader != null ?
                    classLoader.getResources(FACTORIES_RESOURCE_LOCATION) :
                    ClassLoader.getSystemResources(FACTORIES_RESOURCE_LOCATION));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                stream = url.openStream();
                Properties config = new Properties();
                config.load(stream);
                String[] impls = ((String) config.get("com.sf.bean.TestBeanFactory")).split(",");
                for (String impl : impls) {
                    assert classLoader != null;
                    Class<?> clazz = classLoader.loadClass(impl);
                    Constructor<?> constructor = clazz.getDeclaredConstructor();
                    Object instance = constructor.newInstance();
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assert stream != null;
            stream.close();
        }
    }
}
