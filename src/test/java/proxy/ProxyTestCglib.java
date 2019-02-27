package proxy;

import org.assertj.core.internal.cglib.core.DebuggingClassWriter;
import proxy.cglib.UserDao;


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