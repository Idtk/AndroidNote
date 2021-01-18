package com.greedy;

public class Leetcode392 {
    public static void exec(){
        String s = "abc";
        String t = "ahbgdc";
        boolean result = isSubsequence(s, t);
        System.out.println("result: "+result);
    }

    public static boolean isSubsequence(String s, String t) {
        int n = s.length() , m = t.length() , i = 0 , j = 0;
        while(i<n && j<m){
            if(s.charAt(i) == t.charAt(j)){
                i++;
            }
            j++;
        }
        return i == n;
    }
}
