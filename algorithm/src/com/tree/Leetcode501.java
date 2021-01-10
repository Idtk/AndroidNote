package com.tree;

import java.util.ArrayList;
import java.util.List;

public class Leetcode501 {
    public static void exec(){
        TreeNode root = new TreeNode(5);
        TreeNode left = new TreeNode(3);
        left.left = new TreeNode(2);
        left.right = new TreeNode(3);
        root.left = left;
        TreeNode right = new TreeNode(6);
        right.right = new TreeNode(6);
        root.right = right;
        dfs(root);
        System.out.println("result: "+list.toString());
    }

    public static List<Integer> list = new ArrayList<>();
    public static int base,count,maxCount;

    public static void dfs(TreeNode root){
        if(root == null) return;
        dfs(root.left);
        if(base == root.value){
            count++;
        }else{
            count = 1;
            base = root.value;
        }

        if(count == maxCount){
            list.add(root.value);
        }

        if(count > maxCount){
            list.clear();
            list.add(root.value);
            maxCount = count;
        }
        dfs(root.right);
    }
}
