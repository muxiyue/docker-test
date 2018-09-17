package kafka;

import kafka.tools.ConsumerOffsetChecker;
import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class kafkaProducerNew extends Thread {

    private String topic;

    public kafkaProducerNew(String topic) {
        super();
        this.topic = topic;
    }

    @Override public void run() {
        Producer producer = createProducer();
        int i = 0;
        while (true) {
            i++;
            final int index = i;
            Future<RecordMetadata> record = producer.send(
                new ProducerRecord<String, MessageContent>(topic, Integer.toString(i),
                    new MessageContent("message" + i, "desc")), new Callback() {

                    @Override public void onCompletion(RecordMetadata metadata, Exception exception) {
                        if (metadata != null) {
                            System.out.println(
                                index + "  发送成功：" + "checksum: " + metadata.checksum() + " offset: " + metadata.offset()
                                    + " partition: " + metadata.partition() + " topic: " + metadata.topic());
                        }
                        if (exception != null) {
                            System.out.println(index + "异常：" + exception.getMessage());
                        }
                    }
                });
            try {
                System.out.println("send " + i + " offset " + record.get().offset() + " topic " + record.get().topic());
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            catch (ExecutionException e) {
                e.printStackTrace();
            }
            //            try {
            //                Thread.sleep(1000);
            //            }
            //            catch (InterruptedException e) {
            //                e.printStackTrace();
            //            }
        }

        //        producer.close();
    }

    private Producer createProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "127.0.0.1:19091,127.0.0.1:19092,127.0.0.1:19093");

        // 确认leader和follower都已经记录了记录。
        props.put("acks", "all");

        //        props.put("retries", 0);
        // 一次请求最多发送字节数  16KB
        props.put("batch.size", 16384);
        // 1秒批量发送一次
        props.put("linger.ms", 1);

        //        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "kafka.EncodeingKafka");

        Producer<String, String> producer = new KafkaProducer<>(props);

        return producer;
    }

    public static void main(String[] args) {

        for (int i = 0; i < 20; i++) {
            Thread thread = new kafkaProducerNew("test");// 使用kafka集群中创建好的主题 test
            thread.setName("Producer-" + i);
            thread.start();
        }

        while (true) {
            try {
                Thread.sleep(30000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            //            适用于kafka0.8.2.0
            String[] arr = new String[] { "--zookeeper=127.0.0.1:12181,127.0.0.1:12182,127.0.0.1:12183", "--group=test", "--topic=test" };
            //适用于kafka0.8.1
            //      String[] arr = new String[]{"--zkconnect=h71:2181,h72:2181,h73:2181","--group=test-consumer-group"};
            ConsumerOffsetChecker.main(arr);
        }

    }

} 