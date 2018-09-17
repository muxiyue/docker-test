package rpc;

public class RpcClient {
    // 调用服务
    public static void main(String[] args) {
        RpcProxyClient<IHello> rpcClient = new RpcProxyClient<>();

        IHello hello = rpcClient.proxyClient(IHello.class, "localhost" , "8000");
        String s = hello.sayHello("dd");
        System.out.println(s);
    }
}