package kafka;

import org.apache.kafka.common.serialization.Deserializer;
import serializable.protostuff.IoProtostuff;

import java.util.Map;

public class DecodeingKafka implements Deserializer<Object> {  
  
    @Override  
    public void configure(Map<String, ?> configs, boolean isKey) {  
    }  
  
    @Override  
    public Object deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }
        else {
            return IoProtostuff.unserialize(data);
        }
    }
  
    @Override  
    public void close() {  
          
    }  
}  