package com.sf.bean;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

public abstract class abstractBeanFactory {
    private Set<String> preBean=new HashSet<>();
    private Class<?> clazz;
    private ClassLoader loader;
    public void scanPaths(Class<?> clazz)  {
        this.clazz=clazz;
        loader = Thread.currentThread().getContextClassLoader();
        String path = clazz.getPackage().getName().replaceAll("\\.","\\" + File.separator);
        Enumeration<URL> resources = null;
        try {
            resources = loader.getResources(path);
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                File file = new File(url.toURI()).getAbsoluteFile();
                String newPackName = file.getAbsolutePath();
                newPackName = newPackName.substring(!newPackName.contains(path) ?0:newPackName.indexOf(path));
                addClassFiles(file, newPackName);
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
    private void addClassFiles(File file, String packName) throws IOException {
        if (file != null) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null) {
                    for (File f : files) {
                        String newPackName = f.getAbsolutePath();
                        newPackName = newPackName.substring(newPackName.indexOf(packName));
                        addClassFiles(f, newPackName);
                    }
                }
            } else {
                if (packName.endsWith(".class")) {
                    packName = packName.replaceAll("\\\\", "\\.").replaceAll("/", "\\.").replaceAll(".class", "");
                    preBean.add(packName);
                }
                //TODO 处理其他后缀
            }
        }

    }

    public void load() throws Exception {
        for (String s : preBean) {
            System.out.println("查看:"+s);
//            Class<?> aClass = loader.loadClass(s);

        }
    }

}
