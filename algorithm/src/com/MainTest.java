package com;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

import com.basic.HeapSort;
import com.list.Leetcode160;
import com.list.Leetcode19;
import com.list.Leetcode206;
import com.list.Leetcode21;
import com.list.Leetcode234;
import com.list.Leetcode24;
import com.list.Leetcode328;
import com.list.Leetcode445;
import com.list.Leetcode725;
import com.list.Leetcode83;
import com.tree.Leetcode104;
import com.tree.Leetcode110;
import com.tree.Leetcode543;
import com.tree.TreeNode;

public class MainTest {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        Leetcode543.exec();
    }

    /**
     * 层级遍历打印树
     */
    public static void printTree(TreeNode root){
        Queue<TreeNode> queue = new ArrayDeque<TreeNode>();
        queue.offer(root);
        while(!queue.isEmpty()){
            TreeNode node = queue.poll();
            System.out.println(node.value+"");
            if(node.left != null){
                queue.offer(node.left);
            }
            if(node.right != null){
                queue.offer(node.right);
            }
        }
    }
}
