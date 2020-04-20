package com.sf.core.load;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;

public class FileSystemClassLoader extends ClassLoader {
    public FileSystemClassLoader(String rootDir) {

    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = null;
        try {
            classData = getClassData(name);
            return defineClass(name, classData, 0, classData.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new ClassNotFoundException();

    }

    private byte[] getClassData(String name) {
        try {
            InputStream ins = new FileInputStream(name);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffs = new byte[ins.available()];
            ins.read(buffs, 0, buffs.length);
            return baos.toByteArray();
        } catch (Exception e) {
        }
        return null;
    }

}
