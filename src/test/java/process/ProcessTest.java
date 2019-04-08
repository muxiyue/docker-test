package process;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Scanner;

/**
 * @Auther: csp
 * @Description:
 * @Date: Created in 2019/4/4 下午4:46
 * @Modified By:
 */
public class ProcessTest {

//    redirectErrorStream方法设置为ture的时候，会将getInputStream()，getErrorStream()两个流合并，
//    自动会清空流，无需我们自己处理。如果是false，getInputStream()，getErrorStream()两个流分开

    @Test
    public void processBuilder() throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("pwd", "ls");
        builder.directory(new File(this.getClass().getResource("/").getPath())).redirectErrorStream(true);
        Process process = builder.start();
        BufferedReader reader=null;
        String line=null;

        reader=new BufferedReader(new InputStreamReader(process.getErrorStream()));
        while((line=reader.readLine())!=null){
            System.out.println("getErrorStream========:" + line);
        }

        reader=new BufferedReader(new InputStreamReader(process.getInputStream()));
        while((line=reader.readLine())!=null){
            System.out.println("getInputStream========:" + line);
        }

        process.waitFor();
    }



    @Test
    public void test() {
        Process process = null;
        BufferedReader reader=null;
        try {
            process = Runtime.getRuntime().exec("pwd" );
            String line=null;

            ProcessKiller ffmpegKiller = new ProcessKiller(process);
            Runtime.getRuntime().addShutdownHook(ffmpegKiller);

            reader=new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while((line=reader.readLine())!=null){
                System.out.println("getErrorStream========:" + line);
            }

            reader=new BufferedReader(new InputStreamReader(process.getInputStream()));
            while((line=reader.readLine())!=null){
                System.out.println("getInputStream========:" + line);
            }

//            int result=process.waitFor();
//            System.out.println(result);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }


    @Test
    public void test1() throws Exception {

        //    process.getInputStream是用来读取控制台命令结果的
        //
        //    process.getOutputStream是用来往控制台写入参数的
        Process process = Runtime.getRuntime().exec("native2ascii");
        Scanner sc = new Scanner(System.in);
        // 交互式场景下使用
        OutputStream out = process.getOutputStream();
        out.write(("中国"+"\n").getBytes());
        out.write(("中国2"+"\n").getBytes());
        out.flush();
        out.close();

        String str =null;
        BufferedReader buffer = new BufferedReader(new InputStreamReader(process.getInputStream()));
        while((str=(buffer.readLine()))!=null) {
            System.out.println(str);
        }  
    }
}
