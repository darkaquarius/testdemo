package demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 单向链表
 * @author huishen
 * @date 2019-04-12 11:05
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyLinkedList<T> {

    private MyNode<T> head;

    private MyNode<T> tail;

    public boolean add(T t) {
        if (null == head) {
            tail = head = new MyNode<>(t, null);
            return true;
        }

        MyNode<T> myNode = new MyNode<>(t, null);
        tail.next = myNode;
        tail = tail.next;
        return true;
    }

    public T get(int index) {
        MyNode<T> myNode = head;
        if (0 == index) {
            return myNode.data;
        }
        for (int i = 1; i <= index; i++) {
            myNode = myNode.getNext();
        }
        return myNode.data;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class MyNode<T> {
        private T data;
        private MyNode<T> next;
    }

    public static void main(String[] args) {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println(list.get(0));
        System.out.println(list.get(1));
        System.out.println(list.get(2));
        System.out.println(list.get(3));
    }

}
