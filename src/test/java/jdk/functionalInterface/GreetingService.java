package jdk.functionalInterface;

// Java 8为函数式接口引入了一个新注解@FunctionalInterface，主要用于编译级错误检查，
// 加上该注解，当你写的接口不符合函数式接口定义的时候，编译器会报错。
@FunctionalInterface interface GreetingService {
    void sayMessage(String message, String message2);

    // 函数式接口里允许定义默认方法
    default void sayMessage() {
        // Method body
    }

    //    函数式接口里允许定义静态方法
    static void printHello() {
        System.out.println("Hello");
    }

    //    函数式接口里是可以包含Object里的public方法，这些方法对于函数式接口来说，不被当成是抽象方法（虽然它们是抽象方法）；
    // 因为任何一个函数式接口的实现，默认都继承了Object类，包含了来自java.lang.Object里对这些抽象方法的实现；
    @Override boolean equals(Object obj);
}

class Test {
    public static void main(String[] args) {
        GreetingService greetService1 = (message, message2) -> {System.out.println("Hello " + message);};
        greetService1.sayMessage("123", "345");
    }
}


//JDK中的函数式接口举例
//    java.lang.Runnable,
//
//    java.awt.event.ActionListener,
//
//    java.util.Comparator,
//
//    java.util.concurrent.Callable
//
//    java.util.function包下的接口，如Consumer、Predicate、Supplier等