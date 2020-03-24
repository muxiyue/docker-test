package base.classloader.bytes;

import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.MethodVisitor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static jdk.internal.org.objectweb.asm.Opcodes.*;

public class Calc {
	public int calc() {
		int a = 500;
		int b = 200;
		int c = 50;
		return (a + b) / c;
	}

	// 操控指令 处理。
	public static void main(String[] args)
		throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS|ClassWriter.COMPUTE_FRAMES);
		cw.visit(V1_7, ACC_PUBLIC, "Example", null, "java/lang/Object", null);
		MethodVisitor mw = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null,  null);
		mw.visitVarInsn(ALOAD, 0);  //this 入栈
		mw.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
		mw.visitInsn(RETURN);
		mw.visitMaxs(0, 0);
		mw.visitEnd();
		mw = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main",  "([Ljava/lang/String;)V", null, null);
		mw.visitFieldInsn(GETSTATIC, "java/lang/System", "out",  "Ljava/io/PrintStream;");
		mw.visitLdcInsn("Hello world!");
		mw.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",  "(Ljava/lang/String;)V");
		mw.visitInsn(RETURN);
		mw.visitMaxs(0,0);
		mw.visitEnd();
		byte[] code = cw.toByteArray();

		ClassLoader cl=Calc.class.getClassLoader();
		Method md_defineClass=ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class,int.class,int.class);
		md_defineClass.setAccessible(true);
		Class exampleClass = (Class) md_defineClass.invoke(cl, "Example", code,0,code.length);
//		Class exampleClass = loader
//			.defineClass("Example", code, 0, code.length);
		exampleClass.getMethods()[0].invoke(null, new Object[] { null });

	}
}
