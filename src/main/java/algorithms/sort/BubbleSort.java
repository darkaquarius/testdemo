package algorithms.sort;

import algorithms.SortHelper;

import static algorithms.SortHelper.swap;

/**
 * @author huishen
 * @date 2019-07-30 16:53
 * <p>
 * 冒泡排序
 */
public class BubbleSort {

    public static void sort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }

    public static void main(String[] args) {
        SortHelper.sort(
            // () -> {
            //     int[] array = {5, 5};
            //     return array;
            // },
            BubbleSort::sort
        );
    }

}
