package leetcode;

public class Solution141 {
    public boolean hasCycle(ListNode head) {
        if (null == head || null == head.next || null == head.next.next) {
            return false;
        }

        ListNode fast = head;
        ListNode slow = head;
        while (fast != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        ListNode listNode0 = new ListNode(3);
        ListNode listNode1 = new ListNode(2);
        ListNode listNode2 = new ListNode(0);
        ListNode listNode3 = new ListNode(-4);
        listNode0.next = listNode1;
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode1;

        Solution141 s = new Solution141();
        s.hasCycle(listNode0);
    }
}
