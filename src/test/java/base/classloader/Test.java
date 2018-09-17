package base.classloader;


//  1. 子类调用父类的静态变量，子类不会被初始化。只有父类被初始化。。对于静态字段，只有直接定义这个字段的类才会被初始化.
//	  2. 通过数组定义来引用类，不会触发类的初始化
//	  3. 访问类的常量，不会初始化类 final 标识

class SuperClass {
    static {
		System.out.println("superclass init");
	}
	public static int value = 123;
}

class SubClass extends SuperClass {
	static {
		System.out.println("subclass init");
	}

	public static final String HELLOWORLD = "hello world";
	public static String HELLOWORLD2 = "hello world";
}

public class Test {
	public static void main(String[] args) {
		System.out.println(SubClass.value);// 被动应用1
		SubClass[] sca = new SubClass[10];// 被动引用2
		System.out.println(SubClass.HELLOWORLD);
		System.out.println(SubClass.HELLOWORLD2);
	}
}