package com.tree;

public class Leetcode669 {
    public static TreeNode exec(){
        TreeNode root = new TreeNode(3);
        TreeNode left = new TreeNode(0);
        left.right = new TreeNode(2);
        left.right.left = new TreeNode(1);
        root.left = left;
        TreeNode right = new TreeNode(4);
        root.right = right;
        return trimBST(root, 1, 3);
    }

    public static TreeNode trimBST(TreeNode root, int low, int high) {
        if(root == null) return null;
        TreeNode left = root.left;
        TreeNode right = root.right;
        if(left != null){
            left = trimBST(root.left, low, high);
        }
        if(right != null){
            right = trimBST(root.right, low, high);
        }
        root.left = left;
        root.right = right;
        if(root.value > high || root.value < low){
            return left != null ? root.left : root.right;
        }
        return root;
    }
    /**
     * 二叉查找树的话，可以用以下方法
     */
    public static TreeNode trimBST2(TreeNode root, int low, int high) {
        if(root == null) return null;
        if(root.value > high) return trimBST2(root.left, low, high);
        if(root.value < low) return trimBST2(root.right, low, high);
        root.left = trimBST2(root.left, low, high);
        root.right = trimBST2(root.right, low, high);
        return root;
    }

}
