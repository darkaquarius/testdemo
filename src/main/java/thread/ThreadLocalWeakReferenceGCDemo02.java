package thread;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huishen
 * @date 2019-01-28 10:06
 *
 * ThreadLocal中的弱引用的回收情况
 *
 * https://blog.csdn.net/xlgen157387/article/details/78513735
 */
public class ThreadLocalWeakReferenceGCDemo02 {

    private static final int MAIN_THREAD_LOOP_SIZE = 20;

    public static void main(String[] args) throws InterruptedException {

        ThreadLocal<Map<Integer, String>> threadLocal1 = new ThreadLocal<>();
        Map<Integer, String> map1 = new HashMap<>(1);
        map1.put(1, "我是第1个ThreadLocal数据！");
        threadLocal1.set(map1);

        ThreadLocal<Map<Integer, String>> threadLocal2 = new ThreadLocal<>();
        Map<Integer, String> map2 = new HashMap<>(1);
        map2.put(2, "我是第2个ThreadLocal数据！");
        threadLocal2.set(map2);

        for (int i = 3; i <= MAIN_THREAD_LOOP_SIZE; i++) {
            ThreadLocal<Map<Integer, String>> threadLocal = new ThreadLocal<>();
            Map<Integer, String> map = new HashMap<>(1);
            map.put(i, "我是第" + i + "个ThreadLocal数据！");
            threadLocal.set(map);
            threadLocal.get();

            if (i > 20) {
                //-Xms20m -Xmx20m -Xmn10m -XX:+PrintGCDetails -XX:SurvivorRatio=8
                //会触发GC
                byte[] allocation1, allocation2, allocation3, allocation4;
                allocation1 = new byte[2 * 1024 * 1024];
                allocation2 = new byte[2 * 1024 * 1024];
                allocation3 = new byte[2 * 1024 * 1024];
                allocation4 = new byte[4 * 1024 * 1024];
            }
        }
        System.out.println("-------" + threadLocal1.get());
        System.out.println("-------" + threadLocal2.get());
    }

}
