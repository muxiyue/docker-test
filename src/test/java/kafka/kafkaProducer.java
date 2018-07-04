package kafka;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.serializer.StringEncoder;

import java.util.Properties;
import java.util.concurrent.TimeUnit;
  
  
  
  
public class kafkaProducer extends Thread{  
  
    private String topic;  
      
    public kafkaProducer(String topic){  
        super();  
        this.topic = topic;  
    }  
      
      
    @Override  
    public void run() {  
        Producer producer = createProducer();  
        int i=0;  
        while(true){  
            producer.send(new KeyedMessage<Integer, String>(topic, "message: " + i++));  
            try {  
                TimeUnit.SECONDS.sleep(1);  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
  
    private Producer createProducer() {  
        Properties properties = new Properties();  
        properties.put("zookeeper.connect", "127.0.0.1:12181");//声明zk
        properties.put("serializer.class", StringEncoder.class.getName());  
        properties.put("metadata.broker.list", "127.0.0.1:19091,127.0.0.1:19092,127.0.0.1:19093");// 声明kafka broker
        return new Producer<Integer, String>(new ProducerConfig(properties));  
     }  
      
      
    public static void main(String[] args) {  
        new kafkaProducer("test").start();// 使用kafka集群中创建好的主题 test   
          
    }  
       
} 