package thread;

import lombok.Data;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

/**
 * @author huishen
 * @date 18/5/5 下午2:12
 */

@SuppressWarnings("all")
public class CompletableFutureDemo {

    private static Random random = new Random();

    private List<Shop> shops = Arrays.asList(
        new Shop("BestPrice"),
        new Shop("LetsSaveBig"),
        new Shop("MyFavouriteShop"),
        new Shop("BuyItAll"),
        new Shop("newShop")
    );

    private final Executor executor =
        Executors.newFixedThreadPool(Math.min(shops.size(), 100), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                // 使用守护线程-这种方式不会阻止程序的关停
                t.setDaemon(true);
                return t;
            }
        });

    /**
     * 顺序流调用同步api
     */
    public List<String> findPrices1(String product) {
        return shops
            .stream()
            .map(shop -> shop.getPrice(product))
            .map(Quote::parse)
            .map(Discount::applyDiscount)
            .collect(Collectors.toList());
    }

    /**
     * 并行流调用同步api
     */
    public List<String> findPrices2(String product) {
        return shops
            .parallelStream()
            .map(shop -> shop.getPrice(product))
            .map(Quote::parse)
            .map(Discount::applyDiscount)
            .collect(Collectors.toList());
    }

    /**
     * CompletableFuture
     * 异步方式调用同步api
     */
    public List<String> findPrices3(String product) {
        List<CompletableFuture<String>> priceFutures =
            shops
                .stream()
                // supplyAsync()方法的第二个参数，接收一个executor
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)))
                .collect(Collectors.toList());
        // // 同上
        // List<CompletableFuture<String>> priceFutures = shops
        //     .stream()
        //     .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor).thenApply(Quote::parse).thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)))
        //     .collect(Collectors.toList());


        return priceFutures
            .stream()
            // CompletableFuture中的join()方法和Future中的get()方法类似，唯一的不同是join()方法不会抛出checked exception
            .map(CompletableFuture::join)
            .collect(Collectors.toList());
    }


    public void findPrices4(String product) {
        long start = System.currentTimeMillis();
        CompletableFuture[] futures = shops
            .stream()
            .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor))
            .map(future -> future.thenApply(Quote::parse))
            .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)))
            .map(f -> f.thenAccept(s -> {
                System.out.println(s + " (done in) " + (System.currentTimeMillis() - start) + "ms");
            }))
            .toArray(size -> new CompletableFuture[size]);
        CompletableFuture.allOf(futures).join();    // 等待所有的CompletableFuture对象执行完毕
        CompletableFuture.anyOf(futures).join();    // 任意一个CompletableFuture对象执行完毕就停止，不再等待


    }

    // 10221 ms
    @Test
    public void test1() {
        long start = System.currentTimeMillis();
        System.out.println(findPrices1("myPhone"));
        System.out.println("Done in " + (System.currentTimeMillis() - start) + " ms");
    }

    // 4187 ms
    @Test
    public void test2() {
        long start = System.currentTimeMillis();
        System.out.println(findPrices2("myPhone"));
        System.out.println("Done in " + (System.currentTimeMillis() - start) + " ms");
    }

    // 2174 ms
    @Test
    public void test3() {
        long start = System.currentTimeMillis();
        System.out.println(findPrices3("myPhone"));
        System.out.println("Done in " + (System.currentTimeMillis() - start) + " ms");
    }


    @Test
    public void test4() {
        findPrices4("myPhone");
    }


    public static void main(String args[]) {
        Shop shop = new Shop("BeatShop");
        long start = System.nanoTime();
        Future<Double> futurePrice = shop.getPriceAsync("my favourite product");
        long invocationTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Invocation returned after " + invocationTime + " ms");

        doSomeThingElse();

        try {
            double price = futurePrice.get();
            System.out.println("Price is " + price);
        } catch (Exception e) {
            e.printStackTrace();
        }

        long retrivevalTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Price return after " + retrivevalTime + " ms");
    }

    // sleep 2s
    private static void doSomeThingElse() {
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // sleep 1s
    public static void randomDelay() {
        try {
            int delay = 500 + random.nextInt(2000);
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @Data
    public static class Shop {

        private String name;

        public Shop(String name) {
            this.name = name;
        }

        public String getPrice(String product) {
            double price = calculatePrice(product);
            Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
            return String.format("%s:%.2f:%s", name, price, code);
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
        public CompletableFuture<Double> getPriceAsync(String product) {
            return CompletableFuture.supplyAsync(() -> calculatePrice(product));
        }

        private double calculatePrice(String product) {
            randomDelay();
            return random.nextDouble() * product.charAt(0) + product.charAt(1);
        }

    }


    public static class Discount {

        public enum Code {
            NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);

            private final int percentage;

            Code(int percentage) {
                this.percentage = percentage;
            }
        }

        public static String applyDiscount(Quote quote) {
            return quote.getShopName() + " price is "
                + Discount.apply(quote.getPrice(), quote.getDiscountCode());
        }

        private static double apply(double price, Code code) {
            randomDelay();
            return price * (100 - code.percentage) / 100;
        }

    }

    @Data
    public static class Quote {
        private final String shopName;
        private final double price;
        private final Discount.Code discountCode;

        public Quote(String shopName, double price, Discount.Code code) {
            this.shopName = shopName;
            this.price = price;
            this.discountCode = code;
        }

        public static Quote parse(String s) {
            String[] split = s.split(":");
            String shopName = split[0];
            double price = Double.parseDouble(split[1]);
            Discount.Code discountCode = Discount.Code.valueOf(split[2]);
            return new Quote(shopName, price, discountCode);
        }

    }

}
