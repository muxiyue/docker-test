package proxy.spring;

public class MyTarget {
    public void printName() {
        System.err.println("name:Target-");
        inner();
        inner2();
    }

    private void inner() {
        System.err.println("内部打印处理。。。。");
        try {
            Thread.sleep(10);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void inner2() {
        System.err.println("内部打印处理2。。。。");
        try {
            Thread.sleep(10);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}