package com.sf1.asm;

import com.sf.annotation.Bean;
import com.sun.xml.internal.ws.org.objectweb.asm.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

class AddSecurityCheckMethodAdapter extends MethodAdapter {
    String method;
    String superClassName;
    Set<String> annotations=  new HashSet<String>();
    public AddSecurityCheckMethodAdapter(MethodVisitor mv,String superClassName,String method) {
        super(mv);
        this.superClassName=superClassName;
        this.method=method;
        System.out.println("AddSecurityCheckMethodAdapter构造方法------------------>");
    }

    //方法执行前运行
    public void visitCode() {
        if (annotations.contains(Bean.class.getName())){
            System.out.println("dddd");
        }
//        System.out.println("AddSecurityCheckMethodAdapter.visitCode处理---------------------->");
//        visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//        visitLdcInsn("test");
//        visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");

//        visitFieldInsn(Opcodes.INVOKEVIRTUAL,"com/sf1/asm/OperationHandler", "handler", "()V");
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        String name = desc.substring(1,desc.length()-1).replaceAll("/",".");
        annotations.add(name);
        return super.visitAnnotation(desc, visible);
    }

}