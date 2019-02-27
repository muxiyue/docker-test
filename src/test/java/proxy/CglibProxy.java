package proxy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy implements MethodInterceptor
{  
    private Log logger = LogFactory.getLog(CglibProxy.class);

    // 私有方法 没办法代理
    @Override  
    public Object intercept(Object proxy, Method method, Object[] params,
            MethodProxy methodProxy) throws Throwable {
        if (!method.getName().startsWith("newProxy") ) {
            System.out.println("*********代理方法执行前************" + method.getName());
        }

        Object retObj = methodProxy.invokeSuper(proxy, params);
        //执行目标对象的方法

        // 这个地方invoke 需要传入原始的对象，不能传入生成的子对象，否则会死循环。
//        Object retObj = method.invoke(proxy, params);
        if (!method.getName().startsWith("newProxy") ) {
            System.out.println("*********代理方法执行后************" + method.getName());
        }

        return retObj;  
    }


    //返回目标对象的代理对象  
    public Object newProxy(Object target)
    {  
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());  
        enhancer.setCallback(this);
        enhancer.setClassLoader(target.getClass().getClassLoader());
        return enhancer.create();  
    }

    //返回目标对象的代理对象
    public Object newProxy2(Class claaz)
    {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(claaz);
        enhancer.setCallback(this);
        enhancer.setClassLoader(claaz.getClassLoader());
        return enhancer.create();
    }

}