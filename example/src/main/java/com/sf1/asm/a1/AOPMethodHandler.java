package com.sf1.asm.a1;

import com.sun.xml.internal.ws.org.objectweb.asm.AnnotationVisitor;
import com.sun.xml.internal.ws.org.objectweb.asm.MethodAdapter;
import com.sun.xml.internal.ws.org.objectweb.asm.MethodVisitor;
import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public class AOPMethodHandler extends MethodAdapter {
    String methodName;
    String superName;
    public AOPMethodHandler(String methodName,MethodVisitor mv,String superName) {
        super(mv);
        this.methodName=methodName;
        this.superName=superName;
    }

    @Override
    public void visitCode() {

        visitMethodInsn(Opcodes.INVOKESTATIC, MethodAspect.class.getName().replace(".","/"),"beforeMethod","()V");
        super.visitCode();
    }

    @Override
    public void visitInsn(int opcode) {
        super.visitInsn(opcode);
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc) {
        // 调用父类的构造函数时invoke special
        if (opcode == Opcodes.INVOKESPECIAL && name.equals("<init>")) {
            owner = superName;
        }
        super.visitMethodInsn(opcode, owner, name, desc);// 改写父类为 superClassName
    }

    /**
     * 方法注解
     * @param desc
     * @param visible
     * @return
     */
    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        System.out.println("AOPMethodHandler:"+desc);
        return super.visitAnnotation(desc, visible);
    }
}
