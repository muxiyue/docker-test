package string;

import org.junit.Test;

/**
 * @Auther: csp
 * @Description:
 * @Date: Created in 2018/12/28 上午10:04
 * @Modified By:
 */
public class StringTest {

    @Test
    public void test() {
        String a = "dosomething";
        String b = new String("dosome")  + new String("thing");
        String c = new String("dosomething");
        a.intern();
        System.out.println(a.intern() == b.intern());
        System.out.println(b.intern() == c.intern());
        System.out.println(a.intern() == c.intern());
        System.out.println(a == c.intern());
    }

    @Test
    public void lockString() throws InterruptedException {

        for (int i = 0; i < 50 ; i++) {
            final int ii = i;
            new Thread() {
                @Override public void run() {
                    System.out.println("begin thread " + ii);
                    String a = "cspqw";
                    synchronized (a.intern()) {
                        System.out.println("running thread begin " + ii);
                        try {
                            Thread.sleep(100);
                        }
                        catch (InterruptedException e) {
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
