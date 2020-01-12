package algorithms;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author huishen
 * @date 2018/9/9 下午4:00
 */

public class SortHelper {

    private SortHelper() {
        throw new RuntimeException("禁止反射调用");
    }

    /**
     * 生成有n个元素的随机数组,每个元素的随机范围为[rangeL, rangeR]
     */
    public static int[] generateRandomArray(int n, int rangeL, int rangeR) {
        assert rangeL <= rangeR;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = (int) (Math.random() * (rangeR - rangeL + 1) + rangeL);
        }
        return arr;
    }

    /**
     * 生成一个近乎有序的数组
     * 首先生成一个含有[0...n-1]的完全有序数组, 之后随机交换swapTimes对数据
     * swapTimes定义了数组的无序程度:
     * swapTimes == 0 时, 数组完全有序
     * swapTimes 越大, 数组越趋向于无序
     */
    public static int[] generateNearlyOrderedArray(int n, int swapTimes) {
        int arr[] = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i;
        }
        for (int i = 0; i < swapTimes; i++) {
            int a = (int) (Math.random() * n);
            int b = (int) (Math.random() * n);
            swap(arr, a, b);
        }

        return arr;
    }

    /**
     * 交换
     */
    public static void swap(int[] arr, int a, int b) {
        int tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }

    /**
     * 打印数组
     */
    public static void printArray(int arr[]) {
        Arrays.stream(arr).forEach(x -> System.out.print(x + "\t"));
        System.out.println();
    }

    public static void sort(Consumer<int[]> consumer) {
        sort(null, consumer);
    }

    public static void sort(Supplier<int[]> generate, Consumer<int[]> sort) {

        // 默认生成10个1(包含)到10(包含)的数
        int[] array = generate !=null ? generate.get() : SortHelper.generateRandomArray(15, 1, 100);
        SortHelper.printArray(array);
        doSort(array, sort);
        SortHelper.printArray(array);
    }

    public static void sortAndEfficiency(Consumer<int[]> consumer) {
        sortAndEfficiency(null, consumer);
    }

    public static void sortAndEfficiency(Supplier<int[]> generate, Consumer<int[]> sort) {
        final int N = 100_000;
        final int rangeL = 1;
        final int rangeR = 100_000;
        // 默认生成100_000个1(包含)到100_000(包含)的数
        int[] array = generate !=null ? generate.get() : SortHelper.generateRandomArray(N, rangeL, rangeR);

        doSort(array, sort);

        validateSort(array);
    }

    /**
     * 验证队列排序的正确性
     */
    private static void validateSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i-1] > arr[i]) {
                System.out.println("排序结果错误");
                break;
            }
        }
        // System.out.println("排序结果正确");
    }

    /**
     * 调用传入的排序算法并且计时
     */
    private static void doSort(int[] array, Consumer<int[]> sort) {
        long l = System.currentTimeMillis();
        sort.accept(array);
        System.out.println("sort end, spends: " + (System.currentTimeMillis() - l));
    }

    public static void main(String[] args) {
        int[] array1 = generateRandomArray(3, 2, 10);
        printArray(array1);
        swap(array1, 0, 1);
        printArray(array1);
        swap(array1, 1, 2);
        printArray(array1);

        int[] array2 = generateNearlyOrderedArray(10, 2);
        printArray(array2);
    }


}
