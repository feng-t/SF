package com.sf.netty;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class NettyServer {
    private int port;

    public NettyServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        new NettyServer(11090).start();
    }

    private void start() throws IOException {
//        ClassLoader parent = this.getClass();

        Enumeration<URL> resources = NettyServer.class.getClassLoader().getParent().getResources("META-INF/");
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            JarURLConnection jarConnection = (JarURLConnection) url.openConnection();
            System.out.println(jarConnection.getEntryName());
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
}
