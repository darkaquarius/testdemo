package algorithms.sort;

import algorithms.SortHelper;

/**
 * @author huishen
 * @date 2018/9/9 下午8:07
 * <p>
 * 二分插入排序
 * <p>
 * 对于插入排序，如果比较操作的代价比交换操作大的话，可以采用二分查找法来减少比较操作的次数，我们称为二分插入排序
 * 分类 -------------- 内部比较排序
 * 数据结构 ---------- 数组
 * 最差时间复杂度 ---- O(n^2)
 * 最优时间复杂度 ---- O(nlogn)
 * 平均时间复杂度 ---- O(n^2)
 * 所需辅助空间 ------ O(1)
 * 稳定性 ------------ 稳定
 */
public class InsertionSortDichotomy {

    private InsertionSortDichotomy() {
    }

    public static void sort(int arr[]) {
        for (int i = 1; i < arr.length; i++) {
            int e = arr[i];
            int left = 0;
            int right = i - 1;
            while (left <= right) {
                int middle = (left + right)/2;
                if (arr[middle] > e) {
                    right = middle -1;
                } else {
                    left = middle;
                }
            }
            for (int j = i-1; j >= left; j--) {
                arr[j+1] = arr[j];
            }
            arr[left] = e;
        }
    }

    public static void main(String[] args) {
        SortHelper.test(InsertionSortDichotomy::sort);
    }

}
