package com.tree;

public class Leetcode235 {
    public static void exec(){
        TreeNode root = new TreeNode(6);
        TreeNode left = new TreeNode(2);
        left.left = new TreeNode(0);
        left.right = new TreeNode(4);
        left.right.left = new TreeNode(3);
        left.right.right = new TreeNode(5);
        root.left = left;
        TreeNode right = new TreeNode(8);
        right.left = new TreeNode(7);
        right.right = new TreeNode(9);
        root.right = right;
        TreeNode result = lowestCommonAncestor(root, new TreeNode(4), new TreeNode(0));
        System.out.println("result: "+result.value);
    }

    public static TreeNode node = null;

    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null) return null;
        dfs_lowest(root, p, q);
        return node;
    }

    public static void dfs_lowest(TreeNode root, TreeNode p, TreeNode q){
        if(root == null) return;
        int val = root.value;
        if((val-q.value) * (val - p.value)<=0){
            node = root;
        }
        if(node != null) return;
        dfs_lowest(p.value< val?root.left:root.right, p, q);
        // dfs_lowest(root.right, p, q);
    }
}
