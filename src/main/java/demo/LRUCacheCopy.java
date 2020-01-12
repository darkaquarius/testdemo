package demo;

import java.util.HashMap;
import java.util.LinkedList;

public class LRUCacheCopy {

    private int cap;
    private HashMap<Integer, Node> map;
    private LinkedList<Node> list;

    public LRUCacheCopy(int capacity) {
        this.cap = capacity;
        this.map = new HashMap<>(capacity);
        this.list = new LinkedList<>();
    }

    public void put(int key, int val) {
        Node node = new Node(key, val);

        if (map.containsKey(key)) {
            list.remove(map.get(key));
        } else if (cap == list.size()) {
            Node removed = list.removeLast();
            map.remove(removed.key);
        }

        map.put(key, node);
        list.addFirst(node);
    }

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        int val = map.get(key).val;
        put(key, val);
        return val;
    }

    public static class Node {
        private int key, val;
        private Node next, prev;

        public Node(int k, int v) {
            this.key = k;
            this.val = v;
        }
    }


}
