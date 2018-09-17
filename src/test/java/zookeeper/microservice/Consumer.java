package zookeeper.microservice;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;
import java.util.Random;

public class Consumer {

    // 此demo使用的集群，所以有多个ip和端口
    private static String CONNECT_SERVER = "127.0.0.1:12181,127.0.0.1:12182,127.0.0.1:12183";
    private static int SESSION_TIMEOUT = 3000;
    private static int CONNECTION_TIMEOUT = 3000;
    private static ZkClient zkClient ;

    private static List<String> serviceList;

    static {
        zkClient = new ZkClient(CONNECT_SERVER, SESSION_TIMEOUT,CONNECTION_TIMEOUT);
        init();
    }


    public static void  init() {
        String serviceName = "/config" + "/orderService";

        boolean exists = zkClient.exists(serviceName);

        if (exists) {

        }
        else {
            throw new RuntimeException("service not found");
        }

        serviceList = zkClient.getChildren(serviceName);

        zkClient.subscribeChildChanges(serviceName, new IZkChildListener() {
            @Override public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                serviceList = currentChilds;
                System.out.println("services change");
            }
        });


    }

    public static void consume() {
        Random random = new Random();
        int index = random.nextInt(serviceList.size());

        System.out.println(serviceList.get(index));
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            consume();
            Thread.sleep(2000);
        }
    }

}
