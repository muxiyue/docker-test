package com.wxg.download_threads;

import org.junit.Test;
import upload.DownloadUtilThreads;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Test01 {

    public static void main(String[] args) throws Exception {
        File sourceFile=new File("多线程写入.avi");
        File targetFile=new File("copy.avi");
        Scanner scan = new Scanner(System.in);
        System.out.println("请输入需要启动的线程数量(最多8个)");
        int copyNum=scan.nextInt();
        scan.close();
        if(copyNum>8||copyNum<=0){
            System.out.println("输入错误");
            return;
        }

        long copySize=sourceFile.length()/copyNum;//计算前copyNum-1个线程拷贝文件的分段大小
        int i;
        for(i=0;i<copyNum-1;i++){
            new DownloadUtilThreads(sourceFile, targetFile, copySize, copySize*i).start();
        }
        new DownloadUtilThreads(sourceFile, targetFile, copySize+(sourceFile.length()%copyNum), copySize*(i+1)).start();
        
    }




    @Test
    public void testSave() {
        try {
            RandomAccessFile r = new RandomAccessFile("/Users/chenshengpeng/Downloads/test.txt", "r");
            RandomAccessFile w = new RandomAccessFile("/Users/chenshengpeng/Downloads/test2.txt", "rw");

            //设置指针偏移
            r.seek(128);
            w.seek(128);

            //拷贝
            byte[] b = new byte[128];
            int len;

            while ((len = r.read(b)) != -1) {

                w.write(b, 0, len);
                System.out.println("写入" + len + "byte数据, seek 移动到 " + w.getFilePointer());

            }

            w.seek(0);

            System.out.println("文件从seek 0 到 " + w.getFilePointer() + " 有 " + w.read(b) + "byte数据" );


            r.seek(0);

            w.seek(0);

            if ((len = r.read(b)) != -1) {

                w.write(b, 0, len);

                System.out.println("写入" + len + "byte数据, seek 移动到 " + w.getFilePointer());

            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}