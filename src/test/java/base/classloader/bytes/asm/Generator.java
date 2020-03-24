package base.classloader.bytes.asm;

import jdk.internal.org.objectweb.asm.*;

import java.io.File;
import java.io.FileOutputStream;

class AddSecurityCheckClassAdapter extends ClassVisitor {
    public AddSecurityCheckClassAdapter( ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
	}
    // 重写 visitMethod，访问到 "operation" 方法时，
    // 给出自定义 MethodVisitor，实际改写方法内容
    public MethodVisitor visitMethod(final int access, final String name,
        final String desc, final String signature, final String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature,exceptions);
        MethodVisitor wrappedMv = mv;
        if (mv != null) {
            // 对于 "operation" 方法
            if (name.equals("operation")) {
                // 使用自定义 MethodVisitor，实际改写方法内容
                wrappedMv = new AddSecurityCheckMethodAdapter(mv);
            }
        }
        return wrappedMv;
    }
}


class AddSecurityCheckMethodAdapter extends MethodVisitor {
    public AddSecurityCheckMethodAdapter(MethodVisitor mv) {
        super(Opcodes.ASM5,mv);
    }
    public void visitCode() {
        visitMethodInsn(Opcodes.INVOKESTATIC, "base/classloader/classloader/asm/SecurityChecker",
            "checkSecurity", "()Z");
        super.visitCode();
    }
}


public class Generator{
    public static void main(String args[]) throws Exception {
        ClassReader cr = new ClassReader("classloader.asm.Account");
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS|ClassWriter.COMPUTE_FRAMES);
        AddSecurityCheckClassAdapter classAdapter = new AddSecurityCheckClassAdapter(cw);
        cr.accept(classAdapter, ClassReader.SKIP_DEBUG);
        byte[] data = cw.toByteArray();
        File file = new File("bin/classloader/asm/Account.class");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fout = new FileOutputStream(file);
        fout.write(data);
        fout.close();
    }
}

