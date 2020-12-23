package com.list;

import java.util.Stack;

public class Leetcode445 {
    
    public static void exec(){
        ListNode A = new ListNode(1);
        ListNode A1 = new ListNode(2);
        ListNode A2 = new ListNode(4);
        ListNode A3 = new ListNode(8);
        A.next = A1;
        A1.next = A2;
        A2.next = A3;

        ListNode B = new ListNode(1);
        ListNode B1 = new ListNode(5);
        ListNode B2 = new ListNode(4);
        B.next = B1;
        B1.next = B2;
        
        ListNode tempEx = addNode(A, B);
        while(tempEx != null){
            System.out.println(tempEx.value+"");
            tempEx = tempEx.next;
        }
    }

    public static ListNode addNode(ListNode A, ListNode B){
        ListNode C = null;
        Stack<Integer> stackA = new Stack<Integer>();
        Stack<Integer> stackB = new Stack<Integer>();
        while(A != null){
            stackA.push(A.value);
            A = A.next;
        }
        while(B != null){
            stackB.push(B.value);
            B = B.next;
        }
        int temp = 0;
        while(!stackA.isEmpty() || !stackB.isEmpty() || temp > 0){
            int lA = stackA.isEmpty()? 0 : stackA.pop();
            int lB = stackB.isEmpty()? 0 : stackB.pop();
            int cur = lA+lB+temp;
            temp = cur/10;
            ListNode result = new ListNode(cur%10);
            result.next = C;
            C = result;
        }
        return C;
    }

}
