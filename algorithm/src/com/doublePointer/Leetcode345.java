package com.doublePointer;

import java.util.Arrays;
import java.util.HashSet;

public class Leetcode345 {
    private final static HashSet<Character> vowels = 
    new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'));

    public static void exec(){
        String str = "hello";
        String result = reverseVowels(str);
        System.out.println("result: " + result);
    }

    public static String reverseVowels(String s) {
        int l = 0;
        int r = s.length() - 1;
        char[] chars = s.toCharArray();
        while(l<r){
            while(!vowels.contains(chars[l]) && l < r ){
                l++;
            }
            while(!vowels.contains(chars[r]) && l < r ){
                r--;
            }
            swap(chars, l, r);
            l++;
            r--;
        }
        return new String(chars);
    }

    public static void swap(char[] chars , int l ,int r){
        char temp = chars[l];
        chars[l] = chars[r];
        chars[r] = temp;
    }
}
