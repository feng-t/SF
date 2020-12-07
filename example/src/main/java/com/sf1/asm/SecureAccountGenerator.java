package com.sf1.asm;

import com.sun.xml.internal.ws.org.objectweb.asm.ClassAdapter;
import com.sun.xml.internal.ws.org.objectweb.asm.ClassReader;
import com.sun.xml.internal.ws.org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecureAccountGenerator {

    private static AccountGeneratorClassLoader classLoader = new AccountGeneratorClassLoader();

    private static Class secureAccountClass;

    public Object generateSecureAccount(String className) throws ClassFormatError,
            InstantiationException, IllegalAccessException, IOException {
        if (null == secureAccountClass) {
            ClassReader cr = new ClassReader(className);
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            ClassAdapter classAdapter = new AddSecurityCheckClassAdapter(cw);
            cr.accept(classAdapter, ClassReader.SKIP_DEBUG);
            byte[] data = cw.toByteArray();
//            String path = Thread.currentThread().getContextClassLoader().getResource(".").getPath() + className+"$0";
//            File file = new File(path.replaceAll("\\.", "/") + ".class");
//            FileOutputStream fout = new FileOutputStream(file);
//            fout.write(data);
//            fout.close();
            secureAccountClass = classLoader.defineClassFromClassFile(
                    className+"$0", data);
        }
        return secureAccountClass.newInstance();
    }

    private static class AccountGeneratorClassLoader extends ClassLoader {
        public Class defineClassFromClassFile(String className, byte[] classFile) throws ClassFormatError {
            return defineClass(className, classFile, 0, classFile.length);
        }
    }
}