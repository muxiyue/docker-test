package rpc;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//构建一个ServerSocket服务监听来自客户端的请求。
//    接收请求的数据。（方法名和参数）
//    根据请求的数据（方法名和参数），使用反射调用相应的服务。
//    输出服务的响应数据
public class RpcProxyServer {

    ExecutorService executors = Executors.newCachedThreadPool();

    public void publisherServer(Object service, int port) {
        try (ServerSocket ss = new ServerSocket(port)) {
            while (true) {

                try  {
                    Socket socket = ss.accept();

//                try (Socket socket = ss.accept()) {
//                    try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
//                        String method = ois.readUTF();
//                        Object[] objs = (Object[]) ois.readObject();
//                        Class<?>[] types = new Class[objs.length];
//                        for (int i = 0; i < types.length; i++) {
//                            types[i] = objs[i].getClass();
//                        }
//                        Method m = HelloService.class.getMethod(method, types);
//                        Object obj = m.invoke(hello, objs);
//
//                        try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
//                            oos.writeObject(obj);
//                            oos.flush();
//                        }
//                    }
                    executors.execute(new ProcessHandler(socket, service));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
