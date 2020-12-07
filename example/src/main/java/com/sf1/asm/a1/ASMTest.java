package com.sf1.asm.a1;

import com.sf1.asm.Account;
import com.sun.xml.internal.ws.org.objectweb.asm.ClassAdapter;
import com.sun.xml.internal.ws.org.objectweb.asm.ClassReader;
import com.sun.xml.internal.ws.org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileOutputStream;

public class ASMTest {
    private static ASMClassLoader loader=new ASMClassLoader();
    public static void main(String[] args) throws Exception {
        Account account = (Account) getBean(Account.class.getName());
        account.operation();
        System.out.println("----------------------------");
        Thread.sleep(1000);
        Account a = new Account();
        a.operation();


    }

    public static Object getBean(String name) throws Exception {
        ClassReader reader = new ClassReader(name);
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        ClassAdapter adapter = new ClassAopMethodVisitor(cw);
        reader.accept(adapter, ClassReader.SKIP_DEBUG);
        byte[] array = cw.toByteArray();
        saveFile(name,array);
//        Class<?> loadClass = ASMTest.class.getClassLoader().loadClass(name);
        Class<?> loadClass = loader.findClass(name+"$0", array);
        return loadClass.newInstance();
    }
    public static void saveFile(String className,byte[] data) throws Exception {
        String path = Thread.currentThread().getContextClassLoader().getResource(".").getPath() + className+"$0";
        File file = new File(path.replaceAll("\\.", "/") + ".class");
        FileOutputStream fout = new FileOutputStream(file);
        fout.write(data);
        fout.close();
    }
    static class ASMClassLoader extends ClassLoader{
        public Class<?> findClass(String name,byte[] data)throws Exception{
            return defineClass(name,data,0,data.length);
        }
    }
}

