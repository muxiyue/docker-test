package lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

public class JvmLockCompare {
    //线程数
    private static int nThreads=10;
    //二百万
    private static int endValue=2000*10*10*10;
    public static void main(String[] args) throws InterruptedException {
        System.out.println("当n="+nThreads+"时");
        testLockMethod();
        testNoLockMethod();
        testLongAdderMethod();

    }
    private static void testLockMethod() throws InterruptedException {
        //有锁方式
        LockRunnable lockMethod=new LockRunnable();
        LockRunnable.endValue=endValue;
        //初始化线程
        ExecutorService service=  Executors.newFixedThreadPool(nThreads);
        //开始计算
        long stime=System.currentTimeMillis();
        for(int i=0;i<nThreads;i++){
            service.submit(lockMethod);
        }
        service.shutdown();
        service.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
        //结束计算
        long etime=System.currentTimeMillis();
        //打印日志
        System.out.println("  lock.LockRunnable threads "+nThreads+"：stime:"+stime+"  etime:"+etime+" ctime:"+(etime-stime)+" value:"+LockRunnable.startValue);
    }
    private static void testNoLockMethod() throws InterruptedException {
        //无锁方式
        NoLockRunnable lockMethod=new NoLockRunnable();
        NoLockRunnable.endValue=endValue;
        //初始化线程
        ExecutorService service=  Executors.newFixedThreadPool(nThreads);
        //开始计算
        long stime=System.currentTimeMillis();
        for(int i=0;i<nThreads;i++){
            service.submit(lockMethod);
        }
        service.shutdown();
        service.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
        //结束计算
        long etime=System.currentTimeMillis();
        //打印日志
        System.out.println("lock.NoLockRunnable threads "+nThreads+"：stime:"+stime+"  etime:"+etime+" ctime:"+(etime-stime)+" value:"+NoLockRunnable.startValue);
    }

    private static void testLongAdderMethod() throws InterruptedException {
        //无锁方式
        LongAdderRunnable lockMethod=new LongAdderRunnable();
        LongAdderRunnable.endValue=endValue;
        //初始化线程
        ExecutorService service=  Executors.newFixedThreadPool(nThreads);
        //开始计算
        long stime=System.currentTimeMillis();
        for(int i=0;i<nThreads;i++){
            service.submit(lockMethod);
        }
        service.shutdown();
        service.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
        //结束计算
        long etime=System.currentTimeMillis();
        //打印日志
        System.out.println("lock.LongAdderRunnable threads "+nThreads+"：stime:"+stime+"  etime:"+etime+" ctime:"+(etime-stime)+" value:"+NoLockRunnable.startValue);
    }


}
class LongAdderRunnable implements Runnable{
    protected static LongAdder startValue=new LongAdder();
    protected static int endValue;

    @Override
    public void run() {
        Long value=startValue.sum();
        while(value<endValue){
            startValue.increment();
            value=startValue.sum();
        }
    }
}

class NoLockRunnable implements Runnable{
    protected static AtomicInteger startValue=new AtomicInteger();
    protected static int endValue;

    @Override
    public void run() {
        int value=startValue.get();
        while(value<endValue){
            value=startValue.incrementAndGet();
        }
    }
}

class LockRunnable implements Runnable{
    protected  static int startValue;
    protected static int endValue;
    @Override
    public void run() {
        while(startValue<endValue){
            addValue();
        }
    }
    private synchronized void addValue() {
        startValue++;
    }
}