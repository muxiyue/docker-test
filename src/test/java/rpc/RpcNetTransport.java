package rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RpcNetTransport {

    private String host;
    private String port;

    public RpcNetTransport(String host, String port) {
        this.host = host;
        this.port = port;
    }

    private Socket newSocket() {
        System.out.println("begin to connect");
        Socket socket = null;
        try {
            socket = new Socket(host, Integer.parseInt(port));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return socket;
    }

    public Object send(RpcRequest rpcRequest) {
        Socket socket = newSocket();

        ObjectOutputStream outputStream = null;
        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(rpcRequest);

            outputStream.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        ObjectInputStream inputStream = null;

        try {
            inputStream = new ObjectInputStream(socket.getInputStream());
            Object result = inputStream.readObject();
            return result;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (outputStream != null) {
                try {
                    outputStream.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}
