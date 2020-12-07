package com.sf1.asm.a1;

import com.sun.xml.internal.ws.org.objectweb.asm.*;

public class ClassAopMethodVisitor extends ClassAdapter {
    private String superClassName;
    String anno;

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
        System.out.println(anno);
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
        System.out.println("visitAnnotation :"+desc);
        anno=desc;
        return super.visitAnnotation(desc, visible);
    }



}
