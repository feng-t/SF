package com.sf1.asm;

import com.sun.xml.internal.ws.org.objectweb.asm.Label;
import com.sun.xml.internal.ws.org.objectweb.asm.MethodAdapter;
import com.sun.xml.internal.ws.org.objectweb.asm.MethodVisitor;
import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

class AddSecurityCheckMethodAdapter extends MethodAdapter {
    String superClassName;
    public AddSecurityCheckMethodAdapter(MethodVisitor mv,String superClassName) {
        super(mv);
        this.superClassName=superClassName;
        System.out.println("AddSecurityCheckMethodAdapter构造方法------------------>");
    }

    //方法执行前运行
    public void visitCode() {

//        System.out.println("AddSecurityCheckMethodAdapter.visitCode处理---------------------->");
//        visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//        visitLdcInsn("test");
//        visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");

//        visitFieldInsn(Opcodes.INVOKEVIRTUAL,"com/sf1/asm/OperationHandler", "handler", "()V");
    }

}