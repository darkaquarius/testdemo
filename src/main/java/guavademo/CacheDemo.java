package guavademo;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author huishen
 * @date 2019-08-12 13:59
 * <p>
 * guava Cache
 *
 * 手动模式：LocalManualCache
 * 自动模式：LocalLoadingCache
 *
 */
public class CacheDemo {

    @Test
    @SuppressWarnings("Duplicates")
    public void test1() {
        Cache<String, String> cache = CacheBuilder
            .newBuilder()
            .maximumSize(3)
            .build();
        cache.put("key1", "value1");
        cache.put("key2", "value2");
        cache.put("key3", "value3");
        cache.put("key4", "value4");
//        cache.invalidate("key4"); // 清除key4
        System.out.println("第一个值：" + cache.getIfPresent("key1"));
        System.out.println("第二个值：" + cache.getIfPresent("key2"));
        System.out.println("第三个值：" + cache.getIfPresent("key3"));
        System.out.println("第四个值：" + cache.getIfPresent("key4"));

        // 遍历
        for (Map.Entry<String, String> entry : cache.asMap().entrySet()) {
            System.out.println("遍历，key: " + entry.getKey() + ", value: " + entry.getValue());
        }

    }

    @Test
    @SuppressWarnings("Duplicates")
    public void test2() {
        Cache<String, String> cache = CacheBuilder.newBuilder()
            .maximumSize(2)   // 最多可以存储两个对象
            .expireAfterWrite(3, TimeUnit.SECONDS)   // 对象被写入后多久过期
            .expireAfterAccess(3, TimeUnit.SECONDS)  // 对象多久没有被访问后过期
            .weakKeys()       // 只保存对缓存记录key的弱引用
            .weakValues()     // 只保存对缓存记录value的弱引用
            .recordStats()    // 开启统计信息开关
            .build();
        cache.put("key1", "value1");
        cache.put("key2", "value2");
        cache.put("key3", "value3");
        System.out.println("第一个值：" + cache.getIfPresent("key1"));
        System.out.println("第二个值：" + cache.getIfPresent("key2"));
        System.out.println("第三个值：" + cache.getIfPresent("key3"));

        // 删除Cache中一个记录
        cache.invalidate("keyx");

        // 批量删除Cache中的记录, 没有传参数，默认删除所有记录
        cache.invalidateAll(Arrays.asList("key1", "key2"));
    }

    /**
     * 监听器
     * <p>
     * main: [key1:value1] is removed!
     * main: [key2:value2] is removed!
     * main: [key3:value3] is removed!
     * main: [key4:value4] is removed!
     * main: [key5:value5] is removed!
     */
    @Test
    public void test3() {
        // 监听器
        RemovalListener<String, String> listener =
            notification -> System.out.println(Thread.currentThread().getName() + ": [" + notification.getKey() + ":" + notification.getValue() + "] is removed!");
        Cache<String, String> cache = CacheBuilder.newBuilder()
            .maximumSize(3)    // 添加一个监听器，记录被删除时可以感知到这个事件
            .removalListener(listener)
            .build();
        Object value = new Object();
        cache.put("key1", "value1");
        cache.put("key2", "value2");
        cache.put("key3", "value3");
        cache.put("key4", "value4");
        cache.put("key5", "value5");
        cache.put("key6", "value6");
        cache.put("key7", "value7");
        cache.put("key8", "value8");
    }

    /**
     * 虽然是两个线程同时调用get(K, Callable)方法，而且对应的key不存在，
     * 但是只有一个get()方法中的Callable会被执行。
     * 线程安全
     */
    @Test
    public void test4() {

        Cache<String, String> cache = CacheBuilder.newBuilder()
            .maximumSize(3)
            .build();

        new Thread(() -> {
            System.out.println("thread1");
            try {
                String value = cache.get("key", () -> {
                    System.out.println(Thread.currentThread().getName() + ", load1"); //加载数据线程执行标志
                    Thread.sleep(1000); //模拟加载时间
                    return "auto load by Callable";
                });
                System.out.println("thread1 " + value);
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }, "thread1").start();

        new Thread(() -> {
            System.out.println("thread2");
            try {
                String value = cache.get("key", () -> {
                    System.out.println(Thread.currentThread().getName() + ", load2"); //加载数据线程执行标志
                    Thread.sleep(1000); //模拟加载时间
                    return "auto load by Callable";
                });
                System.out.println("thread2 " + value);
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }, "thread2").start();

        while (Thread.activeCount() > 1) {
            Thread.yield();
        }
    }

    /**
     * LoadingCache是Cache的子接口
     * 相比较于cache，当从LoadingCache中读取一个指定的key的记录时，如果该记录不存在，则LoadingCache可以自动执行加载数据到缓存的操作。
     *
     * build(CacheLoader)
     *
     */
    @Test
    public void loadingCache() throws ExecutionException {
        CacheLoader<String, String> loader = new CacheLoader<String, String>() {
            @Override
            public String load(String key) throws Exception {
                Thread.sleep(2000); //休眠1s，模拟加载数据
                System.out.println(key + " is loaded from a cacheLoader!");
                return key + "'s value";
            }
        };

        LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder()
            .maximumSize(3)
            .build(loader); //在构建时指定自动加载器

        String key1 = loadingCache.get("key1");
        String key2 = loadingCache.get("key2");
        String key3 = loadingCache.get("key3");
    }

    public static void main(String[] args) {
        int maxValue = Integer.MAX_VALUE;
        System.out.println(maxValue);
    }

}
