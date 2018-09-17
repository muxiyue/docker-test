package weakReference;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

public class WeakReferenceTest {

    public static void main(String[] args) throws InterruptedException {
        String str = new String("JAVA对象");

        //创建一个如引用对象 指向 str对象

        WeakReference<String> wr = new WeakReference<String>(str);
        List<WeakReference> list = new ArrayList<WeakReference>();
        list.add(wr);





        // WeakReference和ReferenceQueue 是联合使用的：如果弱引用所引用的对象被垃圾回收，Java虚拟机就会把这个弱引用加入到与之关联的引用队列中
        // 清空table中无用键值对。原理如下：
        // (01) 当WeakHashMap中某个“弱引用的key”由于没有再被引用而被GC收回时，
        //   被回收的“该弱引用key”也被会被添加到"ReferenceQueue(queue)"中。
        // (02) 当我们执行expungeStaleEntries时，
        //   就遍历"ReferenceQueue(queue)"中的所有key
        //   然后就在“WeakReference的table”中删除与“ReferenceQueue(queue)中key”对应的键值对
        WeakHashMap map = new WeakHashMap();
        map.put(str, "123123");
        map.put("22222", "2222");

        WeakReference<String> wr2 = new WeakReference<String>(str);
        Vector<WeakReference> vec2 = new Vector<WeakReference>();
        vec2.add(wr2);

        //输出

        for (WeakReference o : list) {
            System.out.println("======2=======list=" + o + "   " + o.get());
        }

        for (WeakReference o : vec2) {
            System.out.println("======2=======vec2=" + o + "   " + o.get());
        }

        //        map.forEach((k, v) -> {
        //            System.out.println("==========" + k + "==========" + v);
        //        });

        str = null;
        //强制垃圾回收
        System.gc();


        //  垃圾回收是一个异步操作 这个地方，在主进程打断点有bug。
        TimeUnit.SECONDS.sleep(2);
//        System.out.println(map.size());

        System.out.println("===============================");

        for (WeakReference o : list) {
            System.out.println("======3=======list=" + o + "   " + o.get());
        }

        //  弱引用字符串空了，但是vector没空
        for (WeakReference o : vec2) {
            System.out.println("======3=======vec2=" + o + "   " + o.get());
        }

        TimeUnit.SECONDS.sleep(5);

        map.forEach((k, v) -> {
            System.out.println("==========" + k + "==========" + v);
        });
    }
}
