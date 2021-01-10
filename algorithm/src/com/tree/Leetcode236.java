package com.tree;

public class Leetcode236 {
    public static void exec(){
        TreeNode root = new TreeNode(3);
        TreeNode left = new TreeNode(5);
        left.left = new TreeNode(6);
        left.right = new TreeNode(2);
        left.right.left = new TreeNode(7);
        left.right.right = new TreeNode(4);
        root.left = left;
        TreeNode right = new TreeNode(1);
        right.left = new TreeNode(0);
        right.right = new TreeNode(8);
        root.right = right;
        TreeNode result = lowestCommonAncestor(root, new TreeNode(6), new TreeNode(8));
        System.out.println("result: "+result.value);
    }

    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null) return null;
        if(root.value == p.value || root.value == q.value){
            return root;
        }
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if(left == null) return right;
        if(right == null) return left;
        return root;
    }
}
