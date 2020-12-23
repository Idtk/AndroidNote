package com.list;

public class Leetcode725 {
    
    public static void exec(){
        ListNode A = new ListNode(1);
        int i = 1;
        ListNode temp = A;
        while(i<4){
            temp.next = new ListNode(++i);
            temp = temp.next;
        }
        ListNode[] ans = split(A, 5);
        int size = ans.length;
        for(int j= 0; j<size ; j++){
            System.out.println("===========");
            ListNode tempEx = ans[j];
            while(tempEx != null){
                System.out.println(tempEx.value+"");
                tempEx = tempEx.next;
            }
        }
    }

    public static ListNode[] split(ListNode head, int k){
        int N = 0;
        ListNode cur = head;
        while(cur !=null){
            N++;
            cur = cur.next;
        }

        int width = N/k ;
        int remain = N%k;

        cur = head;
        ListNode[] ans = new ListNode[k];
        for(int i=0; i<k; i++){
            ListNode node = cur;
            for(int j=0; j<width+(i< remain ? 1 : 0)-1;j++){
                if(cur !=null) {
                    cur = cur.next;
                }
            }
            if(cur != null){
                ListNode pre = cur;
                cur = cur.next;
                pre.next = null;
            }
            ans[i] = node;
        }
        return ans;
    }
}
