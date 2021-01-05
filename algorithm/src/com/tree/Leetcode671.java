package com.tree;

public class Leetcode671 {
    public static void exec(){
        TreeNode root = new TreeNode(2);
        TreeNode left = new TreeNode(2);
        root.left = left;
        TreeNode right = new TreeNode(2147483647);
        // right.left = new TreeNode(5);
        // right.right = new TreeNode(7);
        root.right = right;
        int result = findSecondMinimumValue(root);
        System.out.println("result: "+result);
    }

    public static int findSecondMinimumValue(TreeNode root) {
        if(root == null) return -1;
        if(root.left == null && root.right == null) return -1;
        int left = root.left.value;
        int right = root.right.value;
        if(left == root.value){
            left = findSecondMinimumValue(root.left);
        }
        if(right == root.value){
            right = findSecondMinimumValue(root.right);
        }
        if (left != -1 && right != -1) return Math.min(left, right);
        if (left != -1) return left;
        return right;
    }

    /**
     * 解法二
     * @param root
     * @return
     */
    public static int findSecondMinimumValue2(TreeNode root) {
        find(root);
        return count == 0 ? -1 : second;
    }
    static int first = Integer.MAX_VALUE, second = Integer.MAX_VALUE;
    static int count;
    public static void find(TreeNode root){
        if(root== null) return;
        if(root.value < first){
            second = first;
            first = root.value;
        }else if(root.value<=second && first<root.value){
            count++;
            second = root.value;
        }
        find(root.left);
        find(root.right);
    }
}
