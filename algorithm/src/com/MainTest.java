package com;
import java.util.ArrayDeque;
import java.util.Queue;

import com.binary.Leetcode153;
import com.binary.Leetcode278;
import com.binary.Leetcode34;
import com.binary.Leetcode540;
import com.binary.Leetcode69;
import com.binary.Leetcode744;
import com.doublePointer.Leetcode141;
import com.doublePointer.Leetcode167;
import com.doublePointer.Leetcode345;
import com.doublePointer.Leetcode524;
import com.doublePointer.Leetcode633;
import com.doublePointer.Leetcode680;
import com.doublePointer.Leetcode88;
import com.greedy.Leetcode121;
import com.greedy.Leetcode122;
import com.greedy.Leetcode406;
import com.greedy.Leetcode435;
import com.greedy.Leetcode452;
import com.greedy.Leetcode455;
import com.greedy.Leetcode53;
import com.greedy.Leetcode605;
import com.greedy.Leetcode655;
import com.greedy.Leetcode763;
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
import com.queueAndstack.Leetcode503;
import com.queueAndstack.Leetcode739;
import com.sort.Leetcode347;
import com.sort.Leetcode451;
import com.sort.Leetcode75;
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
        Leetcode34.exec();
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
