package com.greedy;

import java.util.ArrayList;
import java.util.List;

public class Leetcode763 {
    public static void exec(){
        String S = "ababcbacadefegdehijhklij";
        List<Integer> result = partitionLabels(S);
        System.out.println("result: "+result.toString());
    }

    public static List<Integer> partitionLabels(String s) {
        int[] last = new int[26];
        for(int i=0;i<s.length();i++){
            last[s.charAt(i)-'a'] = i;
        }
        List<Integer> part = new ArrayList<>();
        int start = 0, end =0;
        for(int i=0;i<s.length();i++){
            end = Math.max(end, last[s.charAt(i) - 'a']);
            if(i == end){
                part.add(end-start+1);
                start = end+1;
            }
        }
        return part;
    }
}
