package com.sf1.asm;

import com.sun.xml.internal.ws.org.objectweb.asm.ClassAdapter;
import com.sun.xml.internal.ws.org.objectweb.asm.ClassReader;
import com.sun.xml.internal.ws.org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecureAccountGenerator {

//    private static AccountGeneratorClassLoader classLoader = new AccountGeneratorClassLoader();

    private static Class secureAccountClass;

    public Object generateSecureAccount() throws ClassFormatError,
            InstantiationException, IllegalAccessException, IOException {
        if (null == secureAccountClass) {
            String className = Account.class.getName();// + "$0";
            String path = Thread.currentThread().getContextClassLoader().getResource(".").getPath() + className;
            ClassReader cr = new ClassReader(Account.class.getName());
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            ClassAdapter classAdapter = new AddSecurityCheckClassAdapter(cw);
            cr.accept(classAdapter, ClassReader.SKIP_DEBUG);
            byte[] data = cw.toByteArray();
            File file = new File(path.replaceAll("\\.", "/") + ".class");
            FileOutputStream fout = new FileOutputStream(file);
            fout.write(data);
            fout.close();
            secureAccountClass=Account.class;
//            secureAccountClass = classLoader.defineClassFromClassFile(
//                    className, data);
        }
        return secureAccountClass.newInstance();
    }

    private static class AccountGeneratorClassLoader extends ClassLoader {
        public Class defineClassFromClassFile(String className, byte[] classFile) throws ClassFormatError {
            return defineClass(className, classFile, 0, classFile.length);
        }
    }
}