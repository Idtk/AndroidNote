package com.tree;

public class Leetcode538 {
    public static void exec(){
        TreeNode root = new TreeNode(3);
        TreeNode left = new TreeNode(2);
        left.left = new TreeNode(1);
        root.left = left;
        TreeNode right = new TreeNode(4);
        root.right = right;
        TreeNode result = convertBST(root);
        System.out.println("result: "+result.toString());
    }

    public static int num = 0;

    public static TreeNode convertBST(TreeNode root) {
        if(root == null) return null;
        convertBST(root.right);
        num += root.value;
        root.value = num;
        convertBST(root.left);
        return root;
    }
}
