package com.tree;

import java.util.ArrayDeque;
import java.util.Queue;

public class Leetcode111 {
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
        int result = minDepth2(root);
        System.out.println("result: "+ result);
    }

    public static int minDepth(TreeNode root) {
        int step = 1;
        Queue<TreeNode> queue = new ArrayDeque<TreeNode>();
        queue.add(root);
        while(!queue.isEmpty()){
            int length = queue.size();
            for(int i=0; i<length ; i++){
                TreeNode node = queue.poll();
                if(node.left == null || node.right == null){
                    return step;
                }
                queue.add(node.left);
                queue.add(node.right);
            }

            ++step;
        }

        return step;
    }

    public static int minDepth2(TreeNode root){
        if(root == null) return 0;
        int left = minDepth(root.left);
        int right = minDepth(root.right);
        return Math.min(left, right)+1;
    }
}
