package com.sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Leetcode451 {
    public static void exec(){
        String str = "tree";
        String result = frequencySort(str);
        System.out.println("result: "+result);
    }

    public static String frequencySort(String s) {
        Map<Character,Integer> map = new HashMap<>();
        char[] chars = s.toCharArray();
        for(char c:chars){
            map.put(c, map.getOrDefault(c, 0)+1);
        }
        ArrayList<Character>[] charList = new ArrayList[s.length()+1]; 
        for(char c:map.keySet()){
            int num = map.get(c);
            if(charList[num] == null){
                charList[num] = new ArrayList<>();
            }
            charList[num].add(c);
        }
        StringBuilder sb = new StringBuilder();
        for(int i = s.length(); i>=0 ; i--){
            ArrayList<Character> cArray = charList[i];
            if(cArray == null){
                continue;
            }
            for(char c:cArray){
                for(int j=0; j< i;j++){
                    sb.append(c);
                }
            }
        }
        
        return sb.toString();
    }
}
