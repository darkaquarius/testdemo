package demo;

import org.junit.Test;

import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author huishen
 * @date 2019-08-08 23:16
 *
 * CopyOnWriteArrayList and CopyOnWriteArraySet
 *
 * CopyOnWriteArraySet内部有一个CopyOnWriteArrayList对象
 * CopyOnWriteArraySet是通过CopyOnWriteArrayList实现的。
 *
 */
public class CopyOnWriteDemo {

    /**
     * CopyOnWriteArraySet是有序的，但是不能有重复元素
     * CopyOnWriteArraySet#add(E) 调用的是CopyOnWriteArrayList#addIfAbsent(E)
     */
    @Test
    public void testArraySetAdd() {
        CopyOnWriteArraySet<Integer> arraySet = new CopyOnWriteArraySet<>();
        arraySet.add(1);
        arraySet.add(1);
        arraySet.add(2);
        arraySet.add(2);
        arraySet.add(3);
        arraySet.add(3);
        arraySet.add(4);
        arraySet.add(4);
        arraySet.add(5);
        arraySet.add(5);
    }

}
