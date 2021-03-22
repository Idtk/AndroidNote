package com.top100;

import com.list.ListNode;

public class LT100_21 {

    public static void exec(){
        ListNode A = new ListNode(1);
        ListNode A1 = new ListNode(2);
        ListNode A2 = new ListNode(4);
        A.next = A1;
        A1.next = A2;

        ListNode B = new ListNode(1);
        ListNode B1 = new ListNode(3);
        ListNode B2 = new ListNode(4);
        B.next = B1;
        B1.next = B2;
    
        ListNode tempEx = mergeTwoLists(A, B);
        while(tempEx != null){
            System.out.println(tempEx.value+"");
            tempEx = tempEx.next;
        }
    }

    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(-1);
        ListNode pre = head;
        while(l1 !=null && l2 != null){
            if(l1.value < l2.value){
                head.next = l1;
                l1 = l1.next;
            }else{
                head.next = l2;
                l2 = l2.next;
            }
            head = head.next;
        }

        if(l1 != null){
            head.next = l1;
        }
        if(l2 != null){
            head.next = l2;
        }

        return pre.next;
    }
}
