package com.top100;

public class LT100_32 {
    public static void exec(){
        int res = longestValidParentheses("(()())");
        System.out.println("res: "+res);
    }

    public static int longestValidParentheses(String s) {
        int[] dp = new int[s.length()];
        int ans = 0;
        for(int i=1;i<s.length();i++){
            if(s.charAt(i)!=')'){
                continue;
            }
            if(s.charAt(i-1)=='('){
                dp[i] = ((i>=2)?dp[i-2]:0)+2;
            }else if(i-dp[i-1]>0 && s.charAt(i-dp[i-1]-1)=='('){
                dp[i] = dp[i-1]+2+((i-dp[i-1]>=2)?dp[i-dp[i-1]-2]:0);
            }
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }
}
