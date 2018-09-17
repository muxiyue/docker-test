package docker.test.mbean;

// 名称必须是HelloMBean 前缀
public class Hello implements HelloMBean{

    int value = 99;

    @Override public String greeting() {
        return "welcome";
    }

    @Override public void setValue(int count) {
        this.value = count;
    }

    @Override public int getValue() {
        return value;
    }
}
