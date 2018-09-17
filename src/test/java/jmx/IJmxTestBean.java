package jmx;

public interface IJmxTestBean {
    int getAge();

    void setAge(int age);

    void setName(String name);

    String getName();

    int add(int x, int y);

    void dontExposeMe();
}
