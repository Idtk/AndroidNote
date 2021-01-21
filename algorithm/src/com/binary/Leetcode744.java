package com.binary;

public class Leetcode744 {
    public static void exec(){
        char[] letters = new char[]{'c','f','j'};
        char result = nextGreatestLetter(letters, 'a');
        System.out.println("result: "+result);
    }

    public static char nextGreatestLetter(char[] letters, char target) {
        int l = 0, r=letters.length;
        while(l<r){
            int m = l+(r-l)/2;
            if(letters[m]>target){
                r = m-1;
            }else{
                l = m;
            }
        }
        return letters[r % letters.length];
    }
}
