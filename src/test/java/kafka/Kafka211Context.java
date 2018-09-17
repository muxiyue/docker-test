//package kafka;
//
//import com.sun.corba.se.jdk.spi.orbutil.threadpool.ThreadPoolManager;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.clients.consumer.ConsumerRecords;
//import org.apache.kafka.clients.consumer.KafkaConsumer;
//import org.apache.log4j.Logger;
//import org.apache.velocity.texen.util.PropertiesUtil;
//
//import java.util.*;
//
//public class Kafka211Context implements CFContext, CfMonitor
//{
//    private static Logger logger = Logger.getLogger(Kafka211Context.class);
//
//    private static Kafka211Context conext = null;
//
//    private int state = ClientConstant.INT_SCUUCED;
//
//    private static Long time = System.currentTimeMillis(); // 最近的kafka消费时间
//
//    // 监控间隔时间
//    private int monitorIntervalTime;
//
//    private Long lastWarnTime;
//
//    KafkaConsumer<String, String> c = null;
//
//    public static Kafka211Context getConext()
//    {
//        if (conext != null)
//        {
//
//            return conext;
//        }
//        synchronized (Kafka211Context.class)
//        {
//            if (conext != null)
//            {
//
//                return conext;
//            }
//            conext = new Kafka211Context();
//
//        }
//
//        return conext;
//    }
//
//    public static void setConext(Kafka211Context conext)
//    {
//        Kafka211Context.conext = conext;
//    }
//
//    @SuppressWarnings("static-access")
//    @Override
//    public void init() throws CfException
//    {
//        this.monitorIntervalTime = XmlConfigUtil.getValueInt("config.mq-config.monitorIntervalTime");
//        if (monitorIntervalTime == 0)
//        {
//            this.monitorIntervalTime = 10000;
//        }
//        this.conext = this;
//
//    }
//
//    @Override
//    public void destroy() throws CfException
//    {
//        logger.info("Kafka context destroy...");
//
//    }
//
//    @Override
//    public void start() throws CfException
//    {
//        synchronized (this)
//        {
//            if (null != conext)
//            {
//                try {
//                    new Thread(this).start();
//                } catch (Exception e1) {
//                    e1.printStackTrace();
//                }
//                // 启动一个监控线程，监控kafka取数据程序
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        lastWarnTime = time;
//                        while(true){
//                            try {
//                                if (System.currentTimeMillis() - time > 1 * 60 * 1000) {
//                                    logger.error("kafak error...........................");
//                                    int durations = PropertiesUtil.getValueInt(
//                                            "kafka_error_email_notice", 10);
//                                    if (lastWarnTime == null || System.currentTimeMillis() - lastWarnTime > durations * 60 * 1000){
//                                        lastWarnTime = System.currentTimeMillis();
//                                        // send message
//                                        String receiverMail = PropertiesUtil.getValueString(
//                                                "UBI_WEEK_REPORT_RECEIVE_MAIL",
//                                                "373934266@qq.com,houmingbo@e-car.cn");
//                                        SimpleMailSender sender = new SimpleMailSender();
//                                        MailSenderInfo mailInfo = new MailSenderInfo();
//                                        mailInfo.setToAddress(receiverMail);
//                                        mailInfo.setContent("ip为"
//                                                + PropertiesUtil.getValueString(
//                                                        "kafka_error_server_ip", "未配置")
//                                                + "的服务器有" + durations
//                                                + "分钟未从kafka消费到数据，请及时处理!!!");
//                                        mailInfo.setSubject("trip服务器报警");
//                                        boolean result = sender.sendHtmlMailSSL(mailInfo);
//                                        if (result) {
//                                            logger.info("sendEmailForWeekReport send mail success!!!");
//                                        } else {
//                                            logger.info("sendEmailForWeekReport send mail fail!!!");
//                                        }
//                                    }
//                                }
//                            } catch (Exception e2) {
//                                logger.error("kafka zhou monitor error,cause by ", e2);
//                            }
//
//                            try {
//                                Thread.sleep(15000l);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }).start();
//
//            }
//
//        }
//    }
//
//    private void customering()
//    {
//        try
//        {
//            // 资源库
//            Properties properties = new Properties();
//            properties.put("bootstrap.servers", PropertiesUtil.getValueString("kafka.bootstrap.servers"));
//            // 设置不自动提交，自己手动更新offset
//            properties.put("enable.auto.commit", "false");
//            properties.put("auto.offset.reset", "earliest");
//            properties.put("session.timeout.ms", "30000");
//            properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//            properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//            properties.put("group.id", PropertiesUtil.getValueString("kafka.group.id", ""));
//            properties.put("auto.commit.interval.ms", "1000");
//            // ExecutorService executor = Executors.newFixedThreadPool(200);
//
//            if (Boolean.valueOf(PropertiesUtil.getValueString("kafka.production.environment", "false")))
//            {
//                // 执行消费
//
//                c = new KafkaConsumer<String, String>(properties);
//                c.subscribe(Arrays.asList(PropertiesUtil.getValueString("kafka.topic", "")));
//                List<ConsumerRecord<String, String>> buffer = new ArrayList<ConsumerRecord<String, String>>();
//                IMqService ser = null;
//                // 批量提交数量
//                final int minBatchSize = 1;
//                ThreadPoolManager poolManager = ThreadPoolManager.getInstance();
//                while (true)
//                {
//                    ConsumerRecords<String, String> records = c.poll(100);
//
//                    for (ConsumerRecord<String, String> record : records){
//                        time = System.currentTimeMillis();
//                        try
//                        {
//
//                            String[] value = record.value().split("@@@");
//                            MsgDatagram datagram = com.alibaba.fastjson.JSONObject.parseObject(value[1], MsgDatagram.class);
//
//                            String cmdId = datagram.getCmdId();
//
//                            ser = getMqServiceByCmdID(cmdId);
//                            if (ser == null)
//                            {
//                                throw new MessageException("40002", "CMDID对应的IMqService为空");
//                            }
//                            Head head = new Head();
//                            head.setAppID(datagram.getAppType());
//                            head.setCmdId(datagram.getCmdId());
//                            head.setSerialNo("" + datagram.getSerialNo());
//                            head.setTerminalId(datagram.getTerminalId());
//                            head.setToken(datagram.getToken());
//                            Map<String, String> map = new HashMap<String, String>();
//                            String topicName = value[0];
//                            poolManager.execute(new ConsumerThread(ser, datagram, topicName, head, map));
//                        }
//                        catch (MessageException e)
//                        {
//                            logger.error("消费者的名字为:" + ",消费的消息为：" + record.value() + e);
//                        }
//                        catch (Exception e)
//                        {
//                            logger.error("消费者的名字为:" + ",消费的消息为：" + record.value() + e);
//                        }
//
//                        buffer.add(record);
//                    }
//                    if (buffer.size() >= minBatchSize)
//                    {
//                        // 这里就是处理成功了然后自己手动提交
//                        c.commitSync();
//                        // LOG.info("提交完毕");
//                        buffer.clear();
//                    }
//                }
//            }
//        }
//        catch (Exception e)
//        {
//            c=null;
//            logger.error("kafka_Exception---------->>" + e);
//        }
//    }
//
//    @Override
//    public void monitor() throws CfException
//    {
//        if (null == c)
//        {
//            this.customering();
//        }
//    }
//
//    @Override
//    public void run()
//    {
//
//        conext.customering();
//        while (true){
//            try{
//                this.monitor();
//                Thread.sleep(monitorIntervalTime);
//            }
//            catch (Exception e)
//            {
//                logger.error("Kafka monitor error,", e);
//
//                try
//                {
//                    Thread.sleep(2000L);
//                }
//                catch (InterruptedException e1)
//                {
//
//                }
//            }
//
//        }
//
//    }
//
//    private IMqService getMqServiceByCmdID(String cmdId) throws MessageException
//    {
//        if (null == cmdId)
//        {
//            throw new MessageException("40001", "CMDID为空");
//        }
//
//        return (IMqService) SpringConext.getSpring().getBean(CmdIDUtil.getSpringBeanName(cmdId));
//    }
//
//    public int getState()
//    {
//        return state;
//    }
//
//    public int getMonitorIntervalTime()
//    {
//        return monitorIntervalTime;
//    }
//
//}