package com.sf.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;

public class ServiceLoaderTest {

    /**
     * The location to look for factories.
     * <p>Can be present in multiple JAR files.
     */
    public static final String FACTORIES_RESOURCE_LOCATION = "META-INF/spring.factories";

    public static void main(String[] args) throws IOException {
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
                Set<String> interfaces = prop.stringPropertyNames();
                for (String faces : interfaces) {
                    String impl = prop.getProperty(faces);
                    String[] className = impl.split(",");
                    for (String name : className) {
                        Class<?> aClass = Class.forName(name);
                        Object o = aClass.newInstance();
                        System.out.println(o.toString());
                    }
                }

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
