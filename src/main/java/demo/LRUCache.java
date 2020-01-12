package demo;

import java.util.HashMap;

public class LRUCache {

    private int cap;
    private HashMap<Integer, Node> map;
    private DoubleList cache;

    public LRUCache(int capacity) {
        // 初始化 LRU cache 的数据
        this.cap = capacity;
        map = new HashMap<>();
        cache = new DoubleList();
    }

    public int get(int key) {
        if (!map.containsKey(key))
            return -1;
        int val = map.get(key).val;
        put(key, val);
        return val;
    }

    public void put(int key, int val) {
        // 先把新节点 x 做出来
        Node x = new Node(key, val);

        if (map.containsKey(key)) {
            // 删除旧的，新的插到头部
            cache.remove(map.get(key));
        } else {
            if (cap == cache.size()) {
                // 删除链表最后一个
                Node last = cache.removeLast();
                map.remove(last.key);
            }
        }

        // 更新 map 中对应的数据
        map.put(key, x);
        cache.addFirst(x);
    }


    public static class Node {
        private int key, val;
        private Node next, prev;
        public Node(int k, int v) {
            this.key = k;
            this.val = v;
        }
    }

    public static class DoubleList {
        // 头尾虚节点
        private Node head, tail;
        // 链表元素数
        private int size;

        public DoubleList() {
            // 初始化双向链表的数据
            head = new Node(0, 0);
            tail = new Node(0, 0);
            head.next = tail;
            tail.prev = head;
            size = 0;
        }

        public void addFirst(Node x) {
            x.next = head.next;
            x.prev = head;
            head.next.prev = x;
            head.next = x;
            size++;
        }

        public void remove(Node x) {
            x.prev.next = x.next;
            x.next.prev = x.prev;
            size--;
        }

        public Node removeLast() {
            if (tail.prev == head)
                return null;
            Node last = tail.prev;
            remove(last);
            return last;
        }

        public int size() { return size; }
    }

    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache(3);
        lruCache.put(1, 100);
        lruCache.put(2, 200);
        lruCache.put(3, 300);
        lruCache.get(1);
        lruCache.put(4, 400);
    }

}



/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */