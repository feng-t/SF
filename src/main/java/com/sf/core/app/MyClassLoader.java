package com.sf.core.app;

import com.sf.core.load.ClassHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class MyClassLoader extends ClassLoader {
    private static HashMap<String, byte[]> objs = new HashMap<>();
    private Class<?> application;
    private static String prefix;


    protected MyClassLoader(Class<?> application) {
        this.application = application;
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = objs.get(name);
        return defineClass(name, bytes, 0, bytes.length);
    }

    public void parsing() throws Exception {
        parsing(null);
    }

    protected void parsing(ClassHandler c) throws Exception {
        URL resource = application.getClassLoader().getResource("");
        assert resource != null;
        prefix = new File(resource.toURI()).getCanonicalPath().replaceAll("\\\\", "/") + "/";// resource.toURI().getPath();
        URL url = application.getResource("");
        recursivePrintFile(new File(url.toURI()), (file) -> {
            String s = file.getCanonicalPath().replaceAll("\\\\", "/").split(prefix)[1];
            String classname = s.replaceAll("/", ".").substring(0, s.lastIndexOf("."));
            byte[] bytes = getClassBytes(file);
            objs.put(classname, bytes);
        });
        objs.keySet().forEach((key) -> {
            try {
                Class<?> aClass = loadClass(key);
                if (c != null) {
                    c.apply(aClass);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private byte[] getClassBytes(File file) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        return bytes;
    }

    private void recursivePrintFile(File file, FileHandler handler) throws IOException, ClassNotFoundException {
        if (file == null) {
            return;
        }
        if (file.isFile()) {
            if (handler != null) {
                handler.apply(file);
            }
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null)
                for (File f : files) {
                    recursivePrintFile(f, handler);
                }
        }
    }

    public interface FileHandler {
        void apply(File f) throws IOException, ClassNotFoundException;
    }
}
