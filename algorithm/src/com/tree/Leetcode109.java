package com.tree;

import com.list.ListNode;

public class Leetcode109 {
    public static TreeNode exec(){
        ListNode head = new ListNode(-10);
        head.next = new ListNode(-3);
        head.next.next = new ListNode(0);
        head.next.next.next = new ListNode(5);
        head.next.next.next.next = new ListNode(9);
        return sortedListToBST(head);
    }

    public static ListNode pre;

    public static TreeNode sortedListToBST(ListNode head){
        int length = 0;
        ListNode node = head;
        while(node!= null){
            length++;
            node = node.next;
        }
        pre = head;
        return dfs(0, length-1);
    }

    public static TreeNode dfs(int l, int r){
        if(l>r) return null;
        int mid = (r+l+1)/2;
        TreeNode root = new TreeNode();
        root.left = dfs(l, mid-1);
        root.value = pre.value;
        pre = pre.next;
        root.right = dfs(mid+1, r);
        return root;
    }

}
