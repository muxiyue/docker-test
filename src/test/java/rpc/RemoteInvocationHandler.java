package rpc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RemoteInvocationHandler implements InvocationHandler {

    private String host;
    private String port;

    public RemoteInvocationHandler(String host, String port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //                    try (Socket socket = new Socket("localhost", 8000)) {
        //                        try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
        //                            oos.writeUTF(method.getName());
        //                            oos.writeObject(args);
        //                            oos.flush();
        //
        //                            try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
        //                                return ois.readObject();
        //                            }
        //                        }
        //                    }

        RpcRequest rpcRequest = new RpcRequest(method.getDeclaringClass().getName(), method.getName(), args);
        RpcNetTransport rpcNetTransport = new RpcNetTransport(host, port);
        Object result = rpcNetTransport.send(rpcRequest);
        return result;
    }
}
