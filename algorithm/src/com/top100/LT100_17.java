package com.top100;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LT100_17 {

    public static void exec(){
        List<String> res = letterCombinations("23");
        System.out.println("res: "+res);
    }

    public static Map<Character,String> map = new HashMap<>(){{
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
        List<String> res = new ArrayList<>();
        if(digits.length() == 0){
            return res;
        }
        dfsbacktrack(0, digits, new StringBuilder(), res, map);
        return res;
    }

    public static void dfsbacktrack(int index, String digits, StringBuilder sb,
     List<String> res,Map<Character,String> map){
        if(digits.length() == index){
            res.add(sb.toString());
            return;
        }

        char num = digits.charAt(index);
        char[] cs = map.get(num).toCharArray();
        for(int i=0; i<cs.length; i++){
            sb.append(cs[i]);
            dfsbacktrack(index+1, digits, sb, res, map);
            sb.deleteCharAt(index);
        }
    }
    
}
