package base.classloader.bytes.proxy.two;

/**
 * 测试动态代理技术区别 
 * Created by cd_huang on 2017/6/3. 
 */  
public class TestProxy {  
    public static void main(String args[]) {  
        InvokeHandler handler =new InvokeHandler();  
        long time = System.currentTimeMillis();  
        TestService test1 =ProxyEnum.JDK_PROXY.newProxyInstance(TestService.class,handler);  
        time = System.currentTimeMillis() - time;  
        System.out.println("Create JDK Proxy: " + time + " ms");  
        time = System.currentTimeMillis();  
        TestService test2 =ProxyEnum.BYTE_BUDDY_PROXY.newProxyInstance(TestService.class,handler);  
        time = System.currentTimeMillis() - time;  
        System.out.println("Create byteBuddy Proxy: " + time + " ms");  
        time = System.currentTimeMillis();  
        TestService test3 =ProxyEnum.CGLIB_PROXY.newProxyInstance(TestService.class,handler);  
        time = System.currentTimeMillis() - time;  
        System.out.println("Create CGLIB Proxy: " + time + " ms");  
        time = System.currentTimeMillis();  
//        TestService test4 =ProxyEnum.JAVASSIST_BYTECODE_PROXY.newProxyInstance(TestService.class,handler);
//        time = System.currentTimeMillis() - time;
//        System.out.println("Create JAVASSIST Bytecode Proxy: " + time + " ms");
        time = System.currentTimeMillis();  
        TestService test5 =ProxyEnum.JAVASSIST_DYNAMIC_PROXY.newProxyInstance(TestService.class,handler);  
        time = System.currentTimeMillis() - time;  
        System.out.println("Create JAVASSIST Proxy: " + time + " ms");  
        String s ="proxy";  
        System.out.println("----------------");  
        for (int i = 0; i <10; i++) {  
            test(test1, "Run JDK Proxy: ",s);  
            test(test2, "Run byteBuddy Proxy: ",s);  
            test(test3, "Run CGLIB Proxy: ",s);  
//            test(test4, "Run JAVASSIST Bytecode Proxy: ",s);
            test(test5, "Run JAVASSIST Proxy: ",s);  
            System.out.println("----------------");  
        }  
  
    }  
    private static void test(TestService service, String label,String s) {  
        service.test(s); // warm up  
        int count = 100000000;  
        long time = System.currentTimeMillis();  
        for (int i = 0; i < count; i++) {  
            service.test(s);  
        }  
        time = System.currentTimeMillis() - time;  
        System.out.println(label + time + " ms, ");  
    }  
}  