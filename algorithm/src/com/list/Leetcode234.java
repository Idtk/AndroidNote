package com.list;

public class Leetcode234 {
    
    public static void exec(){
        ListNode A = new ListNode(1);
        ListNode A1 = new ListNode(4);
        ListNode A2 = new ListNode(9);
        ListNode A3 = new ListNode(4);
        ListNode A4 = new ListNode(1);
        A.next = A1;
        A1.next = A2;
        A2.next = A3;
        A3.next = A4;
        pairs(A);

        // 验证列表
        ListNode tempEx = A;
        while(tempEx != null){
            System.out.println(tempEx.value+"");
            tempEx = tempEx.next;
        }
    }

    public static void pairs(ListNode head){
        ListNode fast = head;
        ListNode slow = head;

        // 链表分割
        // 前半部分为head——slow
        // 后半部分为slow.next——fast
        while(fast.next != null && fast.next.next!=null){
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode l1 = head;
        ListNode l2 = slow.next;
        // slow.next = null;

        // 反转l2
        l2 = reverse(l2);

        // 比较l1、l2
        ListNode p1 = l1;
        ListNode p2 = l2;
        while(l1 != null && l2 != null){
            if(l1.value != l2.value){
                System.out.println("不是回文");
                break;
            }
            l1 = l1.next;
            l2 = l2.next;
        }
        System.out.println("是回文");

        // 再反转l2
        l2 = reverse(p2);
        // slow.next = l2;
    }

    private static ListNode reverse(ListNode head){
        ListNode pre = null;
        ListNode tmp = null;
        ListNode cur = head;
        while(cur != null){
            tmp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = tmp;
        }
        return pre;
    }
}
