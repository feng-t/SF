package com.sf1.asm.a1;

import com.sf.app.ApplicationContext;
import com.sun.xml.internal.ws.org.objectweb.asm.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 处理aop方法，
 */
public class AOPMethodHandler extends MethodAdapter {
    private Set<String> annotations= Collections.synchronizedSet(new HashSet<>());
    String methodName;
    String superName;
    //通过context获得factory，factory包含所有bean的信息
    private ApplicationContext applicationContext;
    public AOPMethodHandler(String methodName,MethodVisitor mv,String superName) {
        super(mv);
        this.methodName=methodName;
        this.superName=superName;
        applicationContext=ApplicationContext.getInstance();
    }

    @Override
    public void visitCode() {
        visitMethodInsn(Opcodes.INVOKESTATIC, MethodAspect.class.getName().replace(".", "/"), "beforeMethod", "()V");

        super.visitCode();
    }

    @Override
    public void visitInsn(int opcode) {
        if(opcode == Opcodes.ARETURN || opcode == Opcodes.RETURN ) {
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, MethodAspect.class.getName().replace(".","/"),"afterMethod","()V");
        }
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
        annotations.add(desc);
        return super.visitAnnotation(desc, visible);
    }
}
