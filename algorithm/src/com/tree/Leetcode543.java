package com.tree;

public class Leetcode543 {
    public static void exec(){
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.left.left = new TreeNode(6);
        root.left.right = new TreeNode(12);
        TreeNode right = new TreeNode(20);
        right.left = new TreeNode(15);
        right.right = new TreeNode(7);
        // right.right.right = new TreeNode(17);
        // right.right.left = new TreeNode(13);
        // right.right.right.right = new TreeNode(8);
        // right.right.right.right.right = new TreeNode(10);
        root.right = right;
        maxPath(root);
        System.out.println("result: "+ ans);
    }

    public static int ans = 0;

    public static int maxPath(TreeNode root){
        if(root == null) return 0;
        int left = maxPath(root.left);
        int right = maxPath(root.right);
        ans = Math.max(left+right, ans);
        return Math.max(left, right)+1;
    }

    // public static int ans2 = 1;
    // maxPath2(root);
    // int result = ans2-1;

    // public static int maxPath2(TreeNode root){
    //     if(root == null) return 0;
    //     int left = maxPath2(root.left);
    //     int right = maxPath2(root.right);
    //     ans = Math.max(left+right+1, ans);
    //     return Math.max(left, right)+1;
    // }
}
