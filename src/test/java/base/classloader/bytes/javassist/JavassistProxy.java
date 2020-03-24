package base.classloader.bytes.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;
import org.junit.Test;

import java.lang.reflect.Method;
 
/**
 * javassist 生成动态代理可以使用两种方式，
 * 一种使用代理工厂创建，和普通的JDK动态代理和 CGLIB类似，
 * 另一种则可以使用 动态代码创建
 */
public class JavassistProxy {

 
    // 代理工厂创建动态代理
    @Test
    public   void testJavassistFactoryProxy() throws Exception {
        // 创建代理工厂
        ProxyFactory proxyFactory = new ProxyFactory();
 
        // 设置被代理类的类型
        proxyFactory.setSuperclass(ObjTobeProxy.class);
 
        // 创建代理类的class
        Class<ProxyObject> proxyClass = proxyFactory.createClass();
 
        // 创建对象
        ObjTobeProxy proxyTest = (ObjTobeProxy)proxyClass.newInstance();
 
        ((ProxyObject) proxyTest).setHandler(new MethodHandler() {
            // 真实主题
            ObjTobeProxy test = new ObjTobeProxy();
 
            public Object invoke(Object self, Method thisMethod,
                                 Method proceed, Object[] args) throws Throwable {
                String before = "before ";
                Object str = thisMethod.invoke(test, args);
                String after = " after";
                return before + str + after;
            }
        });
        String exe = proxyTest.execute();
        System.out.println(exe);
    }
 
    // 动态代码创建的例子
    // 下面例子使用 Javassist 的 API成功组织出代理类的一个子类，可以看出 添加构造函数，添加属性，
    // 添加方法，内容 都是通过字符串类型完成即可。 通过 Javassist 强大的字节生成能力可以达到动态
    // 增加类和实现动态代理的功能.
    @Test
    public void testJavassistDefineClass() throws Exception  {
        // 创建类池，true 表示使用默认路径
        ClassPool classPool = new ClassPool(true);
 
        String className = ObjTobeProxy.class.getName();
        // 创建一个类 RayTestJavassistProxy
        CtClass ctClass = classPool.makeClass(className + "JavassistProxy");
 
        // 添加超类
        // 设置 RayTestJavassistProxy 的父类是 ObjTobeProxy.
        ctClass.setSuperclass(classPool.get(ObjTobeProxy.class.getName()));
 
        // 添加默认构造函数
        ctClass.addConstructor(CtNewConstructor.defaultConstructor(ctClass));
 
        // 添加属性
        ctClass.addField(CtField.make("public " + className + " real = new " +
                    className + "();", ctClass));
 
        // 添加方法，里面进行动态代理 logic
        ctClass.addMethod(CtNewMethod.make("public String execute() { return \"before \" + real.execute() + \" after\";}",
                ctClass));
        Class<ObjTobeProxy> testClass = ctClass.toClass();
        ObjTobeProxy test = testClass.newInstance();
        String exe = test.execute();
        System.out.println(exe);
 
 
    }
}
 
 