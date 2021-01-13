package com.doublePointer;

import com.list.ListNode;

public class Leetcode141 {
    public static void exec(){
        ListNode pos = new ListNode(100);
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = pos;
        head.next.next.next = new ListNode(3);
        head.next.next.next.next = new ListNode(4);
        head.next.next.next.next.next = pos;
        boolean result = hasCycle(head);
        System.out.println("result: "+result);
    }

    public static boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head.next;
        while(slow != fast){
            if(fast == null || fast.next == null){
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }

        return true;
    }
}
