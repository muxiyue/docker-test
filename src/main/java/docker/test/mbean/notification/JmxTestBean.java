//package docker.test.mbean.notification;
//
//import org.springframework.jmx.export.annotation.ManagedNotification;
//import org.springframework.jmx.export.notification.NotificationPublisher;
//import org.springframework.jmx.export.notification.NotificationPublisherAware;
//
//import javax.management.Notification;
//
//@ManagedNotification(name="bean:nofication", notificationTypes = "nofication.test")
//public class JmxTestBean implements IJmxTestBean, NotificationPublisherAware {
//
//    private NotificationPublisher publisher;
//
//    // other getters and setters omitted for clarity
//
//    public int add(int x, int y) {
//        int answer = x + y;
//        this.publisher.sendNotification(new Notification("add", this, 0));
//        return answer;
//    }
//
//    @Override public long myOperation() {
//        return 0;
//    }
//
//    public void dontExposeMe() {
//        throw new RuntimeException();
//    }
//
//    public void setNotificationPublisher(NotificationPublisher notificationPublisher) {
//        this.publisher = notificationPublisher;
//    }
//
//
//    private String name;
//    private int age;
//    private boolean isSuperman;
//
//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//
//}