package com.greedy;

public class Leetcode121 {
    
    public static void exec(){
        int[] prices = new int[]{7,1,5,3,6,4};
        int result = maxProfit(prices);
        System.out.println("result: "+result);
    }

    public static int maxProfit(int[] prices) {
        int min=prices[0];
        int max=0;
        for(int i = 1 ; i< prices.length ;i++){
            if(prices[i]<min){
                min = prices[i];
            }else if(prices[i]-min>max){
                max = prices[i]-min;
            }
        }
        return max;
    }
}
