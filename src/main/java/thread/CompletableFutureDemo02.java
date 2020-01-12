//package thread;
//
//import junit.framework.TestCase;
//import org.junit.Test;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.CompletionException;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//import java.util.function.IntFunction;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//import static util.Utils.randomSleep;
//import static util.Utils.sleep;
//
///**
// * @author huishen
// * @date 2018-12-12 15:11
// * <p>
// * https://www.ibm.com/developerworks/cn/java/j-cf-of-jdk8/index.html
// */
//public class CompletableFutureDemo02 {
//
//    /**
//     * join()方法会抛出unchecked异常
//     * get方法抛出checked exception
//     */
//    @Test
//    public void testJoinAndGet() throws ExecutionException, InterruptedException {
//        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
//            int i = 1 / 0;
//            return 100;
//        });
//        Integer join = future.join();
//        Integer ret = future.get();
//    }
//
//    /**
//     * 创建
//     */
//    @Test
//    public void completedFuture() {
//        CompletableFuture<String> cf = CompletableFuture.completedFuture("message");
//        System.out.println("cf.isDone(): " + cf.isDone());
//        System.out.println("cf.getNow(null): " + cf.getNow(null));
//    }
//
//    /**
//     * 运行简单的异步场景
//     * 使用 ForkJoinPool 实现异步执行，这种方式使用了 daemon 线程执行 Runnable 任务。
//     */
//    @Test
//    public void runAsync() {
//        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
//            Thread thread = Thread.currentThread();
//            System.out.println(thread.getName());
//            assertTrue(thread.isDaemon());
//            sleep();
//        });
//        cf.join();
//    }
//
//    /**
//     * 同步执行动作示例
//     */
//    @Test
//    public void thenApply() {
//        // thenApply()会阻塞
//        CompletableFuture<String> cf = CompletableFuture
//            .completedFuture("message")
//            .thenApply(s -> {
//                sleep();
//                return s.toUpperCase();
//            });
//        System.out.println("cf.getNow(null): " + cf.getNow(null));
//    }
//
//    /**
//     * 异步执行动作示例
//     */
//    @Test
//    public void thenApplyAsync1() {
//        // thenApplyAsync()不会阻塞
//        CompletableFuture<String> cf = CompletableFuture
//            .completedFuture("message")
//            .thenApplyAsync(s -> {
//                sleep();
//                return s.toUpperCase();
//            });
//
//        System.out.println("cf.getNow(null): " + cf.getNow(null));
//        System.out.println("cf.join(): " + cf.join());
//    }
//
//    /**
//     * 使用固定的线程池完成异步执行动作示例
//     */
//    @Test
//    public void thenApplyAsync2() {
//        ExecutorService executor = Executors.newFixedThreadPool(3, (runnable) -> {
//            int count = 1;
//            return new Thread(runnable, "custom-executor-" + count++);
//        });
//
//        // thenApplyAsync()不会阻塞
//        CompletableFuture<String> cf = CompletableFuture
//            .completedFuture("message")
//            .thenApplyAsync(s -> {
//                    System.out.println(Thread.currentThread().getName());
//                    sleep();
//                    return s.toUpperCase();
//                },
//                executor);
//
//        System.out.println("cf.getNow(null): " + cf.getNow(null));
//        System.out.println("cf.join(): " + cf.join());
//    }
//
//    /**
//     * 作为消费者消费计算结果示例
//     * 同步
//     */
//    @Test
//    public void thenAccept() {
//        StringBuilder result = new StringBuilder();
//        CompletableFuture
//            .completedFuture("thenAccept message")
//            .thenAccept(result::append)
//            .join();
//        System.out.println(result);
//    }
//
//    /**
//     * 异步消费示
//     */
//    @Test
//    public void thenAcceptAsync() {
//        StringBuilder result = new StringBuilder();
//        CompletableFuture<Void> cf = CompletableFuture
//            .completedFuture("thenAccept message")
//            .thenAcceptAsync(result::append);
//        cf.join();
//        System.out.println(result);
//    }
//
//    /**
//     * 计算过程中的异常示例
//     * CompletableFuture.delayedExecutor since java9
//     */
////    @Test
////    public void completeExceptionally() {
////        CompletableFuture<String> cf = CompletableFuture
////            .completedFuture("message")
////            .thenApplyAsync(String::toUpperCase, CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS));
////        CompletableFuture<String> exceptionHandler =
////            cf.handle((e, th) -> (th != null) ? "message upon cancel" : "");
////        cf.completeExceptionally(new RuntimeException("completed exceptionally"));
////        System.out.println("cf.isCompletedExceptionally(): " + cf.isCompletedExceptionally());
////        try {
////            cf.join();
////            TestCase.fail("Should have thrown an exception");
////        } catch (CompletionException ex) { // just for testing
////            System.out.println("ex.getCause().getMessage(): " + ex.getCause().getMessage());
////        }
////        System.out.println("exceptionHandler.join(): " + exceptionHandler.join());
////    }
//
//    /**
//     * cancel()方法与 completeExceptionally(new CancellationException())等价
//     * CompletableFuture.delayedExecutor jdk11 supported
//     */
//    @Test
//    public void cancel() {
//        CompletableFuture cf = CompletableFuture
//            .completedFuture("message")
//            .thenApplyAsync(String::toUpperCase, CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS));
//        CompletableFuture cf2 = cf.exceptionally(throwable -> "canceled message");
//        assertTrue("Was not canceled", cf.cancel(true));
//        assertTrue("Was not completed exceptionally", cf.isCompletedExceptionally());
//        assertEquals("canceled message", cf2.join());
//    }
//
//    /**
//     * applyToEither方法是当任意一个CompletionStage完成的时候，fn会被执行，它的返回值会当作新的CompletableFuture<U>的计算结果。
//     * CompletableFuture.delayedExecutor jdk11 supported
//     */
//    @Test
//    @SuppressWarnings("unchecked")
//    public void applyToEither() {
//        String original = "Message";
//        CompletableFuture cf =
//            CompletableFuture
//                .completedFuture(original)
//                .thenApplyAsync(String::toUpperCase, CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS))
//                .applyToEither(
//                    CompletableFuture
//                        .completedFuture(original)
//                        .thenApplyAsync(String::toUpperCase, CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS)),
//                    s -> s + " from applyToEither"
//                );
//        assertTrue(((String) cf.join()).endsWith(" from applyToEither"));
//    }
//
//    /**
//     * acceptEither方法是当任意一个CompletionStage完成的时候，action这个消费者就会被执行。这个方法返回CompletableFuture<Void>
//     * CompletableFuture.delayedExecutor jdk11 supported
//     */
//    @Test
//    public void acceptEither() {
//        String original = "Message";
//        StringBuilder result = new StringBuilder();
//        CompletableFuture<Void> cf = CompletableFuture
//            .completedFuture(original)
//            .thenApplyAsync(String::toUpperCase, CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS))
//            .acceptEither(
//                CompletableFuture
//                    .completedFuture(original)
//                    .thenApplyAsync(String::toUpperCase, CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS)),
//                s -> result.append(s).append("acceptEither")
//            );
//        cf.join();
//        assertTrue("Result was empty", result.toString().endsWith("acceptEither"));
//    }
//
//    /**
//     * 两个阶段都完成后执行
//     * runAfterBoth是当两个CompletionStage都正常完成计算的时候,执行一个Runnable，这个Runnable并不使用计算的结果
//     */
//    @Test
//    public void runAfterBoth() {
//        String original = "Message";
//        StringBuilder result = new StringBuilder();
//        CompletableFuture<Void> cf = CompletableFuture
//            .completedFuture(original)
//            .thenApply(String::toUpperCase)
//            .runAfterBoth(
//                CompletableFuture
//                    .completedFuture(original)
//                    .thenApply(String::toLowerCase),
//                () -> result.append("done")
//            );
//        assertTrue("Result was empty", result.length() > 0);
//    }
//
//    /**
//     * 两个阶段都完成后执行
//     * thenAcceptBoth是当两个CompletionStage都正常完成计算的时候,执行一个BiConsumer，会使用前面2个计算完成的结果
//     */
//    @Test
//    public void thenAcceptBoth() {
//        String original = "Message";
//        StringBuilder result = new StringBuilder();
//        CompletableFuture
//            .completedFuture(original)
//            .thenApply(String::toUpperCase)
//            .thenAcceptBoth(
//                CompletableFuture
//                    .completedFuture(original)
//                    .thenApply(String::toLowerCase),
//                (s1, s2) -> result.append(s1 + s2)
//            );
//        assertEquals("MESSAGEmessage", result.toString());
//    }
//
//    /**
//     * 整合两个异步计算的结果，同步
//     * 执行一个BiFunction
//     */
//    @Test
//    public void thenCombine1() {
//        String original = "Message";
//        CompletableFuture<String> cf = CompletableFuture
//            .completedFuture(original)
//            .thenApply(this::delayedUpperCase)
//            .thenCombine(
//                CompletableFuture
//                    .completedFuture(original)
//                    .thenApply(this::delayedLowerCase),
//                (s1, s2) -> s1 + s2
//            );
//        assertEquals("MESSAGEmessage", cf.getNow(null));
//    }
//
//    /**
//     * 整合两个异步计算的结果，异步
//     * CompletableFuture.delayedExecutor jdk11 supported
//     */
////    @Test
////    public void thenCombine2() {
////        String original = "Message";
////        CompletableFuture<String> cf = CompletableFuture.completedFuture(original)
////            .thenApplyAsync(
////                String::toUpperCase,
////                CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS)
////            )
////            .thenCombine(
////                CompletableFuture
////                    .completedFuture(original)
////                    .thenApplyAsync(String::toLowerCase, CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS)),
////                (s1, s2) -> s1 + s2
////            );
////        assertEquals("MESSAGEmessage", cf.join());
////    }
//
//    /**
//     * todo 有错误
//     */
//    @Test
//    public void thenCompose() {
//        // String original = "Message";
//        // CompletableFuture<String> cf = CompletableFuture
//        //     .completedFuture(original)
//        //     .thenApply(this::delayedUpperCase)
//        //     .thenCompose(upper -> CompletableFuture.completedFuture(original).thenApply(this::delayedLowerCase))
//        //     .thenApply(s -> upper + s);
//        // assertEquals("MESSAGEmessage", cf.join());
//    }
//
//    /**
//     * 任意一个完成后创建 CompletableFuture
//     * 同步
//     * anyOf()会返回结果
//     */
//    @Test
//    @SuppressWarnings("Duplicates")
//    public void anyof() {
//        StringBuilder result = new StringBuilder(4);
//        List<String> messages = Arrays.asList("a", "b", "c");
//        CompletableFuture<String>[] cfs = messages
//            .stream()
//            .map(msg -> CompletableFuture.completedFuture(msg).thenApply(this::randomDelayedUpperCase))
//            .toArray(size -> (CompletableFuture<String>[]) new CompletableFuture<?>[size]);
//        // .toArray((IntFunction<CompletableFuture<String>[]>) CompletableFuture[]::new);
//        CompletableFuture
//            .anyOf(cfs)
//            .whenComplete((res, th) -> {
//                if (null == th) {
//                    result.append(res);
//                }
//            });
//        System.out.println(result);
//    }
//
//    /**
//     * 当所有的 CompletableFuture 完成后创建 CompletableFuture
//     * 同步
//     * allOf()返回的是CompletableFuture<Void>
//     */
//    @Test
//    @SuppressWarnings("Duplicates")
//    public void allOfSync() {
//        System.out.println("allOfSync starts: " + Thread.currentThread().getName());
//        StringBuilder result = new StringBuilder();
//        List<String> messages = Arrays.asList("a", "b", "c");
//        CompletableFuture<String>[] cfs = messages
//            .stream()
//            .map(msg -> CompletableFuture.completedFuture(msg).thenApply(this::randomDelayedUpperCase))
//            .toArray((IntFunction<CompletableFuture<String>[]>) CompletableFuture[]::new);
//        CompletableFuture
//            .allOf(cfs)
//            .whenComplete((v, th) -> {
//                System.out.println("whenComplete: " + Thread.currentThread().getName());
//                // v is null, and th is null
//                for (CompletableFuture<String> cf : cfs) {
//                    String str = cf.getNow(null);
//                    System.out.println(str);
//                }
//            });
//        System.out.println("allOfSync ends: " + Thread.currentThread().getName());
//    }
//
//    /**
//     * 当所有的 CompletableFuture 完成后创建 CompletableFuture
//     * 异步
//     */
//    @Test
//    public void allOfAsync() {
//        System.out.println("allOfAsync starts: " + Thread.currentThread().getName());
//        StringBuilder result = new StringBuilder();
//        List<String> messages = Arrays.asList("a", "b", "c");
//        CompletableFuture<String>[] cfs = messages
//            .stream()
//            .map(msg -> CompletableFuture.completedFuture(msg).thenApplyAsync(this::randomDelayedUpperCase))
//            .toArray((IntFunction<CompletableFuture<String>[]>) CompletableFuture[]::new);
//        CompletableFuture<Void> allOfCf = CompletableFuture
//            .allOf(cfs)
//            .whenComplete((v, th) -> {
//                System.out.println("whenComplete: " + Thread.currentThread().getName());
//                result.append("done");
//            });
//        allOfCf.join();
//        System.out.println(result);
//        for (CompletableFuture<String> cf : cfs) {
//            String str = cf.getNow(null);
//            System.out.println(str);
//        }
//        System.out.println("allOfAsync ends: " + Thread.currentThread().getName());
//    }
//
//
//    private String delayedUpperCase(String str) {
//        return delayedUpperCase(str, 2000);
//    }
//
//    private String randomDelayedUpperCase(String str) {
//        System.out.println("randomDelayedUpperCase: " + Thread.currentThread().getName());
//        randomSleep();
//        return str.toUpperCase();
//    }
//
//    private String delayedUpperCase(String str, long mills) {
//        System.out.println("delayedUpperCase: " + Thread.currentThread().getName());
//        sleep(mills);
//        return str.toUpperCase();
//    }
//
//    private String delayedLowerCase(String str) {
//        return delayedLowerCase(str, 2000);
//    }
//
//    private String randomDelayedLowerCase(String str) {
//        System.out.println("randomDelayedLowerCase: " + Thread.currentThread().getName());
//        randomSleep();
//        return str.toLowerCase();
//    }
//
//    private String delayedLowerCase(String str, long mills) {
//        System.out.println("delayedLowerCase: " + Thread.currentThread().getName());
//        sleep(mills);
//        return str.toLowerCase();
//    }
//
//    /**
//     * 不会死循环
//     */
//    private void sleepEnough() {
//        while (Thread.activeCount() > 1) {
//            System.out.println(Thread.activeCount());
//            Thread.yield();
//        }
//    }
//
//}
