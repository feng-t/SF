package com.sf.core.load;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class FileSystemClassLoader extends ClassLoader {
    private static HashMap<String, byte[]> objs = new HashMap<>();
    private Class<?> application;
    public FileSystemClassLoader(Class<?> application){
        this.application=application;
    }
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
//        byte[] classData = getClassData(name);
//        if (classData==null){
//            throw new ClassNotFoundException(name);
//        }
//        try {
//            return defineClass(name, classData, 0, classData.length);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        throw new ClassNotFoundException();
        byte[] bytes = objs.get(name);
        return defineClass(name, bytes, 0, bytes.length);
    }

    public void parsing(){
//        application
        ClassLoader loader = Thread.currentThread().getContextClassLoader();


    }

    private byte[] getClassData(final InputStream ins,boolean close) throws IOException {
        if (ins==null){
            throw new IOException("Class not find");
        }
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();){
            byte[] buffs = new byte[2048];
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

}
