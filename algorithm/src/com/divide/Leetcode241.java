package com.divide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Leetcode241 {
    public static void exec(){
        String input = "2*3-4*5";
        List<Integer> result = diffWaysToCompute(input);
        System.out.println("result: "+result.toString());
    }

    static HashMap<String,List<Integer>> map = new HashMap<>(); 

    public static List<Integer> diffWaysToCompute(String input) {
        List<Integer> ways = new ArrayList<>(); 
        for(int i = 0; i<input.length(); i++){
            char c = input.charAt(i);
            if(c =='+' || c=='-' || c=='*'){
                List<Integer> left = map.get(input.substring(0, i)) != null ? map.get(input.substring(0, i)) : diffWaysToCompute(input.substring(0, i));
                List<Integer> right = map.get(input.substring(i+1)) != null ? map.get(input.substring(i+1)) : diffWaysToCompute(input.substring(i+1));
                for(Integer l : left){
                    for(Integer r: right){
                        switch(c){
                            case '+':
                                ways.add(l+r);
                                break;
                            case '-':
                                ways.add(l-r);
                                break;
                            case '*':
                                ways.add(l*r);
                                break;    
                        }
                    }
                }
                map.put(input, ways);
            }
        }
        if(ways.size() == 0){
            ways.add(Integer.valueOf(input));
        }

        return ways;
    }
}
