package com.doublePointer;

public class Leetcode633 {
    public static void exec(){
        boolean result = judgeSquareSum(5);
        System.out.println("result: "+result);
    }

    public static boolean judgeSquareSum(int c) {
        int l = 0;
        int r = (int)Math.sqrt(c);
        while(l<=r){
            int sum = l*l + r*r;
            if(sum<c){
                l++;
            }
            if(sum > c){
                r--;
            }
            if(sum == c){
                return true;
            }
        }
        return false;
    }
}
