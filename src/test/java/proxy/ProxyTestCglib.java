package proxy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.assertj.core.internal.cglib.core.DebuggingClassWriter;
import org.assertj.core.internal.cglib.proxy.Enhancer;
import org.assertj.core.internal.cglib.proxy.MethodInterceptor;
import org.assertj.core.internal.cglib.proxy.MethodProxy;
import proxy.cglib.UserDao;

import java.lang.reflect.Method;

class CglibProxy implements MethodInterceptor
{  
    private Log logger = LogFactory.getLog(CglibProxy.class);


    @Override  
    public Object intercept(Object proxy, Method method, Object[] params,  
            MethodProxy methodProxy) throws Throwable {
        logger.info("*********代理方法执行前************");  
        Object retObj = methodProxy.invokeSuper(proxy, params);
        //执行目标对象的方法

        // 这个地方invoke 需要传入原始的对象，不能传入生成的子对象，否则会死循环。
//        Object retObj = method.invoke(proxy, params);
        logger.info("*********代理方法执行后************");  
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
public class ProxyTestCglib {  
  
    public static void main(String[] args) {

        // 保存字节码文件
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/Users/chenshengpeng/F_Work/demo/renren-generator/src/test/java/proxy/file/");
        //创建目标对象  
        LoginService loninService = new LoginServiceImpl();  
        UserService userService = new UserServiceImpl();  
        CglibProxy proxy = new CglibProxy();  
        //创建代理对象  
        LoginService loninService$Proxy = (LoginService)proxy.newProxy(loninService);
        UserService userService$Proxy = (UserService)proxy.newProxy(userService);



        loninService$Proxy.checkUser();
        userService$Proxy.getUserName();


        loninService$Proxy = (LoginService)proxy.newProxy2(LoginServiceImpl.class);
        userService$Proxy = (UserService)proxy.newProxy2(UserServiceImpl.class);



        loninService$Proxy.checkUser();
        userService$Proxy.getUserName();


        //目标对象
        UserDao target = new UserDao();

        //代理对象
        UserDao userDao = (UserDao)proxy.newProxy(target);

        //执行代理对象的方法
        userDao.save();

    }  
}  