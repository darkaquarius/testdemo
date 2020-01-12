package algorithms.sort;

import algorithms.SortHelper;

/**
 * @author huishen
 * @date 2019-08-13 16:54
 */
public class HeapSort {

    public static void sort(int[] arr) {

    }

    public static void main(String[] args) {
        SortHelper.sort(
            // () -> {
            //     int[] array = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
            //     return array;
            // },
            HeapSort::sort
        );
    }

}
