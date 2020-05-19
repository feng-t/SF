package com.sf.netty;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class NettyServer {
    private int port;

    public NettyServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        new NettyServer(11090).test();
    }

    private void start() throws IOException {

        Enumeration<URL> resources = NettyServer.class.getClassLoader().getResources("META-INF");
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            JarURLConnection jarConnection = (JarURLConnection) url.openConnection();
            JarFile jarFile = jarConnection.getJarFile();
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (!entry.isDirectory()) {
//                    System.out.println(entry.getName());
                    System.out.println(entry);
                }

            }
        }
    }

    public void test() throws Exception{
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = loader.getResources("META-INF/spring.factories");
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            System.out.println(url);
        }
    }
}
