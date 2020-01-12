package demo;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * @author huishen
 * @date 2019-07-31 11:31
 */
public class ArraysDemo {

    /**
     * 比较 parallelSort() 和 sort() 方法
     */
    @Test
    public void test0() {
        for (int i = 10; i < 20_000_000; i *= 3) {
            testParallelSortAndSort(i);
        }
    }

    private static void testParallelSortAndSort(long length) {
        // 生成length个随机数
        Random rand = new Random();
        IntStream stream = rand.ints(length);
        int[] arr = stream.toArray();
        int[] arr1 = Arrays.copyOf(arr, arr.length);

        long t1 = System.currentTimeMillis();
        Arrays.parallelSort(arr);
        long t2 = System.currentTimeMillis();
        Arrays.sort(arr1);
        long t3 = System.currentTimeMillis();

        System.out.println("limit:" + length + "\t parallelSort: " + (t2 - t1) + "ms\tserialSort: " + (t3 - t2) + "ms");
    }

    @Test
    public void testParallelPrefix() {
        int[] array = new int[]{2, 3, 1, 0, 5};
        // [2, 3, 1, 0, 5]
        // [2, 5, 1, 0, 5]
        // [2, 5, 6, 0, 5]
        // [2, 5, 6, 6, 5]
        Arrays.parallelPrefix(array, (left, right) -> {
            System.out.println(Arrays.toString(array));
            return left + right;
        });

        System.out.println();
        // 输出 [2, 5, 6, 6, 11]
        System.out.println(Arrays.toString(array));
    }

    @Test
    public void setAll1() {
        int[] array = new int[10];
        Arrays.setAll(array, i -> i * 10);
        // 输出 [0, 10, 20, 30, 40, 50, 60, 70, 80, 90]
        System.out.println(Arrays.toString(array));
    }

    @Test
    public void setAll2() {
        String[] array = new String[10];
        Arrays.setAll(array, i -> "str" + i);
        System.out.println(Arrays.toString(array));
    }

}
