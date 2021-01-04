package com.tree;

public class Leetcode687 {
    public static void exec(){
        TreeNode root = new TreeNode(3);
        TreeNode left = new TreeNode(3);
        left.left = new TreeNode(3);
        left.right = new TreeNode(3);
        root.left = left;
        TreeNode right = new TreeNode(3);
        right.left = new TreeNode(15);
        right.right = new TreeNode(7);
        root.right = right;
        int result = longestUnivaluePath(root);
        System.out.println("result: "+ ans);
    }

    static int ans = 0;

    public static int longestUnivaluePath(TreeNode root) {
        if(root == null) return 0;
        int left = longestUnivaluePath(root.left);
        int right = longestUnivaluePath(root.right);
        int arrowLeft = 0, arrowRight = 0;
        if(root.left != null && root.value == root.left.value){
            arrowLeft += left+1;
        }
        if(root.right != null && root.value == root.right.value){
            arrowRight += right+1;
        }
        ans = Math.max(arrowLeft+arrowRight, ans);
        return Math.max(arrowLeft,arrowRight);
    }
}
