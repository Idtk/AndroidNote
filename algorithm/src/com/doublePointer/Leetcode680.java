package com.doublePointer;

public class Leetcode680 {
    public static void exec(){
        String str = "abca";
        boolean result = validPalindrome(str);
        System.out.println("result: "+result);
    }

    public static boolean validPalindrome(String s) {
        // char[] chars = s.toCharArray();
        int l = 0;
        int r = s.length() - 1;
        int step = 0;
        while(l<r){
            char lc = s.charAt(l);
            char rc = s.charAt(r);
            char lcnext = s.charAt(l+1);
            char rcnext = s.charAt(r-1);
            if(lc == rc){
                l++;
                r--;
            }else if(lcnext == rc && step<1){
                l+=2;
                r--;
                step++;
            }else if(rcnext == lc && step<1){
                r -= 2;
                l++;
                step++;
            }else{
                return false;
            }
        }

        return true;
    }
}
