package com.dfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Leetcode17 {
    
    public static void exec(){
        List<String> result = letterCombinations("23");
        System.out.println("result: "+Arrays.toString(result.toArray()));
    }

    public static List<String> combinations = new ArrayList<>();
    
    public static HashMap<Character,String> map = new HashMap<>(){{
        put('2', "abc");
        put('3', "def");
        put('4', "ghi");
        put('5', "jkl");
        put('6', "mno");
        put('7', "pqrs");
        put('8', "tuv");
        put('9', "wxyz");
    }};

    public static List<String> letterCombinations(String digits) {
        if(digits.length() == 0){
            return combinations;
        }

        backtrack(digits, 0, new StringBuilder());
        return combinations;
    }

    public static void backtrack(String digits, int index, StringBuilder s){
        if(index == digits.length()){
            combinations.add(s.toString());
            return;
        }

        char digit = digits.charAt(index);
        String letters = map.get(digit);
        for(int i= 0 ; i<letters.length() ; i++){
            s.append(letters.charAt(i));
            backtrack(digits, index + 1, s);
            // 加入的位置与删除的位置需要对应，都是index
            s.deleteCharAt(index);
        }
    }
}
