package docker.test.mbean;

import javax.management.*;
import java.lang.management.ManagementFactory;

public class DynamicMBeanServer {

    public static void main(String[] args)
        throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException,
        MBeanRegistrationException, InterruptedException {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName objectName = new ObjectName("docker.test.mbean:type=DynamicManaMBean");

        IDynamicManaMBean dynamicManaMBean = new DynamicManaMBean();
        DynamicMBean dynamicMBean = new StandardMBean(dynamicManaMBean, IDynamicManaMBean.class);
        mBeanServer.registerMBean(dynamicMBean, objectName);

        System.out.println(" Dynamic MBean Server...");
        Thread.sleep(Long.MAX_VALUE);
    }

}
