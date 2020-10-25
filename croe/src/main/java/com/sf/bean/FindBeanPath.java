package com.sf.bean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FindBeanPath implements ScanPath {
    private String packName;

    public Set<Resource> scanPaths(Class<?> clazz) throws IOException {
        this.packName = clazz.getPackage().getName();
        String path = clazz.getPackage().getName().replaceAll("\\.", "/");
        return scanPathsToArray(path);

    }

    public Set<Resource> scanPathsToArray(String path) throws IOException {
        Set<Resource> urls = new HashSet<>();
        URL[] packs = getResources(path);
        for (URL pagePath : packs) {
            if (isJarURL(pagePath)) {
                //jar
                urls.addAll(scanJarPath(pagePath));
            } else {
                urls.addAll(scanPagePath(pagePath));
            }
        }
        return urls;
    }

    protected Set<Resource> scanPagePath(URL pagePath) throws IOException {
        return scanFilePath(new File(pagePath.getPath()), new HashSet<>());
    }

    protected Set<Resource> scanJarPath(URL pagePath) throws IOException {
        final URLConnection conn = pagePath.openConnection();
        if (conn instanceof JarURLConnection){
            JarURLConnection jarConn = (JarURLConnection) conn;
            JarFile file = jarConn.getJarFile();
            final JarEntry test = file.getJarEntry("test");
//            test.

        }
        return new HashSet<>();
    }


    private Set<Resource> scanFilePath(File file, Set<Resource> result) throws IOException {
        if (!file.canRead()) {
            throw new IOException("file can't read");
        }
        if (!file.isDirectory()) {
            String absolutePath = file.getAbsolutePath().replaceAll("\\\\","/");
            String s = packName.replaceAll("\\.", "/");
            String classPaths = absolutePath.substring(absolutePath.indexOf(s));
            if (classPaths.endsWith(".class")) {
                URL url = file.toURI().toURL();
                result.add(new Resource(url, classPaths.replace(".class", "").replaceAll("/", "\\.")));
            }
        }
        for (File dir : listDirectory(file)) {
            scanFilePath(dir, result);
        }
        return result;
    }

    protected File[] listDirectory(File dir) {
        File[] files = dir.listFiles();
        if (files == null) {
            return new File[0];
        }
        Arrays.sort(files, Comparator.comparing(File::getName));
        return files;
    }

    public URL[] getResources(String packageName) throws IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Set<URL> urls = new HashSet<>();
        Enumeration<URL> resources = loader.getResources(packageName);
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            urls.add(url);
        }
        return urls.toArray(new URL[0]);
    }

    public static boolean isJarURL(URL url) {
        String protocol = url.getProtocol();
        return ("JAR".equalsIgnoreCase(protocol) || "WAR".equalsIgnoreCase(protocol));
    }
}
