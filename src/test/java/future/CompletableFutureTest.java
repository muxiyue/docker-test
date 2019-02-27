//package future;
//
//import org.junit.Test;
//
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.ExecutionException;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//public class CompletableFutureTest {
//
//
//    @Test
//    public void test() {
//        CompletableFuture<List<String>> ids = ifhIds(); // <1>
//
//        CompletableFuture<List<String>> result = ids.thenComposeAsync(l -> { // <2>
//            Stream<CompletableFuture<String>> zip =
//                l.stream().map(i -> { // <3>
//                    CompletableFuture<String> nameTask = ifhName(i); // <4>
//                    CompletableFuture<Integer> statTask = ifhStat(i); // <5>
//
//                    return nameTask.thenCombineAsync(statTask, (name, stat) -> "Name " + name + " has stats " + stat); // <6>
//                });
//            List<CompletableFuture<String>> combinationList = zip.collect(Collectors.toList()); // <7>
//            CompletableFuture<String>[] combinationArray = combinationList.toArray(new CompletableFuture[combinationList.size()]);
//
//            CompletableFuture<Void> allDone = CompletableFuture.allOf(combinationArray); // <8>
//            return allDone.thenApply(v -> combinationList.stream()
//                .map(CompletableFuture::join) // <9>
//                .collect(Collectors.toList()));
//        });
//
//        List<String> results = result.join(); // <10>
//        assertThat(results).contains(
//            "Name NameJoe has stats 103",
//            "Name NameBart has stats 104",
//            "Name NameHenry has stats 105",
//            "Name NameNicole has stats 106",
//            "Name NameABSLAJNFOAJNFOANFANSF has stats 121");
//    }
//
//    public static void main(String[] args) {
//
////        CompletableFuture<String> completableFuture = new CompletableFuture<>();
////
////        new Thread(() -> {
//////            CompletableFuture提供了completeException方法可以让我们返回我们异步线程中的异常
////            completableFuture.completeExceptionally(new RuntimeException("error"));
////            completableFuture.complete(Thread.currentThread().getName());
////        }).start();
//
//
//
//        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(()->
//        {
//            throw new RuntimeException("error happen");
////            return Thread.currentThread().getName();
//        });
//
//        // doSomethingelse(); //做你想做的其他操作
//        try {
//            System.out.println(completableFuture.get());
//        }
//        catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//    }
//}
