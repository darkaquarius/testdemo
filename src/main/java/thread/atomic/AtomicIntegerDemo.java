package thread.atomic;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author huishen
 * @date 2018/11/29 下午2:13
 */
public class AtomicIntegerDemo {

    @Test
    public void test1() {
        AtomicInteger i = new AtomicInteger(0);
        i.incrementAndGet();
        i.incrementAndGet();
        i.incrementAndGet();
        System.out.println(i.get());
    }

}
