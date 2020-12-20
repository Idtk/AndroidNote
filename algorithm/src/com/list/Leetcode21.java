package com.list;

public class Leetcode21 {

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
    
        ListNode tempEx = asce2(A, B);
        while(tempEx != null){
            System.out.println(tempEx.value+"");
            tempEx = tempEx.next;
        }
    }

    public static ListNode asce(ListNode A, ListNode B){
        ListNode head = new ListNode(0);
        ListNode C = head;
        while(A != null && B != null){
            if(A.value <= B.value){
                C.next = A;
                A = A.next;
            }else {
                C.next = B;
                B = B.next;
            }
            C = C.next;
        }
        C.next = A == null ? B : A;
        return head.next;
    }

    public static ListNode asce2(ListNode A, ListNode B){
        if(A == null){
            return B;
        }else if(B == null){
            return A;
        }else if(A.value <= B.value){
            A.next = asce2(A.next, B);
            return A;
        }else{
            B.next = asce2(A, B.next);
            return B;
        }
    }

}
