package com.tree;

import java.util.ArrayDeque;
import java.util.Queue;

public class Leetcode404 {
    public static void exec(){
        TreeNode root = new TreeNode(3);
        TreeNode left = new TreeNode(9);
        // left.left = new TreeNode(3);
        // left.right = new TreeNode(4);
        root.left = left;
        TreeNode right = new TreeNode(20);
        right.left = new TreeNode(15);
        right.right = new TreeNode(7);
        root.right = right;
        // int result = sumOfLeftLeaves(root,0);
        int result = sumOfLeftLeaves2(root);
        System.out.println("result: "+ result);
    }

    public static int sumOfLeftLeaves(TreeNode root,int sum) {
        if(root == null || root.left == null) return sum;
        sum += root.left.value;
        int left = sumOfLeftLeaves(root.left, 0);
        int right = sumOfLeftLeaves(root.right, 0);
        return sum+left+right;
    }

    public static int sumOfLeftLeaves2(TreeNode root){
        int sum = 0;
        Queue<TreeNode> queue = new ArrayDeque<TreeNode>();
        queue.add(root);
        while(!queue.isEmpty()){
            TreeNode node = queue.poll();
            if(node.left != null){
                queue.add(node.left);
                sum += node.left.value;
            }
            if(node.right != null){
                queue.add(node.right);
            }
        }
        return sum;
    }
}
