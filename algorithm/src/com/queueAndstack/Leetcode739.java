package com.queueAndstack;

import java.util.Arrays;
import java.util.Stack;

public class Leetcode739 {
    
    public static void exec(){
        int[] temp = new int[]{73,74,75,71,69,72,76,74};
        int[] result = dailyTemperatures(temp);
        System.out.println("result: "+Arrays.toString(result));
    }

    public static int[] dailyTemperatures(int[] temp) {
        Stack<Integer> index = new Stack<>();
        int n = temp.length;
        int[] dist = new int[n];
        for(int i=0; i < n ; i++){
            while(!index.isEmpty() && temp[i]>temp[index.peek()]){
                int pre = index.pop();
                dist[pre] = i - pre;
            }
            index.push(i);
        }
        return dist;
    }
}
