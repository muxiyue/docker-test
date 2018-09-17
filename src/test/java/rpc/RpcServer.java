package rpc;

public class RpcServer {
    //发布服务
    public static void main(String[] args) {
        RpcProxyServer server = new RpcProxyServer();
        server.publisherServer(new HelloService(), 8000);
    }
}