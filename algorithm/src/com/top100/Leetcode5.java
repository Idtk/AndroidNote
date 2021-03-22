
package com.top100;

public class Leetcode5 {

    public static void exec(){
        String res = longestPalindrome("ab");
        System.out.println("result: "+res);
    }

    public static String longestPalindrome(String s) {
        int len = s.length();
        if(len<2){
            return s;
        }
        boolean[][] dp = new boolean[len][len];
        for(int i=0; i<len; i++){
            dp[i][i] = true;
        }

        int maxlen = 1;
        int start = 0;

        for(int j=1; j<len; j++){
            for(int i=0; i<j; i++){
                if(s.charAt(i) != s.charAt(j)){
                    dp[i][j] = false;
                }else{
                    if(j-i<3){
                        dp[i][j] = true;
                    }else{
                        dp[i][j] = dp[i+1][j-1];
                    }
                }

                if(dp[i][j] && (j-i+1>maxlen)){
                    maxlen = j-i+1;
                    start=i;
                }
            }
        }

        return s.substring(start,start+maxlen);
        
    }
}
