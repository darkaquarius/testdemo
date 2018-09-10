package algorithms;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author huishen
 * @date 2018/9/9 下午4:00
 */

public class SortHelper {

    private SortHelper() {
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
            swap(arr, a , b);
        }

        return arr;
    }

    public static void swap(int[] arr, int a, int b) {
        int tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }

    public static void printArray(int arr[]) {
        Arrays.stream(arr).forEach(x -> System.out.print(x+ "\t"));
        System.out.println();
    }

    public static void main(String[] args) {
        int[] array1 = generateRandomArray(3, 2, 10);
        printArray(array1);
        swap(array1, 0 ,1);
        printArray(array1);
        swap(array1, 1, 2);
        printArray(array1);

        int[] array2 = generateNearlyOrderedArray(10, 2);
        printArray(array2);
    }

    public static void test(Consumer<int[]> consumer) {
        int[] array = SortHelper.generateRandomArray(10, 1, 10);
        SortHelper.printArray(array);
        consumer.accept(array);
        // sort(array);
        SortHelper.printArray(array);
    }

}
