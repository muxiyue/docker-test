package future;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class CompletableFutureTest {



    /**
     *
     * @Description: 非线程池执行异步
     * @auther: csp
     * @date:  2019/3/12 上午9:21
     * @return: void
     *
     */
    @Test
    public  void test1() throws Exception{
        CompletableFuture<String> completableFuture=new CompletableFuture();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //模拟执行耗时任务
                    System.out.println("task doing...");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    throw new RuntimeException("抛异常了");
                }catch (Exception e) {
                    //告诉completableFuture任务发生异常了
                    completableFuture.completeExceptionally(e);
                }
            }
        }).start();
        //获取任务结果，如果没有完成会一直阻塞等待
        String result=completableFuture.get();
        System.out.println("计算结果:"+result);
    }


    /**
     *
     * @Description:  测试单个异步任务
     *
     * @auther: csp
     * @date:  2019/3/12 上午9:20
     * @return: void
     *
     */
    @Test
    public  void test3() throws Exception {

        Executor executorService = Executors.newFixedThreadPool(5);

        //supplyAsync内部使用ForkJoinPool线程池执行任务
        CompletableFuture<String> completableFuture=CompletableFuture.supplyAsync(()->{
            //模拟执行耗时任务
            System.out.println("task doing...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //返回结果
            return "result";
        }
        , executorService   // 可以传递外部线程池
        );
        System.out.println("计算结果:"+completableFuture.get());
    }

    /**
     *
     * @Description: 多任务合并
     *
     * @auther: csp
     * @date:  2019/3/12 上午9:28
     * @return: void
     *
     */
    @Test
    public void test4() throws Exception {

        CompletableFuture<String> completableFuture1=CompletableFuture.supplyAsync(()->{
            //模拟执行耗时任务
            System.out.println("task1 doing...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //返回结果
            return "result1";
        });

        CompletableFuture<String> completableFuture2=CompletableFuture.supplyAsync(()->{
            //模拟执行耗时任务
            System.out.println("task2 doing...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //返回结果
            return "result2";
        });

        CompletableFuture<Object> anyResult=CompletableFuture.anyOf(completableFuture1,completableFuture2);

        System.out.println("第一个完成的任务结果:"+anyResult.get());

        CompletableFuture<Void> allResult=CompletableFuture.allOf(completableFuture1,completableFuture2);

        //阻塞等待所有任务执行完成
        allResult.join();
        System.out.println("所有任务执行完成");

    }



    /**
     *
     * @Description: 链式处理任务 后面任务依赖前面任务
     *
     * @auther: csp
     * @date:  2019/3/12 上午9:33
     * @return: void
     *
     */
    @Test
    public void test5() throws Exception {

        CompletableFuture<String> completableFuture1=CompletableFuture.supplyAsync(()->{
            //模拟执行耗时任务
            System.out.println("task1 doing...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //返回结果
            return "result1";
        });

        //等第一个任务完成后，将任务结果传给参数result，执行后面的任务并返回一个代表任务的completableFuture
        CompletableFuture<String> completableFuture2= completableFuture1.thenCompose(result->CompletableFuture.supplyAsync(()->{
            //模拟执行耗时任务
            System.out.println("task2 doing..., task1 result:" + result );
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //返回结果
            return "result2";
        }));

        System.out.println(completableFuture2.get());

    }


    /**
     *
     * @Description: 合并两个任务的结果
     *
     * @auther: csp
     * @date:  2019/3/12 上午9:34
     * @return: void
     *
     */
    @Test
    public  void test6() throws Exception {

        CompletableFuture<Integer> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            //模拟执行耗时任务
            System.out.println("task1 doing...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //返回结果
            return 100;
        });

        //将第一个任务与第二个任务组合一起执行，都执行完成后，将两个任务的结果合并
        CompletableFuture<Integer> completableFuture2 = completableFuture1.thenCombine(
            //第二个任务
            CompletableFuture.supplyAsync(() -> {
                //模拟执行耗时任务
                System.out.println("task2 doing...");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //返回结果
                return 2000;
            }),
            //合并函数
            (result1, result2) -> result1 + result2);

        System.out.println(completableFuture2.get());

    }


    /**
     *
     * @Description: 在每个CompletableFuture 上注册一个操作，该操作会在 CompletableFuture 完成执行后调用它
     * 即任务完成触发操作
     * 
     * @auther: csp
     * @date:  2019/3/12 上午9:37
     * @return: void
     *
     */
    @Test
    public void test7() throws Exception {

        CompletableFuture<Integer> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            //模拟执行耗时任务
            System.out.println("task1 doing...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //返回结果
            return 100;
        });

        //注册完成事件
        completableFuture1.thenAccept(result->System.out.println("task1 done,result:"+result));

        CompletableFuture<Integer> completableFuture2=
            //第二个任务
            CompletableFuture.supplyAsync(() -> {
                //模拟执行耗时任务
                System.out.println("task2 doing...");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //返回结果
                return 2000;
            });

        //注册完成事件
        completableFuture2.thenAccept(result->System.out.println("task2 done,result:"+result));

        //将第一个任务与第二个任务组合一起执行，都执行完成后，将两个任务的结果合并
        CompletableFuture<Integer> completableFuture3 = completableFuture1.thenCombine(completableFuture2,
            //合并函数
            (result1, result2) -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return result1 + result2;
            });

        System.out.println(completableFuture3.get());

    }

    /**
     *
     * @Description: 多任务执行，并且合并结果
     *
     * @auther: csp
     * @date:  2019/3/12 上午9:39
     * @return: void
     *
     */
    @Test public void test() {

        List<List<String>> idsList = new ArrayList<List<String>>() {
            {
                add(new ArrayList<String>() {
                    {
                        add("role_1_list_1");
                        add("role_2_list_1");
                        add("role_3_list_1");
                    }
                });

                add(new ArrayList<String>() {
                    {
                        add("role_1_list_2");
                        add("role_2_list_2");
                        add("role_3_list_2");
                    }
                });

                add(new ArrayList<String>() {
                    {
                        add("role_1_list_3");
                        add("role_2_list_3");
                        add("role_3_list_3");
                    }
                });

            }
        };

//        List<CompletableFuture<String>> completableFutures= Stream.iterate(1, i->i+1)
//            .map(i-> CompletableFuture.supplyAsync(()-> {return i.toString();}))
//            .collect(Collectors.toList());
//        List<String> strings=completableFutures.stream()
//            .map(CompletableFuture::join)
//            .collect(Collectors.toList());

        List<CompletableFuture<List<String>>> cfs = idsList.stream()
            .map(o -> CompletableFuture.supplyAsync(
                () -> {
                    try {
                        Thread.sleep(10);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("deal for " + o);
                    List<String> o1 = o;
                    return o1;
                })).collect(Collectors.toList());


        System.out.println("continue.....");

        List<List<String>> strings2=cfs.stream()
            //阻塞等待所有任务执行完成
            .map(CompletableFuture::join)
            .collect(Collectors.toList());

        System.out.println("result:" + strings2);

        System.out.println("all is done ");
    }





}
