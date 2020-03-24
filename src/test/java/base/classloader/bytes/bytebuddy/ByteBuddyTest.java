package base.classloader.bytes.bytebuddy;

import base.classloader.bytes.proxy.two.InvokeHandler;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.NamingStrategy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.Test;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class ByteBuddyTest {

//    subclass方法声明了创建的类的父类，
//    method声明了要拦截的方法（实际底层是一个方法过滤器），
//    而intercept则对上一步过滤出来的方法进行了实际拦截处理
    @org.junit.Test
    public void test() throws Exception {
        String toString = "hello ByteBuddy test";
        DynamicType.Unloaded<Object> unloaded = new ByteBuddy()
                .subclass(Object.class)
                .method(named("toString"))
                .intercept(FixedValue.value(toString))
                .make();

        Class<? extends Object> clazz = unloaded
            .load(ByteBuddyTest.class.getClassLoader())
            .getLoaded();
        System.out.println(clazz.newInstance().toString());
        System.out.println(Object.class.newInstance().toString());
    }


    @org.junit.Test
    public void test2() throws Exception {
        String toString = "hello ByteBuddy test";
        DynamicType.Unloaded<Object> unloaded = new ByteBuddy()
            .subclass(Object.class)
            // 指定类的名称
            .name("com.ByteBuddyObject")
            .method(named("toString"))
            .intercept(FixedValue.value(toString))
            .make();

        Class<? extends Object> clazz = unloaded
            .load(ByteBuddyTest.class.getClassLoader())
            .getLoaded();

        System.out.println(clazz.newInstance().toString());
        System.out.println(Object.class.newInstance().toString());
        System.out.println(clazz.getName());

    }




    // 通过接口和实现类实现逻辑重写。
    @org.junit.Test
    public void test3() throws Exception {
        DynamicType.Unloaded<People> unloaded = new ByteBuddy()
            // 使用接口
            .subclass(People.class)
            .name("com.ByteBuddyObject")
            .method(named("say"))
            // 使用外部类进行代理。
            .intercept(MethodDelegation.to(new Delegate()))
            .make();

        Class<? extends People> clazz = unloaded
            .load(ByteBuddyTest.class.getClassLoader())
            .getLoaded();

        System.out.println(clazz.getInterfaces()[0]);
        System.out.println(clazz.newInstance().say());
    }

    public interface People{
        String say();
    }

    public class Delegate {
        public String say() {
            return "hello Delegate";
        }

        public String say2() {
            return "hello Delegate2";
        }
    }




    @org.junit.Test
    public void test4() throws Exception {

        // 指定代理方法名
        People hi = build("say");
        People hello = build("say2");

       System.out.println(hi.say());
        System.out.println(hello.say());
    }

    private People build(String method) throws Exception {
        DynamicType.Unloaded<People> unloaded = new ByteBuddy()
            .subclass(People.class)
            .name("com.joe.ByteBuddyObject")
            .method(named("say"))
            .intercept(MethodDelegation
                .withDefaultConfiguration()
                .filter(ElementMatchers.named(method))
                .to(new Delegate())
            )
            .make();

        Class<? extends People> clazz = unloaded
            .load(ByteBuddyTest.class.getClassLoader())
            .getLoaded();
        return clazz.newInstance();
    }


    // 自定义命名规则
    @Test
    public void test5() throws IllegalAccessException, InstantiationException {
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
            .with(new NamingStrategy.AbstractBase() {

                @Override
                public String name(TypeDescription superClass) {
                    return "i.love.ByteBuddy." + superClass.getSimpleName();
                }
            })
            .subclass(Object.class)
            .make();

        Class<?> clazz = dynamicType
            .load(ByteBuddyTest.class.getClassLoader())
            .getLoaded();

        System.out.println(clazz.getName());
    }




    // 类重新定义
    @Test
    public void test6() {
        ByteBuddyAgent.install();
        Foo foo = new Foo();
        new ByteBuddy()
            // 替换
            .redefine(Bar.class)
            .name(Foo.class.getName())
            .make()
            .load(Foo.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());

        System.out.println(foo.m());
    }


    // 动态代理
    @Test
    public void test7()
        throws IllegalAccessException, InstantiationException, NoSuchFieldException, InterruptedException {

        Class<? extends Bar> cls = new ByteBuddy()
            .subclass(Bar.class)
            .method(ElementMatchers.isDeclaredBy(Bar.class))
            .intercept(MethodDelegation.to(new InvokeHandler(), "m"))
            .make()
            .load(Bar.class.getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
            .getLoaded();


        System.out.println(cls.newInstance().m());



    }







}


class Foo {
    String m() { return "foo"; }
}

class Bar {
    String m() { return "bar"; }
}