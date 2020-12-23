package com.list;

public class Leetcode19 {
    public static void exec(){
        ListNode A = new ListNode(1);
        int i = 1;
        ListNode temp = A;
        while(i<8){
            temp.next = new ListNode(++i);
            temp = temp.next;
        }

        ListNode tempEx = delete(A,2);
        while(tempEx != null){
            System.out.println(tempEx.value+"");
            tempEx = tempEx.next;
        }
    }

    public static ListNode delete(ListNode A, int n){
        ListNode pre = A;
        ListNode cur = A;
        ListNode head = cur;
        int i = 0;
        while(i<n+1){
            pre = pre.next;
            i++;
        }

        while(pre != null){
            pre = pre.next;
            cur = cur.next;
        }
        
        cur.next = cur.next.next;
        return head;
    }
}
