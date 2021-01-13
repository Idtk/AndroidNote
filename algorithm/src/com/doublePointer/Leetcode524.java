package com.doublePointer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Leetcode524 {
    
    public static void exec(){
        String str = "abpcplea";
        List<String> dist = new ArrayList<>(Arrays.asList("ale","apple"
        ,"monkey","plea"));
        String result = findLongestWord(str, dist);
        System.out.println("result: "+result);
    }

    public static String findLongestWord(String s, List<String> d) {
        String longStr = "";
        for(String str:d){
            int longLen = longStr.length();
            int strLen = str.length();
            if(strLen < longLen ||(strLen == longLen && longStr.compareTo(str)<0)){
                continue;
            }
            if(same(s, str)){
                longStr = str;
            }
        }
        return longStr;
    }

    public static boolean same(String o, String n){
        int l1 = 0;
        int l2 = 0;
        while(l2 < n.length() && l1 < o.length()){
            if(o.charAt(l1) == n.charAt(l2)){
                l2++;
            }
            l1++;
        }

        return l2 == n.length();
    }
}
