package com.tree;

import java.util.ArrayDeque;
import java.util.Queue;

public class Leetcode513 {
    
    public static void exec(){
        TreeNode root = new TreeNode(3);
        TreeNode left = new TreeNode(9);
        root.left = left;
        TreeNode right = new TreeNode(20);
        right.left = new TreeNode(15);
        right.right = new TreeNode(7);
        root.right = right;
        int result = findBottomLeftValue2(root);
        System.out.println("result: "+result);
    }

    public static int findBottomLeftValue(TreeNode root){
        int result = root.value;
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        while(!queue.isEmpty()){
            int len = queue.size();
            for(int i=0; i<len; i++){
                TreeNode node = queue.poll();
                if(i == 0){
                    result = node.value;
                }
                if(node.left != null){
                    queue.add(node.left);
                }
                if(node.right != null){
                    queue.add(node.right);
                }
            }
        }
        return result;
    }

    /**
     * 从上到下，从右到左，这样最后一个就是最底层的最左边了
     * @param root
     * @return
     */
    public static int findBottomLeftValue2(TreeNode root){
        int result = root.value;
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        while(!queue.isEmpty()){
            TreeNode node = queue.poll();
            result = node.value;
            if(node.right != null){
                queue.add(node.right);
            }
            if(node.left != null){
                queue.add(node.left);
            }
        }
        return result;
    }
}
