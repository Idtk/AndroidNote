package com.tree;

public class Leetcode530 {
    public static void exec(){
        TreeNode root = new TreeNode(5);
        TreeNode left = new TreeNode(3);
        left.left = new TreeNode(2);
        left.right = new TreeNode(4);
        root.left = left;
        TreeNode right = new TreeNode(6);
        right.right = new TreeNode(7);
        root.right = right;
        dfs(root);
        System.out.println("result: "+min);
    }

    public static int pre = -1;
    public static int min = Integer.MAX_VALUE;

    public static void dfs(TreeNode root){
        if(root == null) return;
        dfs(root.left);
        if(pre>-1){
            min = Math.min(root.value - pre, min);
        }
        pre = root.value;
        dfs(root.right);
    }
}
