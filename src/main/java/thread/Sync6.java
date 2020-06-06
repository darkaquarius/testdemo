package thread;

import lombok.Getter;
import org.junit.Test;

import java.util.stream.IntStream;

public class Sync6 {

    @Test
    public void test1() {
        IntStream
                .rangeClosed(1, 100_000)
                .parallel()
//                .forEach(i -> new Sync6Data().wrong());
                .forEach(i -> new Sync6Data().right());
        System.out.println(Sync6Data.getCounter());
    }

}

class Sync6Data {

    @Getter
    private static Integer counter = 0;
    private static Object locker = new Object();

    public static int reset() {
        counter = 0;
        return counter;
    }

    public synchronized void wrong() {
        counter++;
    }

    public void right() {
        /*
         注意:这里不能锁住counter对象，而必须要锁住locker对象
              因为counter对象在不断装箱拆箱的过程中，对象都会发生变化，每次都会生成新的对象
         */
        synchronized (locker) {
            counter++;
        }

    }
}
