package proxy.spring;

import org.aopalliance.intercept.MethodInterceptor;

import org.aopalliance.intercept.MethodInvocation;

public class AroundInteceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.err.println(invocation.getMethod().getName() + "调用之前");
        Object res = invocation.proceed();
        System.err.println(invocation.getMethod().getName() + "调用之后");
        return res;
    }
}