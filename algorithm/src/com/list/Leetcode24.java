package com.list;

public class Leetcode24 {

    public static void exec(){
        ListNode A = new ListNode(1);
        int i = 1;
        ListNode temp = A;
        while(i<7){
            temp.next = new ListNode(++i);
            temp = temp.next;
        }

        ListNode tempEx = switch2Node(A);
        while(tempEx != null){
            System.out.println(tempEx.value+"");
            tempEx = tempEx.next;
        }
    }
    
    public static ListNode switch2Node(ListNode head){
        ListNode node = new ListNode(-1);
        node.next = head;
        ListNode pre = node;
        while(pre.next != null && pre.next.next != null){
            ListNode l1 = pre.next;
            ListNode l2 = pre.next.next;
            ListNode next = l2.next;
            pre.next = l2;
            l2.next = l1;
            l1.next = next;
            pre = l1;
        }
        return node.next;
    }
}
