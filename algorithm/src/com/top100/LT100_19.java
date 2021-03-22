package com.top100;

import com.list.ListNode;

public class LT100_19 {

    public static void exec(){
        ListNode A = new ListNode(1);
        int i = 1;
        ListNode temp = A;
        while(i<5){
            temp.next = new ListNode(++i);
            temp = temp.next;
        }

        ListNode tempEx = removeNthFromEnd(A,2);
        while(tempEx != null){
            System.out.println(tempEx.value+"");
            tempEx = tempEx.next;
        }
    }


    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode tag = new ListNode(0);
        tag.next = head;
        ListNode pre = tag;
        ListNode cur = tag;
        int i=0;
        while(i<n){
            pre = pre.next;
            i++;
        }

        while(pre.next!=null){
            pre = pre.next;
            cur = cur.next;
        }
        cur.next = cur.next.next;
        return tag.next;
    }
}
