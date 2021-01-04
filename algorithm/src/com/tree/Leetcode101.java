package com.tree;

public class Leetcode101 {
    public static void exec(){
        TreeNode root = new TreeNode(1);
        TreeNode left = new TreeNode(2);
        left.left = new TreeNode(3);
        left.right = new TreeNode(4);
        root.left = left;
        TreeNode right = new TreeNode(2);
        right.left = new TreeNode(4);
        right.right = new TreeNode(3);
        root.right = right;
        boolean result = isSymmetric(root);
        System.out.println("result: "+ result);
    }

    public static boolean isSymmetric(TreeNode root){
        if(root == null) return false;
        return dfs(root.left, root.right);
    }

    public static boolean dfs(TreeNode left, TreeNode right){
        if(left == null && right == null){
            return true;
        }
        if(left == null || right == null || left.value != right.value) return false;
        boolean d1 = dfs(left.left, right.right);
        boolean d2 = dfs(left.right, right.left);
        return d1 && d2;
    }
}
