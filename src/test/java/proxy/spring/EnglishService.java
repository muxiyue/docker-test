package proxy.spring;

public class EnglishService implements PeopleService {
    @Override
    public void sayHello() {
        System.err.println("Hi~");
        this.printName("内部调用");
    }

    @Override
    public void printName(String name) {
        System.err.println("Your name:" + name);
    }
}