package demo;

import org.junit.Test;

import java.util.LinkedList;

/**
 * @author huishen
 * @date 2019-07-31 16:39
 */
public class LinkedListTest {

    @Test
    public void test0() {
        LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList.add(10);
        linkedList.add(10);
        linkedList.add(20);
        linkedList.add(30);
        // linkedList.remove(10);
        linkedList.remove((Integer)10);
    }


}
