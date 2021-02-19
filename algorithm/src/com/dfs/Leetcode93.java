package com.dfs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class Leetcode93 {
    public static void exec(){
        String s = "25525511135";
        List<String> result = restoreIpAddresses(s);
        System.out.println("result: "+Arrays.toString(result.toArray()));
    }

    private static List<String> res = new ArrayList<>();

    public static List<String> restoreIpAddresses(String s) {
        if(s.length()<4 || s.length()>12){
            return res;
        }
        backtrack(s, 0, 0, new ArrayDeque<>());
        return res;
    }

    public static void backtrack(String s, int split, int start, Deque<String> path){
        if(s.length() == start){
            if(split == 4){
                res.add(String.join(".", path));
            }
            return;
        }

        int len = s.length() - start;
        // 长度过小，或超出
        if(len < (4-split) || len > 3*(4-split)){
            return;
        }

        for(int i=0 ; i < 3; i++){
            if(i>=len){
                break;
            }
            int num = ipSegment(s, start, start+i);
            if(num < 0){
                return;
            }
            path.addLast(num+"");
            backtrack(s, split+1, start+i+1, path);
            path.removeLast();
        }
    }

    private static int ipSegment(String s, int left, int right){
        int len = right - left + 1;
        if(len> 1 && s.charAt(left) == '0'){
            return -1;
        }

        int num = 0;
        for(int i = left ; i<= right; i++){
            num = num*10 + s.charAt(i)-'0';
        }
        if(num > 255){
            return -1;
        }

        return num;
    }
}
