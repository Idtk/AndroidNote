package com.list;

public class Leetcode328 {
    public static void exec(){
        ListNode A = new ListNode(1);
        int i = 1;
        ListNode temp = A;
        while(i<7){
            temp.next = new ListNode(++i);
            temp = temp.next;
        }

        ListNode tempEx = oddEven(A);
        while(tempEx != null){
            System.out.println(tempEx.value+"");
            tempEx = tempEx.next;
        }
    }

    // public static ListNode oddEven(ListNode head){
    //     ListNode oddHead = head;
    //     ListNode oddCur = oddHead;
    //     ListNode evenHead = oddHead.next;
    //     ListNode evenCur = evenHead;
    //     while(head.next != null && head.next.next != null){
    //         head = head.next.next;
    //         oddCur.next = evenCur.next;
    //         oddCur = oddCur.next;
    //         evenCur.next = oddCur.next;
    //         evenCur = evenCur.next;
    //     }
    //     oddCur.next = evenHead;
    //     return oddHead;
    // }

    public static ListNode oddEven(ListNode head){
        if(head == null){
            return head;
        }
        ListNode oddCur = head;
        ListNode evenHead = head.next;
        ListNode evenCur = evenHead;
        while(evenCur != null && evenCur.next != null){
            oddCur.next = evenCur.next;
            oddCur = oddCur.next;
            evenCur.next = oddCur.next;
            evenCur = evenCur.next;
        }
        oddCur.next = evenHead;
        return head;
    }
}
