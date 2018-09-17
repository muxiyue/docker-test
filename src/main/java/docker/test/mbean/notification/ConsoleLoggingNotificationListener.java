//package docker.test.mbean.notification;
//
//import org.springframework.jmx.export.annotation.ManagedNotification;
//
//import javax.management.AttributeChangeNotification;
//import javax.management.Notification;
//import javax.management.NotificationFilter;
//import javax.management.NotificationListener;
//
//@ManagedNotification(name="bean:nofication", notificationTypes = "nofication.test")
//public class ConsoleLoggingNotificationListener
//        implements NotificationListener, NotificationFilter {
//
//    public void handleNotification(Notification notification, Object handback) {
//        System.out.println(notification);
//        System.out.println(handback);
//    }
//
//    public boolean isNotificationEnabled(Notification notification) {
//        return AttributeChangeNotification.class.isAssignableFrom(notification.getClass());
//    }
//
//}