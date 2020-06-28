package com.sf.core.load;

import com.sf.core.utils.ClassUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

public class DefaultClassLoader {

    private Set<String>rescues = new HashSet<>();
    private Class<?> application;
    public DefaultClassLoader(Class<?> application){
        this.application=application;
    }
    public DefaultClassLoader preloadClass()throws Exception {
        String packName = ClassUtils.packRevPath(application);
        Enumeration<URL> resources = getClass().getClassLoader().getResources(packName);
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            File file = new File(url.toURI()).getAbsoluteFile();
            String newPackName = file.getAbsolutePath();
            newPackName = newPackName.substring(newPackName.indexOf(packName));
            addClassFiles(file,newPackName);
        }
        return this;
    }
    private void addClassFiles(File file,String packName) throws IOException {
        if (file!=null) {
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
                    rescues.add(packName);
                }
                //TODO 处理其他后缀
            }
        }
    }
    private byte[] getClassData(final InputStream ins,boolean close) throws IOException {
        if (ins==null){
            throw new IOException("Class not find");
        }
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();){
            byte[] buffs = new byte[4096];
            int byteRead=0;
            while ((byteRead = ins.read(buffs,0,buffs.length))!=-1){
                outputStream.write(buffs,0,byteRead);
            }
            return outputStream.toByteArray();
        } finally {
            if (close){
                ins.close();
            }
        }
    }

    public void parsing(ClassHandler ch) throws Exception {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        for (String rescue : rescues) {
            ch.apply(loader.loadClass(rescue));
        }
    }

}
