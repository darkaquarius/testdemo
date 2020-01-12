package algorithms.sort;

/**
 * @author huishen
 * @date 2019-08-13 16:57
 * <p>
 * 最大堆
 * 用数组实现，从数组的下标0开始
 */
public class MaxHeap {

    private int[] data;
    private int size;
    private int capacity;

    public MaxHeap(int capacity) {
        this.data = new int[capacity];
        this.size = 0;
        this.capacity = capacity;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void insert(int num) {
        // todo
    }

    public int extractMax() {
        // todo
        return -1;
    }

    public int getMax() {
        assert size > 0;
        return data[0];
    }

    private void swap(int i, int j) {
        int tmp = data[i];
        data[i] = data[j];
        data[i] = tmp;
    }

    private void shiftUp(int k) {
        for (; k > 0 && (data[(k - 1) / 2] < data[k]); k /= 2) {
            swap((k - 1) / 2, k);
        }
    }

    private void shiftDown(int k) {
        while (2 * k + 1 < size) {
            int j = 2 * k + 1;
            if (j + 1 < size && data[j + 1] > data[j]) {
                j = j + 1;
            }

            if (data[k] >= data[j]) {
                break;
            }
            swap(k, j);
            k = j;
        }
    }

    public static void main(String[] args) {

    }

}
