package com.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

import com.list.Stack;

public class Leetcode230 {
    public static void exec(){
        TreeNode root = new TreeNode(3);
        TreeNode left = new TreeNode(2);
        left.left = new TreeNode(1);
        root.left = left;
        TreeNode right = new TreeNode(4);
        root.right = right;
        int result = kthSmallest2(root,2);
        System.out.println("result: "+result);
    }

    /**
     * 递归解法
     * @param root
     * @param k
     * @return
     */
    public static int kthSmallest(TreeNode root, int k) {
        ArrayList<Integer> list = dfs(root, new ArrayList<Integer>());
        return list.get(k-1);
    }

    public static ArrayList<Integer> dfs(TreeNode root, ArrayList<Integer> arr){
        if(root == null) return arr;
        dfs(root.left, arr);
        arr.add(root.value);
        dfs(root.right, arr);
        return arr;
    }

    /**
     * 迭代
     * @param root
     * @param k
     * @return
     */
    public static int kthSmallest2(TreeNode root, int k) {
        Queue<TreeNode> queue = new ArrayDeque<TreeNode>();
        Stack<TreeNode> stack = new Stack<>();
        ArrayList<Integer> list = new ArrayList<>();
        queue.add(root);
        while(!queue.isEmpty()){
            TreeNode node = queue.poll();
            while(!stack.isEmpty() || node != null){
                while(node != null){
                    stack.push(node);
                    node = node.left;
                }
                node = stack.pop();
                list.add(node.value);
                if(list.size() == k){
                    return node.value;
                }
                node = node.right;
            }
        }
        return list.get(k-1);
        // System.out.println("result: "+list.toString());
    }

}
