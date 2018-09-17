package docker.test.mbean;

import javax.management.*;
import java.lang.management.ManagementFactory;

public class HelloMBeanServer {

    public static void main(String[] args)
        throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException,
        MBeanRegistrationException, InterruptedException {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName objectName = new ObjectName("docker.test.mbean:type=hello");
        mBeanServer.registerMBean(new Hello(), objectName);

        System.out.println(" Hello MBean Server...");
        Thread.sleep(Long.MAX_VALUE);
    }

}
