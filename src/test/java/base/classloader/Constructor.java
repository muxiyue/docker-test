package base.classloader;

// new子类的对象时，先调用父类staic{}里的东西，在调用子类里的static{}，在调用父类{}的在调用父类构造方法，在调用子类构造方法
// 调用子类或者父类的静态方法时，先调用父类的static{}在调用子类的static{}
// 其实再累内部{}只是代表在调用构造函数之前在{}中初始化，static{}只在类实例化时调用
// 执行顺序：静态代码块 高于 构造代码块 高于 构造函数
public class Constructor {
    public static int count = 0;
    public static int count2 = 0;

    // 每次实例化都会执行
    {
        System.out.println("构造代码块");
        count++;
    }

    // 只会执行一次。访问静态变量会触发执行
    static {
        System.out.println("静态代码块");
        count2++;
    }

    public Constructor() {
        System.out.println("空构造函数");
    }

    public Constructor(int i) {

        this();
        System.out.println("构造函数 int" );
    }

    public Constructor(String string) {

        System.out.println("构造函数 string");
    }

    public static void main(String[] args) {
        System.out.println(Constructor.count);
        new Constructor();
        new Constructor(1);
        new Constructor("1");
        System.out.println(Constructor.count);
        System.out.println(Constructor.count2);
    }
}

