package com.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack; 

public class Leetcode94 {
    public static void exec(){
        TreeNode root = new TreeNode(3);
        TreeNode left = new TreeNode(9);
        root.left = left;
        TreeNode right = new TreeNode(20);
        right.left = new TreeNode(15);
        right.right = new TreeNode(7);
        root.right = right;
        List<Integer> result = preorderTraversal(root);
        System.out.println("result: "+result.toString());
    }

    public static List<Integer> preorderTraversal(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        List<Integer> list = new ArrayList<>();
        TreeNode cur = root;
        while(!stack.isEmpty() || cur != null){
            while(cur != null){
                stack.add(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            list.add(cur.value);
            cur = cur.right;
        }
        return list;
    }
}
