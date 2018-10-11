package future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureTest {
    public static void main(String[] args) {

//        CompletableFuture<String> completableFuture = new CompletableFuture<>();
//
//        new Thread(() -> {
////            CompletableFuture提供了completeException方法可以让我们返回我们异步线程中的异常
//            completableFuture.completeExceptionally(new RuntimeException("error"));
//            completableFuture.complete(Thread.currentThread().getName());
//        }).start();



        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(()->
        {
            throw new RuntimeException("error happen");
//            return Thread.currentThread().getName();
        });

        // doSomethingelse(); //做你想做的其他操作
        try {
            System.out.println(completableFuture.get());
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
