package rpc;

import java.lang.reflect.Proxy;

//构建一个Socket，连接远程服务。
//    向远程服务发送数据。（方法名和方法参数）
//    接收远程服务响应的数据。
public class RpcProxyClient<T> {

    public T proxyClient(Class<T> interfaceCls, String host, String port) {
        // clazz.getInterfaces()
        return (T) Proxy.newProxyInstance(interfaceCls.getClassLoader(), new Class<?>[]{interfaceCls}, new RemoteInvocationHandler(host, port));
    }
}