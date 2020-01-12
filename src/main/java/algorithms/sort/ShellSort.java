package algorithms.sort;

import algorithms.SortHelper;

/**
 * @author huishen
 * @date 2018/9/9 下午9:25
 * <p>
 * 希尔排序，也叫递减增量排序，是插入排序的一种更高效的改进版本。希尔排序是不稳定的排序算法。
 */
public class ShellSort {

    public static void sort(int[] arr) {
        int d = arr.length / 2;
        int x, j, k = 1;
        while (d >= 1) {
            for (int i = d; i < arr.length; i++) {
                x = arr[i];
                j = i - d;
                //直接插入排序，会向前找所适合的位置
                while (j >= 0 && arr[j] > x) {
                    //交换位置
                    arr[j + d] = arr[j];
                    j = j - d;
                }
                arr[j + d] = x;
            }
            d = d / 2;
        }
    }

    public static void sort2(int[] arr) {
        int d = arr.length / 2;
        while (d > 0) {
            for (int i = d; i < arr.length; i++) {
                int tmp = arr[i];
                int j;
                for (j = i; j - d >= 0 && arr[j - d] > arr[j]; j = j - d) {
                    arr[j] = arr[j - d];
                }
                arr[j] = tmp;
            }

            d /= 2;
        }
    }


    public static void main(String[] args) {
        SortHelper.sort(
            // () -> {
            //     int[] array = {32, 24, 95, 45};
            //     return array;
            // },
            ShellSort::sort2
        );
    }

}