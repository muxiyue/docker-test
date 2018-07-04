package kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/**
 * 接收数据
 * 接收到: message: 10
 * 接收到: message: 11
 * 接收到: message: 12
 * 接收到: message: 13
 * 接收到: message: 14
 *
 * @author zm
 */
public class kafkaConsumerNew extends Thread {

    private String topic;

    private String group;

    private Long times;

    public kafkaConsumerNew(String topic, String group, Long times) {
        super();
        this.topic = topic;
        this.group = group;
        this.times = times;
    }

    @Override public void run() {
        Long beginTimes = System.currentTimeMillis();
        Consumer consumer = createConsumer(group);
        consumer.subscribe(Arrays.asList("test", "mq"));

//        List<PartitionInfo> partitionInfos = consumer.partitionsFor("test");
//        partitionInfos.forEach(k -> {
//
//            TopicPartition partition = new TopicPartition("test", k.partition());
//
//            consumer.assign(Arrays.asList(partition));
////            consumer.seekToEnd(Arrays.asList(k));
//            consumer.seekToBeginning(Arrays.asList(partition));
//        });


//        final int minBatchSize = 1;
//        List<ConsumerRecord<String, String>> buffer = new ArrayList<ConsumerRecord<String, String>>();
        while (System.currentTimeMillis() - beginTimes <= times) {

//            System.out.println(Thread.currentThread().getName() + " still going");

            ConsumerRecords<String, MessageContent> records = consumer.poll(100);

            System.out.println("get records:" + records.count());

            for (ConsumerRecord<String, MessageContent> record : records) {
                System.out.printf("消费: group " + group + "," + Thread.currentThread().getName()
                        + " topic = %s, offset = %d, key = %s, value = %s%n", record.topic(), record.offset(), record.key(),
                    record.value());
//                buffer.add(record);

                if (System.currentTimeMillis() - beginTimes > times) {
                    consumer.close();
                    break;
                }
            }

//            if (buffer.size() >= minBatchSize) {
                // 这里就是处理成功了然后自己手动提交
                consumer.commitSync();
                // LOG.info("提交完毕");
//                buffer.clear();
//            }


        }

        System.out.println(Thread.currentThread().getName() + " end");
    }

    private Consumer createConsumer(String group) {

        Properties props = new Properties();
        props.put("bootstrap.servers", "127.0.0.1:19091,127.0.0.1:19092,127.0.0.1:19093");
        props.put("group.id", group);
        // 设置不自动提交，自己手动更新offset
        props.put("enable.auto.commit", "false");
        props.put("auto.commit.interval.ms", "1000");

        props.put("auto.offset.reset", "earliest");
        props.put("session.timeout.ms", "30000");

        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "kafka.DecodeingKafka");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        return consumer;

    }

    public static void main(String[] args) {
//        Thread thread1 = new kafkaConsumerNew("test", "test", 40000L);// 使用kafka集群中创建好的主题 test
//        Thread thread2 = new kafkaConsumerNew("test", "test2", 1000000L);
//        Thread thread3 = new kafkaConsumerNew("test", "test", 1000000L);
//
//        thread1.setName("thread1-test-30000L");
//        thread2.setName("thread2-test2-1000000L");
//        thread3.setName("thread3-test-1000000L");
//        thread3.start();
//        thread1.start();
//        thread2.start();


//        for (int i = 0; i < 10; i ++) {
            Thread thread = new kafkaConsumerNew("test", "mq-group", 1000000000L);
            thread.setName("Consumer-" + 0);
            thread.start();
//        }

    }

} 