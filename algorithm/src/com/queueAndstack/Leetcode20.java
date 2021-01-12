package com.queueAndstack;

import java.util.Stack;

public class Leetcode20 {
    
    static Stack<Character> stack = new Stack<>();

    public static void exec(){
        String str = "{[()]}";
    }

    public static boolean isValid(String s) {
        char[] chars = s.toCharArray();
        int len = chars.length;
        for(int i=0; i<len; i++){
            char c = chars[i];
            if(c == '{' || c == '[' || c == '('){
                stack.push(c);
            }else{
                char pop = stack.pop();
                boolean b1 = c == '}' && pop != '{';
                boolean b2 = c == ']' && pop != '[';
                boolean b3 = c == ')' && pop != '(';
                if(b1||b2||b3){
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}
