package com.top100;

import com.list.ListNode;

public class LT100_23 {

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

        ListNode C = new ListNode(1);
        ListNode C1 = new ListNode(3);
        ListNode C2 = new ListNode(4);
        C.next = C1;
        C1.next = C2;
    
        ListNode tempEx = mergeKLists(new ListNode[]{A,B,C});
        while(tempEx != null){
            System.out.println(tempEx.value+"");
            tempEx = tempEx.next;
        }
    }

    public static ListNode mergeKLists(ListNode[] lists) {
        if(lists.length == 0) return null;
        return partList(0, lists.length-1, lists);
    }

    public static ListNode partList(int left,int right, ListNode[] lists){
        if(left == right){
            return lists[left];
        }

        int mid = left + (right-left)/2;
        ListNode l = partList(left, mid, lists);
        ListNode r = partList(mid+1, right, lists);
        return merge(l, r);
    }

    public static ListNode merge(ListNode l1,ListNode l2){
        ListNode head = new ListNode(-1);
        ListNode tail = head;
        while(l1!=null && l2!=null){
            if(l1.value<l2.value){
                tail.next = l1;
                l1 = l1.next;
            }else{
                tail.next = l2;
                l2 = l2.next;
            }
            tail = tail.next;
        }
        if(l1 != null){
            tail.next = l1;
        }
        if(l2 != null){
            tail.next = l2;
        }
        return head.next;
    }
}
