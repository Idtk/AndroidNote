package com.tree;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Leetcode145 {
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
        // stack.add(root);
        TreeNode cur = root;
        while(!stack.isEmpty() || cur != null){
            while(cur != null){
                list.add(cur.value);
                stack.add(cur);
                cur = cur.right;
            }
            cur = stack.pop();
            cur = cur.left;
        }
        Collections.reverse(list);
        return list;
    }

    public static List<Integer> preorderTraversal2(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        List<Integer> list = new ArrayList<>();
        stack.add(root);
        while(!stack.isEmpty()){
            TreeNode node = stack.pop();
            list.add(node.value);
            if(node.left != null) stack.add(node.left);
            if(node.right != null) stack.add(node.right);
        }
        Collections.reverse(list);
        return list;
    }
}
