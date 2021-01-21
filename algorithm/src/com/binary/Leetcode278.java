package com.binary;


public class Leetcode278 {
    public static void exec(){
        int result = firstBadVersion(5);
        System.out.println("result: "+result);
    }

    public static int firstBadVersion(int n) {
        int l = 1;
        int r = n;
        while(l<r){
            int m = l + (r-l)/2;
            if(isBadVersion(m)){
                r = m;
            }else{
                l = m+1;
            }
        }
        return r;
    }

    public static boolean isBadVersion(int version){
        return version>=1;
    }
}
