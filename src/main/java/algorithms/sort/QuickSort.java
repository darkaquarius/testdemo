package algorithms.sort;

import algorithms.SortHelper;

import java.util.Random;

import static algorithms.SortHelper.swap;

/**
 * @author huishen
 * @date 2019-08-02 09:47
 * <p>
 * 快速排序
 */
public class QuickSort {

    public static void sort(int[] arr) {
        sort(arr, 0, arr.length - 1);
    }

    /**
     * 快速排序
     * <p>
     * 优化：对于小规模数组, 使用插入排序
     */
    private static void sort(int[] arr, int l, int r) {
        // if (l >= r) {
        //     return;
        // }

        /*
        ----------------------------
        优化:对于小规模数组, 使用插入排序
        ----------------------------
        */
        if (r - l <= 15) {
            InsertionSort.sort(arr, l, r);
            return;
        }

        // int pivot = partition1(arr, l, r);
        int pivot = partition2(arr, l, r);

        sort(arr, l, pivot - 1);
        sort(arr, pivot + 1, r);
    }

    /**
     * |m|    <m    |    >=m    | |        |
     *  l          j             i        r
     * <p>
     * arr[i] >= m, 直接i++
     * arr[i] < m, j++, 交换，i++
     * 遍历结束后，交换arr[l]和arr[j]
     */
    private static int partition1(int[] arr, int l, int r) {

        swap(arr, l, new Random().nextInt(r - l + 1) + l);

        int mark = arr[l];

        int j = l;
        for (int i = l + 1; i < arr.length; i++) {
            if (arr[i] < mark) {
                swap(arr, ++j, i);
            }
        }
        swap(arr, j, l);
        return j;
    }

    /**
     * 先要从后向前!!!!
     * <p>
     * 反证：如果先从前向后，因为在i和j相邻的临界情况下，swap之后，i指向较小的元素，j指向较大的元素
     * i先向后移动，i== j停止了，此时会将arr[i]与arr[l]互换，把较大的元素交换到了左半边，出错了!
     */
    private static int partition2(int[] arr, int l, int r) {

        /*
        ----------------------------------------------
        优化：
           在近乎有序的数组中，如果每次都直接取第一个元素作为mark，那么会退化为n^2的算法，因为可能一直没有比分界点小的元素，
        二叉树会退化为链表。
           可以随机找一个元素为mark，并且与第一个元素交换，下面的步骤和优化前一致
        -----------------------------------------------
         */
        swap(arr, l, new Random().nextInt(r - l + 1) + l);

        int mark = arr[l];

        int i = l, j = r;
        while (i < j) {
            while (i < j && arr[j] >= mark) {
                j--;
            }
            while (i < j && arr[i] <= mark) {
                i++;
            }
            swap(arr, i, j);
        }
        swap(arr, l, i);

        return i;
    }

    /**
     * 三路快排
     */
    private static int partition3(int[] arr, int l, int r) {
        // todo
        return -1;
    }

    public static void main(String[] args) {
        SortHelper.sort(
            // () -> {
            //     int[] array = {4, 1, 30, 6, 5, 10};
            //     return array;
            // },
            QuickSort::sort
        );

        // SortHelper.sortAndEfficiency(
        //     () -> SortHelper.generateNearlyOrderedArray(100_000, 100),
        //     QuickSort::sort
        // );
    }

}
