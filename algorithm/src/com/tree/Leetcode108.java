package com.tree;

public class Leetcode108 {
    public static TreeNode exec(){
        int[] nums = new int[]{-10,-3,0,5,9};
        return sortedArrayToBST(nums, 0, nums.length-1);
    }

    public static TreeNode sortedArrayToBST(int[] nums,int l ,int r) {
        if(r<l) return null;
        int mid = (l+r+1)/2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = sortedArrayToBST(nums, l, mid-1);
        root.right = sortedArrayToBST(nums, mid+1, r);
        return root;
    }
}
