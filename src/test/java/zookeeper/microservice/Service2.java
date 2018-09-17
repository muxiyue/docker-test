package zookeeper.microservice;

import org.I0Itec.zkclient.ZkClient;

import java.io.IOException;
import java.util.List;

public class Service2 {

    // 此demo使用的集群，所以有多个ip和端口
    private static String CONNECT_SERVER = "127.0.0.1:12181,127.0.0.1:12182,127.0.0.1:12183";
    private static int SESSION_TIMEOUT = 3000;
    private static int CONNECTION_TIMEOUT = 3000;
    private static ZkClient zkClient ;

    static {
        zkClient = new ZkClient(CONNECT_SERVER, SESSION_TIMEOUT,CONNECTION_TIMEOUT);
    }

    public static void add(String ip){
        // 如果不存在节点，就新建一个节点
        if(!zkClient.exists("/config")){
            zkClient.createPersistent("/config");
        }

        String serviceName = "/config" + "/orderService";

        if(!zkClient.exists(serviceName)){
            zkClient.createPersistent(serviceName);
        }

        zkClient.createEphemeral(serviceName + "/" + ip);

        //        String value = zkClient.readData(serviceName);
        List<String> value = zkClient.getChildren(serviceName);
        System.out.println("服务发布成功===" + value);
    }

    public static void main(String[] args) throws IOException {
        add("192.168.1.21:8080");
        add("192.168.1.22:8080");
        add("192.168.1.23:8080");
        System.in.read();
    }


}
