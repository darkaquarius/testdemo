package algorithms.sort;

import algorithms.SortHelper;

import java.util.Arrays;

/**
 * @author huishen
 * @date 2019-08-01 16:12
 * <p>
 * 归并排序
 */
public class MergeSort {

    /**
     * 归并排序
     */
    public static void sort(int[] arr) {
        doSort(arr, 0, arr.length - 1);
    }

    /**
     * 递归地切分
     */
    private static void doSort(int[] arr, int l, int r) {
        if (l >= r) {
            return;
        }

        int mid = l + ((r - l) >> 1);
        doSort(arr, l, mid);
        doSort(arr, mid + 1, r);
        if (arr[mid] > arr[mid + 1]) {
            merge(arr, l, mid, r);
        }
    }

    /**
     * 合并
     */
    private static void merge(int[] arr, int l, int mid, int r) {
        // 特别注意这里copy元素的下标和arr元素的下标是不一样的。copy下标需要arr下标减去l
        int[] copy = Arrays.copyOfRange(arr, l, r + 1);
        // 左边子数组的第一个元素
        int i = l;
        // 右边子数组的第一个元素
        int j = mid + 1;

        for (int k = l; k <= r; k++) {
            if (i > mid) {
                // 左边子数组已经处理完毕
                arr[k] = copy[j - l];
                j++;
            } else if (j > r) {
                // 右边子数组已经处理完毕
                arr[k] = copy[i - l];
                i++;
            } else if (copy[i - l] <= copy[j - l]) {
                arr[k] = copy[i - l];
                i++;
            } else {
                arr[k] = copy[j - l];
                j++;
            }
        }
    }

    /**
     * 归并排序的迭代写法
     */
    public static void sort2(int[] arr) {
        int n = arr.length;

        // Merge Sort Bottom Up 无优化版本
        for (int sz = 1; sz < n; sz *= 2) {
            for (int i = 0; i < n - sz; i += sz + sz) {
                // 对 arr[i...i+sz-1] 和 arr[i+sz...i+2*sz-1] 进行归并
                merge(arr, i, i + sz - 1, Math.min(i + sz + sz - 1, n - 1));
            }
        }
    }

    public static void main(String[] args) {
        SortHelper.sort(
            // () -> {
            //     int[] array = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
            //     return array;
            // },
            MergeSort::sort
        );
        // SortHelper.sortAndEfficiency(MergeSort::sort);
    }

}
