package com.list;

public class Leetcode160 {
    
    public static void exec(){
        // 构造数据
        ListNode A = new ListNode(1);
        int i = 1;
        ListNode temp = A;
        while(i<8){
            temp.next = new ListNode(++i);
            temp = temp.next;
        }

    
        ListNode B = new ListNode(8);
        i = 4;
        temp = B;
        while(i<8){
            temp.next = new ListNode(++i);
            temp = temp.next;
        }

        // ListNode tempEx = B;
        // while(tempEx.next != null){
        //     System.out.println(tempEx.value+"");
        //     tempEx = tempEx.next;
        // }
        compareTo(A, B);
    }

    /**
     * 比较AB链表，获取相交点
     * @param A
     * @param B
     */
    public static void compareTo(ListNode A,ListNode B){
        ListNode AHeader = A;
        ListNode BHeader = B;
        while(A.value != B.value){
            A = A.next == null ? BHeader : A.next;
            B = B.next == null ? AHeader : B.next;
        }
        System.out.println(A.value+"");
    }
}
