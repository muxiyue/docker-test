package io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 选择器服务端
 * Created by ascend on 2017/6/9 9:30.
 */
public class NIOServer {
    //    public final static String REMOTE_IP = "192.168.0.44";
    public final static String REMOTE_IP = "127.0.0.1";
    public final static int PORT = 17531;
    private static ByteBuffer bb = ByteBuffer.allocate(1024);
    private static ServerSocketChannel ssc;
    private static boolean closed = false;

    public static void main(String[] args) throws IOException {
        //先确定端口号
        int port = PORT;
        if (args != null && args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        //打开一个ServerSocketChannel
        ssc = ServerSocketChannel.open();
        //获取ServerSocketChannel绑定的Socket
        ServerSocket ss = ssc.socket();
        //设置ServerSocket监听的端口
        ss.bind(new InetSocketAddress(port));
        //设置ServerSocketChannel为非阻塞模式
        ssc.configureBlocking(false);
        //打开一个选择器
        Selector selector = Selector.open();
        //将ServerSocketChannel注册到选择器上去并监听accept事件
        SelectionKey selectionKey = ssc.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("服务端启动监听。。。");

        while (!closed) {
            //这里会发生阻塞，等待就绪的通道,但在每次select()方法调用之间，只有一个通道就绪了。
            int n = selector.select();
            //没有就绪的通道则什么也不做
            if (n == 0) {
                continue;
            }
            //获取SelectionKeys上已经就绪的集合
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            //遍历每一个Key
            while (iterator.hasNext()) {
                SelectionKey sk = iterator.next();
                System.out.println("========" + sk.channel() );
                //通道上是否有可接受的连接
                if (sk.isAcceptable()) {
                    ServerSocketChannel sscTmp = (ServerSocketChannel) sk.channel();
                    SocketChannel sc = sscTmp.accept(); // accept()方法会一直阻塞到有新连接到达。
                    sc.configureBlocking(false);
                    sc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                } else if (sk.isReadable()) {   //通道上是否有数据可读
                    try {
                        readDataFromSocket(sk);
                    } catch (IOException e) {
                        sk.cancel();
                        continue;
                    }
                }
                if (sk.isWritable()) {  //测试写入数据，若写入失败在会自动取消注册该键
                    try {
                        writeDataToSocket(sk);
                    } catch (IOException e) {
                        sk.cancel();
                        continue;
                    }
                }
                //必须在处理完通道时自己移除。下次该通道变成就绪时，Selector会再次将其放入已选择键集中。
                iterator.remove();
            }//. end of while

        }

    }



    /**
     * 发送测试数据包，若失败则认为该socket失效
     *
     * @param sk SelectionKey
     * @throws IOException IOException
     */
    private static void writeDataToSocket(SelectionKey sk) throws IOException {
        SocketChannel sc = (SocketChannel) sk.channel();
        bb.clear();
        String str = "server data";
        bb.put(str.getBytes());
        while (bb.hasRemaining()) {
            sc.write(bb);
        }
    }

    /**
     * 从通道中读取数据
     *
     * @param sk SelectionKey
     * @throws IOException IOException
     */
    private static void readDataFromSocket(SelectionKey sk) throws IOException {
        SocketChannel sc = (SocketChannel) sk.channel();
        bb.clear();
        List<Byte> list = new ArrayList<>();
        while (sc.read(bb) > 0) {
            bb.flip();
            while (bb.hasRemaining()) {
                list.add(bb.get());
            }
            bb.clear();
        }
        byte[] bytes = new byte[list.size()];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = list.get(i);
        }
        String s = (new String(bytes)).trim();
        if (!s.isEmpty()) {
            if ("exit".equals(s)){
                ssc.close();
                closed = true;
            }
            System.out.println("服务器收到：" + s);
        }
    }

}