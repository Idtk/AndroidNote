package com.tree;

public class Leetcode617 {
    public static TreeNode exec(){
        TreeNode root0 = new TreeNode(1);
        root0.left = new TreeNode(3);
        root0.left.left = new TreeNode(5);
        root0.right = new TreeNode(2);

        TreeNode root1 = new TreeNode(2);
        root1.left = new TreeNode(1);
        root1.left.right = new TreeNode(4);
        root1.right = new TreeNode(3);
        root1.right.right = new TreeNode(7);

        return add(root0, root1);
    }

    public static TreeNode add(TreeNode root0, TreeNode root1){
        if(root0 == null && root1 == null) return null;
        if(root1 == null) return root0;
        if(root0 == null) return root1;
        TreeNode root = new TreeNode(root0.value + root1.value);
        TreeNode left = add(root0.left, root1.left);
        TreeNode right = add(root0.right, root1.right);
        root.left = left;
        root.right = right;
        return root;
    }
}
