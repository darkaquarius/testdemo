package algorithms.sort;

import algorithms.SortHelper;

import static algorithms.SortHelper.swap;

/**
 * @author huishen
 * @date 2019-07-30 14:31
 * <p>
 * 插入排序
 */
public class InsertionSort {

    /**
     * 插入排序
     * 每次都要交换位置，效率不高
     */
    public static void sort2(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                if (arr[j] < arr[j - 1]) {
                    swap(arr, j - 1, j);
                }
            }
        }
    }

    /**
     * 插入排序
     * 直接找到应该插入的位置，在其后的都向后移一位
     */
    @SuppressWarnings("Duplicates")
    public static void sort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int tmp = arr[i];
            int j;
            for (j = i; j > 0 && arr[j - 1] > tmp; j--) {
                arr[j] = arr[j - 1];
            }
            arr[j] = tmp;
        }
    }

    /**
     * 对一个子序列排序
     */
    @SuppressWarnings("Duplicates")
    public static void sort(int[] arr, int l, int r) {
        for (int i = l + 1; i < r + 1; i++) {
            int tmp = arr[i];
            int j;
            for (j = i; j > 0 && arr[j - 1] > tmp; j--) {
                arr[j] = arr[j - 1];
            }
            arr[j] = tmp;
        }
    }

    public static void main(String[] args) {
        SortHelper.sort(
            // () -> {
            //     int[] array = {6, 9, 2};
            //     return array;
            // },
            InsertionSort::sort
        );

        // int[] arr = {5, 1, 8, 3, 2, 7, 12};
        // InsertionSort.sort(arr, 1, 6);
    }

}
