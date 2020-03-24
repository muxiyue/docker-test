package string;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: csp
 * @Description:
 * @Date: Created in 2018/12/28 上午10:04
 * @Modified By:
 */
public class StringTest {

    // 1.字面量的处理是 如果已resolved，则返回。否则创建一个String且加入常量池(如果已存在手动调用intern的会被覆盖)
    // 2.new String 肯定是在堆中创建新对象(如果构造器传入字面量，同1即可)
    // 调用intern会检查常量池，如果常量池已有该串，则返回，否则加入常量池
    // 3.常量池由StringTable保存，都是引用
    @Test
    public void test() {
        String a1 = "dosomething";
        String a = "dosomething";
        String b = new String("dosome") + new String("thing");
        String c = new String("dosomething");
        a.intern();
        System.out.println(a1 == a);
        System.out.println(a.intern() == b.intern());
        System.out.println(b.intern() == c.intern());
        System.out.println(a.intern() == c.intern());
        System.out.println(a == c.intern());

        List<String> list = new ArrayList<String>();
//        while (true) {
//            String a2 = "dosomething";
//            list.add(a2);
//        }
    }

    @Test
    public void lockString() throws InterruptedException {

        for (int i = 0; i < 50; i++) {
            final int ii = i;
            new Thread() {
                @Override
                public void run() {
                    System.out.println("begin thread " + ii);
                    String a = "cspqw";
                    synchronized (a.intern()) {
                        System.out.println("running thread begin " + ii);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("running thread end " + ii);
                    }
                    System.out.println("end thread " + ii);
                }
            }.start();
        }

        Thread.sleep(5000);

    }
}
