package com.sf.netty;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class NettyServer {
    private int port;

    public NettyServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        new NettyServer(11090).start();
    }

    private void start() throws IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = loader.getResources("META-INF/test");
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            URLConnection urlConnection = url.openConnection();
            if (urlConnection instanceof JarURLConnection) {
                JarURLConnection jarConnection = (JarURLConnection) urlConnection;
                JarFile jarFile = jarConnection.getJarFile();
                System.out.println(jarFile.getName());
                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    if (!entry.isDirectory()) {
//                    System.out.println(entry.getName());
//                    System.out.println(entry);
                    }
                }
            }
        }
    }
}
