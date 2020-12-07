package com.sf1.asm;

import com.sun.xml.internal.ws.org.objectweb.asm.ClassAdapter;
import com.sun.xml.internal.ws.org.objectweb.asm.ClassVisitor;
import com.sun.xml.internal.ws.org.objectweb.asm.MethodVisitor;

class AddSecurityCheckClassAdapter extends ClassAdapter {
    String enhancedSuperName;

    public AddSecurityCheckClassAdapter(ClassVisitor cv) {
        //Responsechain 的下一个 ClassVisitor，这里我们将传入 ClassWriter，
        // 负责改写后代码的输出
        super(cv);
    }

    public void visit(final int version, final int access, final String name,
                      final String signature, final String superName,
                      final String[] interfaces) {
        String enhancedName = name + "$0";  // 改变类命名
        enhancedSuperName = name; // 改变父类，这里是”Account”
        super.visit(version, access, enhancedName, signature,
                enhancedSuperName, interfaces);
//        super.visit(version, access, name, signature, superName, interfaces);

    }

    // 重写 visitMethod，访问到 "operation" 方法时，
    // 给出自定义 MethodVisitor，实际改写方法内容
    public MethodVisitor visitMethod(final int access, final String name,
                                     final String desc, final String signature, final String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        MethodVisitor wrappedMv = mv;
        if (mv != null) {
            if (name.equals("operation")) {
                wrappedMv = new AddSecurityCheckMethodAdapter(mv);
            }
            else if (name.equals("<init>")) {
                wrappedMv = new ChangeToChildConstructorMethodAdapter(mv,
                        enhancedSuperName);
            }
        }
        return wrappedMv;
    }
}