package com.greedy;

public class Leetcode455 {
    public static void exec(){
        int[] g = new int[]{1,2,3};
        int[] s = new int[]{1,1};
        int result = findContentChildren(g, s);
        System.out.println("result: "+result);
    }

    public static int findContentChildren(int[] g, int[] s) {
        int gi = 0 , si = 0;
        while(gi<g.length && si<s.length){
            if(g[gi] <= s[si]){
                gi++;
            }
            si++;
        }
        return gi;
    }
}
