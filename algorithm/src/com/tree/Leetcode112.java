package com.tree;

public class Leetcode112 {
    
    public static void exec(){
        TreeNode root = new TreeNode(5);
        TreeNode left = new TreeNode(4);
        left.left = new TreeNode(11);
        left.left.left = new TreeNode(7);
        left.left.right = new TreeNode(2);
        root.left = left;
        TreeNode right = new TreeNode(8);
        right.left = new TreeNode(13);
        right.right = new TreeNode(4);
        right.right.right = new TreeNode(11);
        root.right = right;
        boolean result = maxPath(root, 22);
        System.out.println(result);
    }

    public static boolean maxPath(TreeNode root,int balance){
        if(root == null) return false;
        if(root.left == null && root.right == null){
            return balance == root.value;
        }
        int remain = balance - root.value;
        boolean left = maxPath(root.left, remain);
        boolean right = maxPath(root.right, remain);
        return left||right;
    }
}
