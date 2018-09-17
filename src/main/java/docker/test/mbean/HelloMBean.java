package docker.test.mbean;

public interface HelloMBean {
    String greeting();

    void setValue(int count);

    int getValue();
}
