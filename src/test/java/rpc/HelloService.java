package rpc;

public class HelloService implements IHello {
    public String sayHello(String info) {
        String result = "hello : " + info;
        System.out.println(result);
        return result;
    }
}