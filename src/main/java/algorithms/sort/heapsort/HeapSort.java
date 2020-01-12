package algorithms.sort.heapsort;

import algorithms.SortHelper;

/**
 * 堆排序
 */
public class HeapSort {

    // 我们的算法类不允许产生任何实例
    private HeapSort() {
    }

    public static void sort(int[] arr) {

        int n = arr.length;

        // 注意，此时我们的堆是从0开始索引的
        // 从(最后一个元素的索引-1)/2开始向前
        // 最后一个元素的索引 = n-1
        // heapify，形成一个最大堆
        for (int i = (n - 1) / 2; i >= 0; i--)
            shiftDown(arr, n, i);

        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);
            shiftDown(arr, i, 0);
        }
    }

    // 交换堆中索引为i和j的两个元素
    private static void swap(int[] arr, int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    /**
     * 原始的shiftDown过程
     * @param arr  堆
     * @param n    堆中的元素个数
     * @param k    对堆中的第几个元素执行shiftDown操作
     */
    private static void shiftDown(int[] arr, int n, int k) {

        while (2 * k + 1 < n) {
            int j = 2 * k + 1;
            if (j + 1 < n && arr[j + 1] > arr[j])
                j += 1;

            if (arr[k] >= arr[j]) break;

            swap(arr, k, j);
            k = j;
        }
    }

    // 测试 HeapSort
    public static void main(String[] args) {
        SortHelper.sort(
                // () -> {
                //     int[] array = {5, 5};
                //     return array;
                // },
                HeapSort::sort
        );
    }

}
