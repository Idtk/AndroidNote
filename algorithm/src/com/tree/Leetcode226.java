package com.tree;

public class Leetcode226 {
    
    public static TreeNode exec(){
        TreeNode root = new TreeNode(4);
        TreeNode left = new TreeNode(2);
        left.left = new TreeNode(1);
        left.right = new TreeNode(3);
        root.left = left;
        TreeNode right = new TreeNode(7);
        right.left = new TreeNode(6);
        right.right = new TreeNode(9);
        root.right = right;
        return reverse(root);
    }

    public static TreeNode reverse(TreeNode root){
        if(root == null) return null;
        TreeNode left = reverse(root.right);
        TreeNode right = reverse(root.left);
        root.left = left;
        root.right = right;
        return root;
    }
}
