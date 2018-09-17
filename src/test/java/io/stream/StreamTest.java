package io.stream;

import java.io.*;

public class StreamTest {
    public static void main(String[] args) throws IOException {
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream("/Users/chenshengpeng/F_Work/demo/docker-test/src/test/java/io/NIOServer.java"));
        byte[] bs = new byte[1024];

        int flag=0;
//        while((flag=inputStream.read(bs))!=-1) {
//            System.out.println("===========");
//            System.out.println(new String(bs, 0, flag));
//        }



        System.out.println("=====================================");

//        inputStream.reset();


        InputStreamReader reader = new InputStreamReader(inputStream);

        char[] chs = new char[1024];
        while((flag=reader.read(chs))!=-1) {
            System.out.println("===========2");
            System.out.println(new String(chs, 0, flag));
        }

        //关闭的时候只需要关闭最外层的流就行了
        inputStream.close();
    }
}
