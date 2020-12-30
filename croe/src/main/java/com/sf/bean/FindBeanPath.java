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

    public Set<Resource<?>> scanPaths(Class<?> clazz) throws IOException {
        String path = clazz.getPackage().getName().replaceAll("\\.", "/");
        return scanPathsToArray(path);
    }
    public Set<Resource<?>> scanPaths(String pagePaths)throws IOException{
        if (pagePaths==null){
            return new HashSet<>();
        }
        return scanPathsToArray(pagePaths.replaceAll("\\.","/"));
    }

    private Set<Resource<?>> scanPathsToArray(String path) throws IOException {
        Set<Resource<?>> urls = new HashSet<>();
        URL[] packs = getResources(path);
        for (URL pagePath : packs) {
            if (isJarURL(pagePath)) {
                //jar
                urls.addAll(scanJarPath(pagePath,path));
            } else {
                urls.addAll(scanPagePath(pagePath,path));
            }
        }
        return urls;
    }

    private Set<Resource<?>> scanPagePath(URL pagePath,String path) throws IOException {
        return scanFilePath(new File(pagePath.getPath()), new HashSet<>(),path);
    }

    //TODO jar
    private Set<Resource<?>> scanJarPath(URL pagePath,String path) throws IOException {
//        final URLConnection conn = pagePath.openConnection();
//        if (conn instanceof JarURLConnection){
//            JarURLConnection jarConn = (JarURLConnection) conn;
//            JarFile file = jarConn.getJarFile();
//            final JarEntry test = file.getJarEntry("test");
////            test.
//
//        }
        return new HashSet<>();
    }


    private Set<Resource<?>> scanFilePath(File file, Set<Resource<?>> result,String path) throws IOException {
        if (!file.canRead()) {
            throw new IOException("file can't read");
        }
        if (!file.isDirectory()) {
            String absolutePath = file.getAbsolutePath().replaceAll("\\\\","/");
            String s = path.replaceAll("\\.", "/");
            String classPaths = absolutePath.substring(absolutePath.indexOf(s));
            if (classPaths.endsWith(".class")) {
                URL url = file.toURI().toURL();
                result.add(new Resource(url, classPaths.replace(".class", "").replaceAll("/", "\\.")));
            }
        }
        for (File dir : listDirectory(file)) {
            scanFilePath(dir, result,path);
        }
        return result;
    }

    private File[] listDirectory(File dir) {
        File[] files = dir.listFiles();
        if (files == null) {
            return new File[0];
        }
        Arrays.sort(files, Comparator.comparing(File::getName));
        return files;
    }

    private URL[] getResources(String packageName) throws IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Set<URL> urls = new HashSet<>();
        Enumeration<URL> resources = loader.getResources(packageName);
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            urls.add(url);
        }
        return urls.toArray(new URL[0]);
    }

    private static boolean isJarURL(URL url) {
        String protocol = url.getProtocol();
        return ("JAR".equalsIgnoreCase(protocol) || "WAR".equalsIgnoreCase(protocol));
    }
}
