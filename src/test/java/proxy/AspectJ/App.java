package proxy.AspectJ;

public class App {

    public void say() {
        System.out.println("App say");
        innerSay();
    }

    private void innerSay() {
        System.out.println("Inner App say");
    }

    public static void main(String[] args) {
        App app = new App();
        app.say();
    }
}