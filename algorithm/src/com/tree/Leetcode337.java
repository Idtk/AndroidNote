package com.tree;

import java.util.HashMap;

public class Leetcode337 {
    
    public static void exec(){
        TreeNode root = new TreeNode(3);
        TreeNode left = new TreeNode(2);
        left.right = new TreeNode(3);
        root.left = left;
        TreeNode right = new TreeNode(3);
        right.right = new TreeNode(1);
        root.right = right;
        int result = rob3(root);
        System.out.println("result: "+result);
    }

    /**
     * 解法一 暴力
     * @param root
     * @return
     */
    public static int rob(TreeNode root) {
        if(root == null){
            return 0;
        }
        int money = root.value;
        if(root.left != null){
            money += rob(root.left.left)+rob(root.left.right);
        }
        if(root.right != null){
            money += rob(root.right.left) + rob(root.right.right);
        }
        return Math.max(money, (rob(root.left)+rob(root.right)));
    }

    /**
     * 解法二 暴力+记忆
     */
    public static int rob2(TreeNode root) {
        HashMap<TreeNode,Integer> memo = new HashMap<>();
        return robInternal2(root,memo);
    }

    public static int robInternal2(TreeNode root, HashMap<TreeNode,Integer> memo) {
        if(root == null){
            return 0;
        }
        if (memo.containsKey(root)) return memo.get(root);
        int money = root.value;
        if(root.left != null){
            money += robInternal2(root.left.left,memo)+robInternal2(root.left.right,memo);
        }
        if(root.right != null){
            money += robInternal2(root.right.left,memo) + robInternal2(root.right.right,memo);
        }
        int result = Math.max(money, (robInternal2(root.left,memo)+robInternal2(root.right,memo)));
        memo.put(root, result);
        return result;
    }

    /**
     * 解法三 DP
     * @param root
     * @return
     * int[] res = new int[2] 0 代表不偷，1 代表偷
     */
    
    public static int rob3(TreeNode root){
        int[] result = robInternal3(root);
        return Math.max(result[0], result[1]);
    }

    public static int[] robInternal3(TreeNode root){
        if(root == null) return new int[2];
        int[] left = robInternal3(root.left);
        int[] right = robInternal3(root.right);
        int[] result = new int[2];
        result[0] = Math.max(left[0], left[1])+Math.max(right[0], right[1]);
        result[1] = left[0]+right[0] + root.value;
        return result;
    }

}
