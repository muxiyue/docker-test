package proxy.spring;

import org.junit.Test;
import org.springframework.aop.framework.ProxyFactory;
import proxy.CglibProxy;

public class ProxyFactoryTest {
    @Test
    public void classProxy() {
        //代理对象未指定接口，使用CGLIB生成代理类
        ProxyFactory factory = new ProxyFactory();
        factory.setTarget(new MyTarget());
        factory.addAdvice(new AroundInteceptor());
        MyTarget targetProxy = (MyTarget) factory.getProxy();
        targetProxy.printName();
        System.err.println(targetProxy.getClass().getName());
    }

    @Test
    public void cglibTest() {
        MyTarget myTarget = (MyTarget) new CglibProxy().newProxy(new MyTarget());
        myTarget.printName();
    }

    @Test
    public void interfaceProxy() {
        //代理对象指定接口PeopleService，目标类为实现PeopleService的EnglishService，使用JDK proxy生成代理类
        ProxyFactory factory = new ProxyFactory();
        factory.setInterfaces(new Class[] { PeopleService.class });
        factory.addAdvice(new AroundInteceptor());
        factory.setTarget(new EnglishService());
        PeopleService peopleProxy = (PeopleService) factory.getProxy();
        peopleProxy.sayHello();
        System.err.println(peopleProxy.getClass().getName());
    }
}