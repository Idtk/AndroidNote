package com.tree;

public class Leetcode572 {
    
    public static void exec(){
        TreeNode s = new TreeNode(3);
        TreeNode left = new TreeNode(4);
        left.left = new TreeNode(1);
        left.right = new TreeNode(2);
        s.left = left;
        TreeNode right = new TreeNode(5);
        s.right = right;

        TreeNode t = new TreeNode(4);
        t.left = new TreeNode(1);
        t.right = new TreeNode(2);
        boolean result = isSubtree(s, t);
        System.out.println("result: "+result);
    }

    public static boolean isSubtree(TreeNode s, TreeNode t){
        if(s == null){
            return false;
        }
        return checkTree(s, t) || isSubtree(s.right, t) || isSubtree(s.left, t);
    }

    public static boolean checkTree(TreeNode s, TreeNode t){
        if(t == null && s == null){
            return true;
        }
        if(t == null || s == null || t.value != s.value) return false;
        boolean left = checkTree(s.left, t.left);
        boolean right = checkTree(s.right, t.right);
        return left && right;
    }
}
