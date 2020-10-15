package com.sf.bean;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public abstract class AbstractBeanFactory {
    private Set<String> preBean = new HashSet<>();
    private String packName;
    private Set<Resource> beanURLs;

    public void scanPaths(Class<?> clazz) {
        this.packName = clazz.getPackage().getName();
        String path = clazz.getPackage().getName().replaceAll("\\.", "/");
        try {
            this.beanURLs = scanPathsToArray(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Set<Resource> scanPathsToArray(String path) throws Exception {
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

    protected Set<Resource> scanJarPath(URL pagePath) {
        return new HashSet<>(0);
    }

    private Set<Resource>  scanFilePath(File file,Set<Resource> result) throws IOException {
        if (!file.canRead()){
            throw new IOException("file can't read");
        }
        if (!file.isDirectory()){
            String absolutePath = file.getAbsolutePath();
            String classPaths = absolutePath.substring(absolutePath.indexOf(packName.replaceAll("\\.", "/")));
            if (classPaths.endsWith(".class")) {
                URL url = file.toURI().toURL();
                result.add(new Resource(url,classPaths.replace(".class","").replaceAll("/","\\.")));
            }
        }
        for (File dir : listDirectory(file)) {
            scanFilePath(dir,result);
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
