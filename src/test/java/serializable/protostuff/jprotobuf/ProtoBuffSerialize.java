package serializable.protostuff.jprotobuf;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import serializable.protostuff.IoProtostuff;
import serializable.protostuff.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author liqqc
 *
 */
public class ProtoBuffSerialize {

    public static void main(String[] args) throws IOException {
        new ProtoBuffSerialize().start();
    }

    public void start() throws IOException {
        Codec<User> studentClassCodec = ProtobufProxy.create(User.class, false);

        User u2 = new User();
        List<User> friends = new ArrayList<>();
        u2.setUserName("张三");
        u2.setPassWord("123456");
        u2.setUserInfo("张三是一个很牛逼的人");
        u2.setFriends(friends);

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

        studentClassCodec.decode(studentClassCodec.encode(u2));


        Long stime_jpb_encode = System.currentTimeMillis();
        byte[] bytes = null;
        for(int i = 0; i<10; i++) {
            bytes = studentClassCodec.encode(u2);
        }
        System.out.println("jprotobuf序列化耗时：" + (System.currentTimeMillis() - stime_jpb_encode) + "ms; 总大小：" + bytes.length);

        Long stime_jpb_decode = System.currentTimeMillis();
        User user = studentClassCodec.decode(bytes);
        Long etime_jpb_decode = System.currentTimeMillis();
        System.out.println("jprotobuf反序列化耗时："+ (etime_jpb_decode-stime_jpb_decode) + "ms; User: " + user);



        System.out.println(IoProtostuff.unserialize(IoProtostuff.serialize(1234.23F), Float.class));



        stime_jpb_encode = System.currentTimeMillis();
        bytes = null;
        for(int i = 0; i<10; i++) {
            bytes = IoProtostuff.serialize(u2);
        }
        System.out.println("io protostuff 序列化耗时：" + (System.currentTimeMillis() - stime_jpb_encode) + "ms; 总大小：" + bytes.length);

        stime_jpb_decode = System.currentTimeMillis();
        user = (User) IoProtostuff.unserialize(bytes);
        etime_jpb_decode = System.currentTimeMillis();
        System.out.println("io protostuff  反序列化耗时："+ (etime_jpb_decode-stime_jpb_decode) + "ms; User: " + user);



    }

}