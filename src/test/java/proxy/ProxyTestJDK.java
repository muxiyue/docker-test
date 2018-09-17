package proxy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
  
  
interface LoginService{  
    public boolean checkUser();  
}  
  
class LoginServiceImpl implements LoginService{  
    @Override  
    public boolean checkUser() {  
        System.out.println("LoginServiceImpl  checkUser");  
        return false;  
    }

    @Override public String toString() {
        return super.toString();
    }
}
  
interface UserService{  
    public String getUserName();  
}  
  
class UserServiceImpl implements UserService{  
  
    @Override  
    public String getUserName() {  
        System.out.println("UserServiceImpl getUserName");  
        return null;  
    }  
      
}  
  
class ProxyHandler implements InvocationHandler{  
    private Object target;  
    private Log logger = LogFactory.getLog(ProxyHandler.class);  
      
    public void setTarget(Object target) {  
        this.target = target;  
    }  
    @Override  
    public Object invoke(Object proxy, Method method, Object[] param)  
            throws Throwable {  
        logger.info("*********代理方法执行前************");  
        Object retObj = method.invoke(target, param);
        logger.info("*********代理方法执行后************");  
        return retObj;  
    }  
      
}  
  
  
public class ProxyTestJDK {  
    public static void main(String[] args) throws NoSuchMethodException {

        System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        //创建目标对象  
        LoginService loninService = new LoginServiceImpl();  
        UserService userService = new UserServiceImpl();

        ProxyHandler proxyHandler = new ProxyHandler();




        //创建LoginService代理对象
        proxyHandler.setTarget(loninService);
        LoginService loninService$Proxy = (LoginService) Proxy.newProxyInstance(loninService.getClass().getClassLoader(),
            loninService.getClass().getInterfaces(), proxyHandler);

        System.out.println("=== ========" + loninService$Proxy.toString());


        loninService$Proxy = (LoginService) Proxy.newProxyInstance(loninService.getClass().getClassLoader(),
            loninService.getClass().getInterfaces(), new MapperProxyOwn<LoginService>(null, null, null));
        System.out.println("=== " + loninService$Proxy.toString());


        loninService$Proxy.checkUser();

        //创建UserService代理对象
        proxyHandler.setTarget(userService);
        UserService userService$Proxy = (UserService) Proxy.newProxyInstance(userService.getClass().getClassLoader(),
            userService.getClass().getInterfaces(), proxyHandler);
        userService$Proxy.getUserName();
          


        // 继承于哪个类的方法，就是哪个类的，如果子类重写，则method.getDeclaringClass()就是子类
        Method method = loninService.getClass().getMethod("toString", null);
        System.out.println(method.getDeclaringClass());
        method = loninService.getClass().getDeclaredMethod("checkUser", null);
        System.out.println(method.getDeclaringClass());
    }

}