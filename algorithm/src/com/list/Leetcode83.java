package com.list;

public class Leetcode83 {
    public static void exec(){
        ListNode A = new ListNode(1);
        ListNode A1 = new ListNode(2);
        ListNode A2 = new ListNode(2);
        ListNode A3 = new ListNode(2);
        ListNode A4 = new ListNode(4);
        A.next = A1;
        A1.next = A2;
        A2.next = A3;
        A3.next = A4;

        ListNode tempEx = delete(A);
        while(tempEx != null){
            System.out.println(tempEx.value+"");
            tempEx = tempEx.next;
        }
    }

    public static ListNode delete(ListNode A){
        ListNode head = A;
        while(A != null && A.next != null){
            if(A.value == A.next.value){
                A.next = A.next.next;
            }else{
                A = A.next;
            }
        }
        return head;
    }
}
