package serializable.JavaSerialize;

import serializable.protostuff.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author liqqc
 *
 */
public class JavaSerialize {
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        new JavaSerialize().start();
    }

    public void start() throws IOException, ClassNotFoundException {
        User u = new User();
        List<User> friends = new ArrayList<>();
        u.setUserName("张三");
        u.setPassWord("123456");
        u.setUserInfo("张三是一个很牛逼的人");
        u.setFriends(friends);

        User f1 = new User();
        f1.setUserName("李四");
        f1.setPassWord("123456");
        f1.setUserInfo("李四是一个很牛逼的人");

        User f2 = new User();
        f2.setUserName("王五");
        f2.setPassWord("123456");
        f2.setUserInfo("王五是一个很牛逼的人");

        friends.add(f1);
        friends.add(f2);

        Long t1 = System.currentTimeMillis();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream obj = new ObjectOutputStream(out);
        for(int i = 0; i<10; i++) {
            obj.writeObject(u);
        }
        System.out.println("java serialize: " +(System.currentTimeMillis() - t1) + "ms; 总大小：" + out.toByteArray().length );

        Long t2 = System.currentTimeMillis();
        ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new java.io.ByteArrayInputStream(out.toByteArray())));
        User user = (User) ois.readObject();
        System.out.println("java deserialize: " + (System.currentTimeMillis() - t2) + "ms; User: " + user);
    }


    /**
     * 反序列化
     * @param bytes
     * @return
     */
    public static Object unserializeOld(byte[] bytes) {
        ByteArrayInputStream bais = null;
        BufferedInputStream bis = null;
        ObjectInputStream ois = null;

        try {
            bais = new ByteArrayInputStream(bytes);
            bis = new BufferedInputStream(bais);
            ois = new ObjectInputStream(bis);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bais != null) {
                    bais.close();
                }
                if(bis != null){
                    bis.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 序列化
     * @param object
     * @return
     */
    public static byte[] serializeOld(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}