package kafka;

import org.apache.kafka.common.serialization.Serializer;
import serializable.protostuff.IoProtostuff;

import java.util.Map;

public class EncodeingKafka implements Serializer<Object> {
    @Override public void configure(Map configs, boolean isKey) {

    }

    @Override public byte[] serialize(String topic, Object data) {

        if (data == null) {
            return null;
        }
        else {
            return IoProtostuff.serialize(data);
        }
    }

    /* 
     * producer调用close()方法是调用 
     */
    @Override public void close() {
        System.out.println("EncodeingKafka is close");
    }

    public static void main(String[] args) {
        System.out.println(IoProtostuff.serialize(new MessageContent("message" + 1, "desc")).length);
        System.out.println(IoProtostuff.serialize("message" + 1 +  ":desc").length);
        System.out.println(("message" + 1 +  ":desc").getBytes().length);
    }

}