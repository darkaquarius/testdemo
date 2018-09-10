package algorithms.sort;

import algorithms.SortHelper;

/**
 * @author huishen
 * @date 2018/9/9 下午3:52
 * <p>
 * 鸡尾酒排序
 * <p>
 * <p>
 * 也叫定向冒泡排序，是冒泡排序的一种改进
 * 分类 -------------- 内部比较排序
 * 数据结构 ---------- 数组
 * 最差时间复杂度 ---- O(n^2)
 * 最优时间复杂度 ---- 如果序列在一开始已经大部分排序过的话,会接近O(n)
 * 平均时间复杂度 ---- O(n^2)
 * 所需辅助空间 ------ O(1)
 * 稳定性 ------------ 稳定
 */

public class CocktailSort {

    private CocktailSort() {
    }

    public static void sort(int arr[]) {
        int left = 0;
        int right = arr.length - 1;
        while (left < right) {
            for (int i = left; i < right; i++) {
                if (arr[i] > arr[i + 1]) {
                    SortHelper.swap(arr, i, i + 1);
                }
            }
            right--;
            for (int i = right; i > left; i--) {
                if (arr[i - 1] > arr[i]) {
                    SortHelper.swap(arr, i - 1, i);
                }
            }
            left++;
        }
    }

    public static void main(String[] args) {
        SortHelper.test(CocktailSort::sort);
    }


}
