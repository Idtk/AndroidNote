package com.tree;

public class Leetcode110 {
    public static void exec(){
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        TreeNode right = new TreeNode(20);
        right.left = new TreeNode(15);
        right.right = new TreeNode(7);
        right.right.right = new TreeNode(17);
        root.right = right;
        // int depth = depth(root);
        boolean result = recur(root)>=0;
        System.out.println("result: "+ result);

    }

    public static boolean result = true;
    public static int depth(TreeNode root){
        if(root == null) return 0;
        int left = depth(root.left);
        int right = depth(root.right);
        if(Math.abs(left-right)>1){
            result = false;
        }
        return Math.max(left, right)+1;
    }

    public static int recur(TreeNode root){
        if(root == null) return 0;
        int left = recur(root.left);
        if(left == -1) return -1;
        int right = recur(root.right);
        if(right == -1) return -1;
        return Math.abs(left-right) < 2 ? Math.max(left, right)+1:-1;
    }
}
