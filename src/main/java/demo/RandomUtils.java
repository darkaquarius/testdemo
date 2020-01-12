package demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author huishen
 * @date 2019-01-22 10:45
 */
public class RandomUtils {

    /**
     * 使用java.util.Random产生一个长度为expectedNum的随机数组，随机数的范围[min,max)
     */
    public static int[] gerateRandomArrayWithRange1(int min, int max, int expectedNum) {
        int[] randomArray = new int[expectedNum];
        Random random = new Random();
        for (int i = 0; i < expectedNum; i++) {
            randomArray[i] = random.nextInt(max - min) + min;
        }
        return randomArray;
    }

    /**
     * JDK 7 引入java.util.concurrent.ThreadLocalRandom,使用ThreadLocalRandom可以让产生范围内的随机数变得更加方便。
     */
    public static int[] gerateRandomArrayWithRange2(int min, int max, int expectedNum) {
        int[] randomArray = new int[expectedNum];
        for (int i = 0; i < expectedNum; i++) {
            randomArray[i] = ThreadLocalRandom.current().nextInt(min, max);
        }
        return randomArray;
    }

    /**
     * 随机产生和为S的N个随机正整数
     * <p>
     * 思路：
     * 第一步：把和为S的数值看做是一把尺子的长度，比如S=20。
     * 那么随机产生和为S的N个整数的问题就变成了在0~20之间产生N-1不同的刻度。这样的话，尺子就被不同的刻度分割成了N段。
     * 第二步：从左到右，计算出每一段的长度，每一段的长度就可以看做是随机数。N段就有了N个随机数。
     */
    public static int[] generateRandomArray(int expectedSum, int expectedNum, boolean ordered) {

        Set<Integer> set = new TreeSet<Integer>();
        /*
         * 先将最两端的刻度加入到集合中去。
         */
        set.add(0);
        set.add(expectedSum);

        Random random = new Random();
        while (set.size() < expectedNum + 1) {
            set.add(random.nextInt(expectedSum - 1) + 1);
        }

        Integer[] locations = new Integer[set.size()];
        set.toArray(locations);

        int[] result = new int[expectedNum];
        /*
         * 计算相邻刻度之间的长度，得到的数值就可以认为是随机数:
         */
        for (int i = 0; i < result.length; i++) {
            result[i] = locations[i + 1] - locations[i];
        }

        /*
         * 如果想让得到的随机数从小到大排列，则调用Arrays.sort
         */
        if (ordered) {
            Arrays.sort(result);
        }
        return result;
    }

    /**
     * 使用Collections的shuffle方法，将已有的List顺序随机打乱。
     */
    public static void shuffle(List<?> list) {
        Collections.shuffle(list);
    }

    /**
     * 直接调用UUID获取随机字符串(包含'-')
     * <p>
     * 可用作数据库表的主键
     * <p>
     * 如： 53b25837-05f1-4be2-9c81-2f8bc898f6bd
     */
    public static String randomUUID1() {
        return UUID.randomUUID().toString();
    }

    /**
     * 直接调用UUID获取随机字符串(不包含'-')
     * <p>
     * * 可用作数据库表的主键
     * <p>
     * 如： 97013848ac764487b01d0470cdde3f1a
     */
    public static String randomUUID2() {
        return randomUUID1().replaceAll("-", "");
    }

    public static void main(String[] args) {

        System.out.println("随机产生和为30的5个正整数如下：");
        int[] randomArray0 = generateRandomArray(30, 5, false);
        printArray(randomArray0);
        System.out.println();

        System.out.println("产生20个[1,100)范围的随机数:");
        int[] randomArray1 = gerateRandomArrayWithRange1(1, 100, 20);
        printArray(randomArray1);
        System.out.println();

        System.out.println("产生20个[1,100)范围的随机数:使用JDK 7  的 ThreadRandomLocal");
        int[] randomArray2 = gerateRandomArrayWithRange2(1, 100, 20);
        printArray(randomArray2);
        System.out.println();

        System.out.println("使用java.util.UUID产生唯一的标记(包括'-')");
        System.out.println(randomUUID1());
        System.out.println();

        System.out.println("使用java.util.UUID产生唯一的标记(不包括'-')");
        System.out.println(randomUUID2());
        System.out.println();

        System.out.println("使用Collections.shuffle打乱已有List的顺序:");
        List<Integer> intList = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            intList.add(i);
        }

        System.out.println("调用shuffle方法之前数组的内容是: ");
        printArray(intList);

        System.out.println("调用shuffle方法之后数组的内容是: ");
        shuffle(intList);
        printArray(intList);
    }

    /**
     * 打印整形数组中的数组内容
     */
    private static void printArray(int[] data) {
        for (int i : data) {
            System.out.print(i);
            System.out.print(" ");
        }
        System.out.println();
    }

    private static void printArray(List<? extends Object> list)
    {
        for(Object o :  list)
        {
            System.out.print(o + " ");
        }
        System.out.println();
    }
}