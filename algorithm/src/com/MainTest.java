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
import com.tree.Leetcode101;
import com.tree.Leetcode104;
import com.tree.Leetcode108;
import com.tree.Leetcode109;
import com.tree.Leetcode110;
import com.tree.Leetcode111;
import com.tree.Leetcode112;
import com.tree.Leetcode144;
import com.tree.Leetcode145;
import com.tree.Leetcode226;
import com.tree.Leetcode230;
import com.tree.Leetcode235;
import com.tree.Leetcode236;
import com.tree.Leetcode337;
import com.tree.Leetcode404;
import com.tree.Leetcode437;
import com.tree.Leetcode501;
import com.tree.Leetcode513;
import com.tree.Leetcode530;
import com.tree.Leetcode538;
import com.tree.Leetcode543;
import com.tree.Leetcode572;
import com.tree.Leetcode617;
import com.tree.Leetcode637;
import com.tree.Leetcode653;
import com.tree.Leetcode669;
import com.tree.Leetcode671;
import com.tree.Leetcode687;
import com.tree.Leetcode94;
import com.tree.TreeNode;

public class MainTest {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        // TreeNode node =  Leetcode109.exec();
        // printTree(node);
        Leetcode501.exec();
        // System.out.println("result: "+result);
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
