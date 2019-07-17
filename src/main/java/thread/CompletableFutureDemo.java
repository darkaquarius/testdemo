package thread;

import lombok.Data;
import lombok.NonNull;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

/**
 * @author huishen
 * @date 18/5/5 下午2:12
 */

public class CompletableFutureDemo {

    private List<Car> cars = Arrays.asList(
        new Car("长安", 100, 0.1),
        new Car("本田", 200, 0.2),
        new Car("宝马", 300, 0.3),
        new Car("奔驰", 400, 0.4),
        new Car("福特", 500, 0.5)
    );

    private final Executor executor =
        Executors.newFixedThreadPool(Math.min(cars.size(), 100), (runnable) -> {
            Thread t = new Thread(runnable);
            // 使用守护线程-这种方式不会阻止程序的关停
            t.setDaemon(true);
            return t;
        });

    /**
     * 顺序流调用同步api
     * <p>
     * 10s
     */
    @Test
    public void test1() {
        List<String> ret = cars
            .stream()
            .map(Car::getPrice)
            .map(Quote::parse)
            .map(this::applyDiscount)
            .collect(Collectors.toList());
        System.out.println(ret);
    }

    /**
     * 并行流调用同步api
     * parallelStream启动Runtime.getRuntime().availableProcessors()个线程，这里是4个线程
     * <p>
     * 4s
     */
    @Test
    public void test2() {
        List<String> ret = cars
            .parallelStream()
            .map(Car::getPrice)
            .map(Quote::parse)
            .map(this::applyDiscount)
            .collect(Collectors.toList());
        System.out.println(ret);
    }

    /**
     * CompletableFuture
     * 异步方式调用同步api
     * <p>
     * 10s
     */
    @Test
    public void test3() {
        List<String> ret = cars
            .stream()
            .map(car -> CompletableFuture
                .supplyAsync(car::getPrice, executor)
                .thenApply(Quote::parse)
                .thenCompose(quote -> CompletableFuture.supplyAsync(() -> applyDiscount(quote), executor)))
            // CompletableFuture中的join()方法和Future中的get()方法类似，都会阻塞，唯一的不同是join()方法不会抛出checked exception
            .map(CompletableFuture::join)
            .collect(Collectors.toList());
        System.out.println(ret);
    }

    /**
     * 异步方式调用同步api
     * <p>
     * 2s
     */
    @Test
    public void test4() {
        // long start = System.currentTimeMillis();
        CompletableFuture<String>[] futures = cars
            .stream()
            .map(car -> CompletableFuture
                .supplyAsync(car::getPrice, executor)
                .thenApply(Quote::parse)
                .thenCompose(quote -> CompletableFuture.supplyAsync(() -> applyDiscount(quote), executor))) // 这里会立刻执行，不会延迟执行
            // .map(f -> f.thenAccept(s -> {
            //     System.out.println(s + " (done in) " + (System.currentTimeMillis() - start) + "ms");
            // }))
            .toArray((IntFunction<CompletableFuture<String>[]>) CompletableFuture[]::new);
        // 等待所有的CompletableFuture对象执行完毕
        // Void join = CompletableFuture.allOf(futures).join();
        // 任意一个CompletableFuture对象执行完毕就停止，不再等待
        // Object join1 = CompletableFuture.anyOf(futures).join();

        List<String> ret = Arrays
            .stream(futures)
            .map(f -> {
                try {
                    return f.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                return null;
            })
            .collect(Collectors.toList());

        System.out.println(ret);
    }

    private static void delay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 暂停2s
     */
    private String applyDiscount(Quote quote) {
        delay(2000);
        System.out.println(Thread.currentThread().getName() + ": run applyDiscount()......");
        return quote.getCarName() + " price is " + (quote.getPrice() * (1 - quote.getDiscount()));
    }

    @Data
    static class Car {
        @NonNull
        private String name;
        @NonNull
        private int price;
        @NonNull
        private double discount;

        String getPrice() {
            return String.format("%s:%s:%s", name, price, discount);
        }

        /**
         * getPrice()的异步版本
         * 手动创建CompletableFuture
         */
        public Future<Double> getPriceAsync0(String product) {
            CompletableFuture<Double> futurePirce = new CompletableFuture<>();
            // todo Thread??
            new Thread(() -> {
                try {
                    double price = calculatePrice(product);
                    // 完成Future操作并设置商品价格
                    futurePirce.complete(price);
                } catch (Exception ex) {
                    // 处理异常
                    futurePirce.completeExceptionally(ex);
                }

            }).start();
            return futurePirce;
        }

        /**
         * 使用supplyAsync()创建CompletableFuture
         * 和getPriceAsync0()完全等价
         */
        CompletableFuture<Double> getPriceAsync(String product) {
            return CompletableFuture.supplyAsync(() -> calculatePrice(product));
        }

        private double calculatePrice(String product) {
            delay(1000);
            return 100;
        }

    }

    @Data
    static class Quote {
        @NonNull
        private String carName;
        @NonNull
        private int price;
        @NonNull
        private double discount;

        static Quote parse(String s) {
            String[] split = s.split(":");
            String carName = split[0];
            int price = Integer.parseInt(split[1]);
            double discount = Double.parseDouble(split[2]);
            return new Quote(carName, price, discount);
        }
    }
}
