package com.greedy;

public class Leetcode122 {
    public static void exec(){
        int[] prices = new int[]{7,1,5,3,6,4};
        int result = maxProfit(prices);
        System.out.println("result: "+result);
    }

    public static int maxProfit(int[] prices) {
        int sum = 0;
        for(int i = 1;i<prices.length;i++){
            if(prices[i]-prices[i-1]>0){
                sum += prices[i]-prices[i-1];
            }
        }
        return sum;
    }
}
