package com.queueAndstack;

import java.util.Arrays;
import java.util.Stack;


public class Leetcode503 {
    
    public static void exec(){
        int[] nums = new int[]{73,74,75,71,69,72,76,74};
        int[] result = nextGreaterElements(nums);
        System.out.println("result: "+Arrays.toString(result));
    }

    public static int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int[] dist = new int[n];
        Arrays.fill(dist, -1);
        Stack<Integer> pre = new Stack<>();
        for(int i = 0; i<2*n ; i++){
            int num = nums[i%n];
            while(!pre.isEmpty()&&num>nums[pre.peek()]){
                int pop = pre.pop();
                dist[pop] = num;
            }
            if(i<n){
                pre.push(i);
            }
        }
        
        return dist;
    }
}
