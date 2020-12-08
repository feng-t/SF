package com.sf1.asm.a1;

import com.sf.app.ApplicationContext;
import com.sun.xml.internal.ws.org.objectweb.asm.AnnotationVisitor;
import com.sun.xml.internal.ws.org.objectweb.asm.ClassAdapter;
import com.sun.xml.internal.ws.org.objectweb.asm.ClassVisitor;
import com.sun.xml.internal.ws.org.objectweb.asm.MethodVisitor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ClassAopMethodVisitor extends ClassAdapter {
    private Set<String> annotations= Collections.synchronizedSet(new HashSet<>());
    private String superClassName;
    public ClassAopMethodVisitor(ClassVisitor cv) {
        super(cv);

    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        superClassName=name;
        super.visit(version, access, name+"$0", signature, superClassName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv=cv.visitMethod(access, name, desc, signature, exceptions);

        //根据anno获取注解类型
        return new AOPMethodHandler(name,mv,superClassName);

    }

    /**
     * 类注解
     * @param desc
     * @param visible
     * @return
     */
    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        annotations.add(desc);
        return super.visitAnnotation(desc, visible);
    }



}
