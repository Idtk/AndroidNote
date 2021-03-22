package com.top100;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class LT100_20 {
    
    public static void exec(){
        boolean res  = isValid("{");
        System.out.println("res: "+res);
    }

    public static boolean isValid(String s) {
        Map<Character,Character> map = new HashMap<>(){{
            put('(', ')');
            put('{', '}');
            put('[', ']');
            put('!', '!');
        }};  
        if(s.length()==0) return true;
        LinkedList<Character> stack = new LinkedList<Character>(){{add('!');}};
        for(char c:s.toCharArray()){
            if(map.containsKey(c)) stack.addLast(c);
            else if(map.get(stack.removeLast()) != c) return false;
        } 

        return stack.size() == 1;
    }

}
