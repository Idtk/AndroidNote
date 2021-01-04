package com.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

public class Leetcode437 {
    public static void exec(){
        TreeNode root = new TreeNode(10);
        TreeNode left = new TreeNode(5);
        left.left = new TreeNode(3);
        left.left.left = new TreeNode(3);
        left.left.right = new TreeNode(-2);
        left.right = new TreeNode(2);
        left.right.right = new TreeNode(1);
        root.left = left;
        TreeNode right = new TreeNode(-3);
        right.left = new TreeNode(11);
        root.right = right;
        // dfs_node(root, 8);
        // System.out.println(result.size());
        int size = init(root,8);
        System.out.println(size);
    }

    public static ArrayList<ArrayDeque<TreeNode>> result = new ArrayList();

    public static void dfs_sum(TreeNode root, int sum, ArrayDeque<TreeNode> path){
        if(root == null) return;
        if(root.value > sum) return;
        path.add(root);
        if(sum == root.value){
            result.add(path);
            return;
        }
        dfs_sum(root.left, sum-root.value, path.clone());
        dfs_sum(root.right, sum-root.value, path.clone());
    }

    public static void dfs_node(TreeNode root, int sum){
        if(root == null) return;
        dfs_sum(root, sum, new ArrayDeque<TreeNode>());
        dfs_node(root.left, sum);
        dfs_node(root.right, sum);
    }


    //第二种方法，一次便利
    public static int init(TreeNode root, int sum){
        HashMap<Integer,Integer> map = new HashMap<Integer,Integer>();
        map.put(0, 1);
        return sum(root,sum,0,map);
    }

    public static int sum(TreeNode root, int sum,int pathSum, HashMap<Integer,Integer> map){
        if(root == null) return 0;
        int res = 0;
        pathSum += root.value;
        res += map.getOrDefault(pathSum - sum, 0);
        map.put(pathSum, map.getOrDefault(pathSum, 0)+1);
        res = sum(root.left,sum,pathSum,map)+sum(root.right,sum,pathSum,map)+res;
        map.put(pathSum, map.getOrDefault(pathSum, 0)-1);
        return res;
    }
}
