package com.binary;

public class Leetcode69 {
    public static void exec(){
        int result = mySqrt(10);
        System.out.println("result: "+result);
    }

    public static int mySqrt(int x) {
        int l = 0;
        int r = x/2+1;
        // int ans = -1;
        while(l<=r){
            int m = l + (r-l)/2;
            int temp = m*m;
            if(temp>x){
                r = m-1;
            }else{
                // ans = m;
                l = m+1;
            }
        }
        // return ans;
        return l-1;
    }
}
