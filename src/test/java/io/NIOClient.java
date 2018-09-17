package io;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Objects;
import java.util.Scanner;

/**
 *
 * Created by ascend on 2017/6/13 10:36.
 */
public class NIOClient {

    @org.junit.Test
    public void test(){
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(NIOServer.REMOTE_IP,NIOServer.PORT));
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.write("exit".getBytes());
            out.flush();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Thread(new ClientThread()).start();
    }

    public void checkStatus(String input){
        if ("exit".equals(input.trim())) {
            System.out.println("系统即将退出，bye~~");
            System.exit(0);
        }
    }


}

class ClientThread implements Runnable {
    private SocketChannel sc;
    private boolean isConnected = false;
    NIOClient client = new NIOClient();

    public ClientThread(){
        try {
            sc = SocketChannel.open();
            sc.configureBlocking(false);
            sc.connect(new InetSocketAddress(NIOServer.REMOTE_IP,NIOServer.PORT));
            while (!sc.finishConnect()) {
                System.out.println("同" + NIOServer.REMOTE_IP + "的连接正在建立，请稍等！");
                Thread.sleep(10);
            }
            System.out.println("连接已建立，待写入内容至指定ip+端口！时间为" + System.currentTimeMillis());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true){
                Scanner scanner = new Scanner(System.in);
                System.out.print("请输入要发送的内容：");
                String writeStr = scanner.nextLine();
                client.checkStatus(writeStr);
                ByteBuffer bb = ByteBuffer.allocate(writeStr.length());
                bb.put(writeStr.getBytes());
                bb.flip(); // 写缓冲区的数据之前一定要先反转(flip)
                while (bb.hasRemaining()){
                    sc.write(bb);
                }
                bb.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (Objects.nonNull(sc)) {
                try {
                    sc.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }finally {
            if (Objects.nonNull(sc)) {
                try {
                    sc.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
