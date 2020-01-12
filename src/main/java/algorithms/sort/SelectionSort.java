package algorithms.sort;

import algorithms.SortHelper;

import static algorithms.SortHelper.swap;

/**
 * @author huishen
 * @date 2019-07-30 11:53
 *
 * 选择排序
 */
public class SelectionSort {

    /**
     * 每次选取最小值放在数组前面
     */
    public static void sort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[min]) {
                    swap(arr, j, min);
                }
            }
            swap(arr, i, min);
        }
    }

    /**
     * 每次选取最大值放在数组后面
     */
    public static void sort2(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int max = 0;
            for (int j = 0; j < arr.length - i; j++) {
                if (arr[j] > arr[max]) {
                    max = j;
                }
            }
            swap(arr, max, arr.length - 1 - i);
        }
    }

    public static void main(String[] args) {
        SortHelper.sort(
            // () -> {
            //     int[] array = {5, 5};
            //     return array;
            // },
            SelectionSort::sort
        );
    }

}
