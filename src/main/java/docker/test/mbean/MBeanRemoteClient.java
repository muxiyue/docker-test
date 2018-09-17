package docker.test.mbean;

import javax.management.*;
import javax.management.openmbean.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class MBeanRemoteClient implements NotificationListener {

    public static void main(String[] args) {
        try {
            new MBeanRemoteClient().start();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() throws Exception {
        // 如果agent不做配置的话，默认jndi path为jmxrmi
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi://localhost/jndi/rmi://localhost:2088/myconnector");
        JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
        MBeanServerConnection server = jmxc.getMBeanServerConnection();
        ObjectName mbeanName = new ObjectName("bean:name=test");

        MBeanInfo mBeanInfo = server.getMBeanInfo(new ObjectName("docker.test.mbean:type=Person"));
        System.out.println(server.getAttribute(new ObjectName("docker.test.mbean:type=Person"), "Name"));

        ObjectName mbeanName2 = new ObjectName("docker.test.mbean:type=Person");

        // 订阅Notification
        server.addNotificationListener(mbeanName2, this, null, null);

//        server.invoke(mbeanName2, "setName", new Object[] { true }, new String[] { "修改了名字" });
        // 构建一个jmx.Name类型实例。
        CompositeType msgType = new CompositeType("jmx.Name", "Message Class Name",
            new String[] { "title", "body", "by" }, new String[] { "title", "body", "by" },
            new OpenType[] { SimpleType.STRING, SimpleType.STRING, SimpleType.STRING });
        CompositeData msgData = new CompositeDataSupport(msgType, new String[] { "title", "body", "by" },
            new Object[] { "Hello", "This is a new message.", "xpbug" });
        // 调用setDesc方法
        server.invoke(mbeanName2, "setDesc", new Object[] { msgData }, new String[] { CompositeData.class.getName() });

        // 访问修改后的Desc属性。
        msgData = (CompositeData) server.getAttribute(mbeanName2, "Desc");
        System.out.println("The Desc changes to:");
        System.out.println(msgData.get("title"));
        System.out.println(msgData.get("body"));
        System.out.println(msgData.get("by"));

        Thread.sleep(1000 * 10);
    }

    @Override public void handleNotification(Notification notification, Object handback) {
        System.out.println("*** Handling new notification ***");
        System.out.println("Message: " + notification.getMessage());
        System.out.println("Seq: " + notification.getSequenceNumber());
        System.out.println("*********************************");
    }
}