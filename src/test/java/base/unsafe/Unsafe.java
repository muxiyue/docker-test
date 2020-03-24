//package base.unsafe;
//
//import sun.misc.VM;
//import sun.reflect.CallerSensitive;
//import sun.reflect.Reflection;
//
//public final class Unsafe {
//    // 单例对象
//    private static final Unsafe theUnsafe;
//
//    private Unsafe() {
//    }
//
//    // 其一，从getUnsafe方法的使用限制条件出发，通过Java命令行命令
//    // -Xbootclasspath/a把调用Unsafe相关方法的类A所在jar包路径追加到默认的bootstrap路径中
//    // ，使得A被引导类加载器加载，从而通过Unsafe.getUnsafe方法安全的获取Unsafe实例。
//    // java -Xbootclasspath/a: ${path} // 其中path为调用Unsafe相关方法的类所在jar包路径
//    @CallerSensitive
//    public static Unsafe getUnsafe() {
//        Class var0 = Reflection.getCallerClass();
//        // 仅在引导类加载器`BootstrapClassLoader`加载时才合法
//        if (!VM.isSystemDomainLoader(var0.getClassLoader())) {
//            throw new SecurityException("MyUnsafe");
//        } else {
//            return theUnsafe;
//        }
//    }
//}