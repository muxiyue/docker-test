package jmx;


public class JmxTestBean implements IJmxTestBean {

    private String name;
    private int age;
    private boolean isSuperman;

    @Override public int getAge() {
        return age;
    }

    @Override public void setAge(int age) {
        this.age = age;
    }

    @Override public void setName(String name) {
        this.name = name;
    }

    @Override public String getName() {
        return name;
    }

    @Override public int add(int x, int y) {
        return x + y;
    }

    @Override public void dontExposeMe() {
        throw new RuntimeException();
    }
}