package com.sf.bean;

import com.sun.jndi.toolkit.url.Uri;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public abstract class abstractBeanFactory {
    private Set<String> preBean = new HashSet<>();
    private Class<?> clazz;

    public void scanPaths(Class<?> clazz) {
        this.clazz = clazz;
        String path = clazz.getPackage().getName().replaceAll("\\.", "/");
        try {
            URL[] urls = scanPathsToArray(path);
            System.out.println("dd");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public URL[] scanPathsToArray(String path) throws Exception {
        Set<URL> urls = new HashSet<>();
        URL[] packs = getResources(path);
        for (URL pagePath : packs) {
            if (isJarURL(pagePath)) {
                //jar
                urls.addAll(scanJarPath(pagePath));
            } else {
                urls.addAll(scanPagePath(pagePath));
            }
        }
        return urls.toArray(new URL[0]);
    }

    protected Set<URL> scanPagePath(URL pagePath) throws IOException {
        return scanFilePath(new File(pagePath.getPath()),new HashSet<>());
    }

    protected Set<URL> scanJarPath(URL pagePath) {
        return null;
    }

    private Set<URL>  scanFilePath(File file,Set<URL> result) throws IOException {
        if (!file.canRead()){
            throw new IOException("file can't read");
        }
        if (!file.isDirectory()){

            result.add(file.toURI().toURL());
        }
        for (File dir : listDirectory(file)) {
            scanFilePath(dir,result);
        }
        System.out.println(file.getName());
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
    public URL[] getResources(String packageName) throws IOException, URISyntaxException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = null;
        Set<URL> urls = new HashSet<>();
        resources = loader.getResources(packageName);
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            urls.add(url);
        }
        return urls.toArray(new URL[0]);
    }

    public void load() throws Exception {
        for (String s : preBean) {
            System.out.println("查看:" + s);
//            Class<?> aClass = loader.loadClass(s);

        }
    }

    public static boolean isJarURL(URL url) {
        String protocol = url.getProtocol();
        return ("JAR".equalsIgnoreCase(protocol) || "WAR".equalsIgnoreCase(protocol));
    }
}
