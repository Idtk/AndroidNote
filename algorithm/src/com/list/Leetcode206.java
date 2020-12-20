package com.list;

public class Leetcode206 {
    
    public static void exec(){
        // 构造数据
        ListNode origin = new ListNode(1);
        int i = 1;
        ListNode temp = origin;
        while(i<8){
            temp.next = new ListNode(++i);
            temp = temp.next;
        }

        ListNode tempEx = reverse2(origin);
        while(tempEx != null){
            System.out.println(tempEx.value+"");
            tempEx = tempEx.next;
        }
    }

    public static ListNode reverse(ListNode origin){
        ListNode pre = null;
        ListNode cur = origin;
        ListNode tmp = null;
        while(cur != null){
            tmp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = tmp;
        }
        return pre;
    }

    public static ListNode reverse2(ListNode origin){
        if(origin == null || origin.next == null){
            return origin;
        }

        ListNode cur = reverse2(origin.next);
        origin.next.next = origin;
        origin.next = null;
        return cur;
    }
}
