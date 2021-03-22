package com.top100;

import java.util.ArrayList;
import java.util.List;

public class LT100_22 {

    public static void exec(){
        List<String> res = generateParenthesis(3);
        System.out.println("res: "+res.toString());
    }

    public static List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        if(n==0){
            return res;
        }

        dfs(n, n, "", res);
        return res;
    }

    public static void dfs(int left, int right, String cur, List<String> res){
        if(left==0 && right==0){
            res.add(cur);
            return;
        }
        if(left > right){
            return;
        }
        if(left>0){
            dfs(left-1, right, cur+"(",res);
        }
        if(right>0){
            dfs(left, right-1, cur+")", res);
        }
    }

}
