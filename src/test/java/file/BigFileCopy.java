package file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 在java中，FileChannel类中有一些优化方法可以提高传输的效率，
 * 其中transferTo( )和 transferFrom( )方法允许将一个通道交叉连接到另一个通道，
 * 而不需要通过一个缓冲区来传递数据。只有FileChannel类有这两个方法，
 * 因此 channel-to-channel 传输中通道之一必须是 FileChannel。
 * 不能在sock通道之间传输数据，不过socket 通道实现WritableByteChannel 和 ReadableByteChannel 接口,
 * 因此文件的内容可以用 transferTo( )方法传输给一个 socket 通道,
 * 或者也可以用 transferFrom( )方法将数据从一个 socket 通道直接读取到一个文件中。
 * Channel-to-channel 传输是可以极其快速的,特别是在底层操作系统提供本地支持的时候。
 * 某些操作系统可以不必通过用户空间传递数据而进行直接的数据传输。对于大量的数据传输,这会是一个巨大的帮助。
 * 注意：如果要拷贝的文件大于4G，则不能直接用Channel-to-channel 的方法，替代的方法是使用ByteBuffer，
 * 先从原文件通道读取到ByteBuffer，再将ByteBuffer写到目标文件通道中。
 */
public class BigFileCopy {

    /**
     * 通过channel到channel直接传输  值覆盖当前下标覆盖的所有数据
     *
     * @param source
     * @param dest
     * @throws IOException
     */
    public static void copyByChannelToChannel(String source, String dest, long newPosition) throws IOException {
        File source_tmp_file = new File(source);
        if (!source_tmp_file.exists()) {
            return;
        }
        RandomAccessFile source_file = new RandomAccessFile(source_tmp_file, "r");
        FileChannel source_channel = source_file.getChannel();
        File dest_tmp_file = new File(dest);
        if (!dest_tmp_file.isFile()) {
            if (!dest_tmp_file.createNewFile()) {
                source_channel.close();
                source_file.close();
                return;
            }
        }
        RandomAccessFile dest_file = new RandomAccessFile(dest_tmp_file, "rw");
        FileChannel dest_channel = dest_file.getChannel();
        long left_size = source_channel.size();
        long position = 0;

        // 写入哪个位置 是 指定的。
//        dest_channel.position(newPosition);

        dest_channel.position(dest_channel.size());

        while (left_size > 0) {
            long write_size = source_channel.transferTo(position, left_size, dest_channel);
            position += write_size;
            left_size -= write_size;
        }
        source_channel.close();
        source_file.close();
        dest_channel.close();
        dest_file.close();
    }

    public static void main(String[] args) throws InterruptedException, IOException {


        BigFileCopy.copyByChannelToChannel(
            "/Users/chenshengpeng/Downloads/test/xaa",
            "/Users/chenshengpeng/Downloads/test/2.mp4",
            0);

        BigFileCopy.copyByChannelToChannel(
            "/Users/chenshengpeng/Downloads/test/xab",
            "/Users/chenshengpeng/Downloads/test/2.mp4",
            0);

        BigFileCopy.copyByChannelToChannel(
            "/Users/chenshengpeng/Downloads/test/xac",
            "/Users/chenshengpeng/Downloads/test/2.mp4",
            0);

        BigFileCopy.copyByChannelToChannel(
            "/Users/chenshengpeng/Downloads/test/xad",
            "/Users/chenshengpeng/Downloads/test/2.mp4",
            0);

        BigFileCopy.copyByChannelToChannel(
            "/Users/chenshengpeng/Downloads/test/xae",
            "/Users/chenshengpeng/Downloads/test/2.mp4",
            0);

        BigFileCopy.copyByChannelToChannel(
            "/Users/chenshengpeng/Downloads/test/xaf",
            "/Users/chenshengpeng/Downloads/test/2.mp4",
            0);



//        CountDownLatch  latch = new CountDownLatch(100);
//        for (int i = 0; i < 100; i ++) {
//            final int index = i;
//            CountDownLatch finalLatch = latch;
//            new Thread() {
//                @Override public void run() {
//                    long start_time = System.currentTimeMillis();
//                    System.out.println(index + " begin write time = " + (start_time));
//
//                    StringBuilder sb = new StringBuilder();
//                    for (int j = 0; j < 1024/((index+"").length()); j++) {
//                        sb.append(index);
//                    }
//
//                    FileUtil.writeFileAppend("/Users/chenshengpeng/Downloads/test/" + index + ".txt", sb.substring(0, 1024));
//
//                    long end_time = System.currentTimeMillis();
//                    System.out.println(index + " end write time = " + end_time + " cost " + (end_time - start_time));
//
//                    finalLatch.countDown();
//                }
//            }.start();
//        }
//
//        latch.await();

        System.out.println("========================");

//        latch = new CountDownLatch(100);
//        for (int i = 0; i < 100; i ++) {
//
//            if (i == 50) {
//                latch.countDown();
//                continue;
//            }
//
//            final int index = i;
//            CountDownLatch finalLatch1 = latch;
//            new Thread() {
//                @Override public void run() {
//                    long start_time = System.currentTimeMillis();
//                    System.out.println(index + " begin copy time = " + (start_time));
//                    try {
//                        BigFileCopy.copyByChannelToChannel(
//                            "/Users/chenshengpeng/Downloads/test/" + index + ".txt",
//                            "/Users/chenshengpeng/Downloads/test/merger.txt",
//                            index*1024);
//
//                    long end_time = System.currentTimeMillis();
//                    System.out.println(index + " end copy time = " + end_time + " cost " + (end_time - start_time));
//                    }
//                    catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                    finalLatch1.countDown();
//                }
//            }.start();
//        }
//
//
//
//        latch.await();

//        System.out.println("first ========================" + new File("/Users/chenshengpeng/Downloads/test/merger.txt").length());
//
//        BigFileCopy.copyByChannelToChannel(
//            "/Users/chenshengpeng/Downloads/test/" + 50 + ".txt",
//            "/Users/chenshengpeng/Downloads/test/merger.txt",
//            50*1024);
//
//        System.out.println("sencod ========================" + new File("/Users/chenshengpeng/Downloads/test/merger.txt").length());
//
//
//        // 该自己用户的权限
//        File file = new File("/Users/chenshengpeng/Downloads/test/merger.txt");
//        file.setExecutable(true);
//        file.setReadable(true);
//        file.setWritable(true);
//
//        // 改所有
//        Runtime.getRuntime().exec("chmod 777 -R " + "/Users/chenshengpeng/Downloads/test/merger.txt");


//
//        BigFileCopy.copyByChannelToChannel(
//            "/Users/chenshengpeng/Downloads/test/" + 50 + ".txt",
//            "/Users/chenshengpeng/Downloads/test/merger-test.txt",
//            50*1024);
//
//        latch = new CountDownLatch(100);
//        for (int i = 0; i < 100; i ++) {
//
////            if (i == 50) {
////                latch.countDown();
////                continue;
////            }
//
//
//            final int index = i;
//            CountDownLatch finalLatch1 = latch;
//            new Thread() {
//                @Override public void run() {
//                    long start_time = System.currentTimeMillis();
//                    System.out.println(index + "   222 begin copy time = " + (start_time));
//                    try {
//                        BigFileCopy.copyByChannelToChannel2(
//                            "/Users/chenshengpeng/Downloads/test/" + index + ".txt",
//                            "/Users/chenshengpeng/Downloads/test/merger-2.txt",
//                            index*1024);
//
//                        long end_time = System.currentTimeMillis();
//                        System.out.println(index + "  222 end copy time = " + end_time + " cost " + (end_time - start_time));
//                    }
//                    catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                    finalLatch1.countDown();
//                }
//            }.start();
//        }
//
//
//        latch.await();
//
//        System.out.println("first 222========================" + new File("/Users/chenshengpeng/Downloads/test/merger-2.txt").length());

//        BigFileCopy.copyByChannelToChannel2(
//            "/Users/chenshengpeng/Downloads/test/" + 50 + ".txt",
//            "/Users/chenshengpeng/Downloads/test/merger-2.txt",
//            50*1024);
//
//        System.out.println("sencond 222========================" + new File("/Users/chenshengpeng/Downloads/test/merger-2.txt").length());


    }


    /**
     * 通过channel到channel直接传输 只有最后的执行效果
     *
     * @param source
     * @param dest
     * @throws IOException
     */
    public static void copyByChannelToChannel2(String source, String dest, long newPosition) throws IOException {
        File source_tmp_file = new File(source);
        if (!source_tmp_file.exists()) {
            return;
        }
        FileInputStream source_file = new FileInputStream(source_tmp_file);
        FileChannel source_channel = source_file.getChannel();
        File dest_tmp_file = new File(dest);
        if (!dest_tmp_file.isFile()) {
            if (!dest_tmp_file.createNewFile()) {
                source_channel.close();
                source_file.close();
                return;
            }
        }

        // append true 会让 指定下标无效,
//        FileOutputStream dest_file = new FileOutputStream(dest_tmp_file, true);
        FileOutputStream dest_file = new FileOutputStream(dest_tmp_file);
        FileChannel dest_channel = dest_file.getChannel();
        long left_size = source_channel.size();
        long position = 0;

        // 写入哪个位置 是 指定的。 下标之后的内容 会被忽略掉
        dest_channel.position(newPosition);


        while (left_size > 0) {
            long write_size = source_channel.transferTo(position, left_size, dest_channel);
            position += write_size;
            left_size -= write_size;
        }
        source_channel.close();
        source_file.close();
        dest_channel.close();
        dest_file.close();
    }





    // 未验证
    @Deprecated
    public void test(String sourceFileName, String filename, boolean appendable) throws IOException {

        // 如果appendable为true，则表示追加到文件中写入。
        //        注意：filechannel 的方法write(ByteBuffer src,long position)，position为从文件的位置开始写入，
        // 如果fos=new FileOutputStream(file,false),跟write（）一样也是会覆盖文件，同
        // 时生成的文件position位置之前的数据会全部为0
        // filechannel写的新文件，由appendable决定是替换原文件，还是追加到原文件中。
        File file = new File(filename);
        if (!file.exists()) {

            Runtime.getRuntime().exec("sudo chmod 777 -R " + filename);
        }
        FileOutputStream fos = null;
        int rtn = 0;
        try {
            fos = new FileOutputStream(file, appendable);
            FileChannel fc = fos.getChannel();

            FileChannel inChannel =  new FileInputStream(sourceFileName).getChannel();


            int res = 0;
            ByteBuffer buffer=ByteBuffer.allocate(1024);
            while(res < 0) {
                buffer.clear();
                inChannel.read(buffer);
                fc.write(buffer);
            }

            fc.close();
            fos.flush();
            fos.close();
        }
        catch (IOException e) {
            rtn = 1;
            e.printStackTrace();
        }
        finally {
            if (fos != null) {
                try {
                    fos.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

//    public void test2() {
//        FileChannel mFileChannel = new FileOutputStream(combfile).getChannel();
//        FileChannel inFileChannel;
//        for (String infile : fileArray) {
//            File fin = new File(infile);
//            inFileChannel = new FileInputStream(fin).getChannel();
//            inFileChannel.transferTo(0, inFileChannel.size(), mFileChannel);
//            inFileChannel.close();
//        }
//        mFileChannel.close();
//    }
}