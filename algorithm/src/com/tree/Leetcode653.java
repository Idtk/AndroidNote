package com.tree;

import java.util.ArrayList;
import java.util.List;

public class Leetcode653 {
    public static boolean exec(){
        TreeNode root = new TreeNode(5);
        TreeNode left = new TreeNode(3);
        left.left = new TreeNode(2);
        left.right = new TreeNode(4);
        root.left = left;
        TreeNode right = new TreeNode(6);
        right.right = new TreeNode(7);
        root.right = right;
        
        List<Integer> list = new ArrayList<Integer>();
        toList(root, list);
        int target = 5;
        int l = 0,r = list.size() - 1;
        while(l<r && l >-1 && r < list.size()){
            int result = list.get(l)+list.get(r);
            if(target > result){
                l++;
            }
            if(target < result){
                r--;
            }
            if(target == result){
                return true;
            }
        }
        return false;
    }

    public static void toList(TreeNode root, List<Integer> list){
        if(root == null) return;
        toList(root.left, list);
        list.add(root.value);
        toList(root.right, list);
    }
}
