package classloader;

import com.test.test.HelloLoader;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;

public class FindClassOrder2 {


    //读取文件，返回字节数组
    private static byte[] loadClassBytes(String name) throws IOException {
        ClassLoader cl=FindClassOrder2.class.getClassLoader();
//        String cname = cl.getResources(name.replace('.', '/') + ".class").nextElement().getPath();
        FileInputStream in=null;
        try {
            in=new FileInputStream("/Users/chenshengpeng/F_Work/demo/springBoot/springboot-springSecurity4/target/test-classes/com/test/test/HelloLoader.class");
            ByteArrayOutputStream buffer=new ByteArrayOutputStream();
            int ch;
            while((ch=in.read())!=-1){
                byte b=(byte) ch;
                buffer.write(b);
            }
            in.close();
            return buffer.toByteArray();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String args[]) throws Exception {
        ClassLoader cl=FindClassOrder2.class.getClassLoader();
        byte[] bHelloLoader=loadClassBytes("com.test.test.HelloLoader");
        Method md_defineClass=ClassLoader.class.getDeclaredMethod("defineClass", byte[].class,int.class,int.class);
        md_defineClass.setAccessible(true);
        md_defineClass.invoke(cl, bHelloLoader,0,bHelloLoader.length);
        md_defineClass.setAccessible(false);

        HelloLoader loader = new HelloLoader();
        System.out.println(loader.getClass().getClassLoader());
        loader.print();
    }

}
