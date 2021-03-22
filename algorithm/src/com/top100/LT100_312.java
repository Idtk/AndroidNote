package com.top100;

public class LT100_312 {

    public static void exec(){
        maxCoins(new int[]{3,1,5,8});
    }

    public static int maxCoins(int[] nums) {
        int n = nums.length;
        int[] val = new int[n+2];
        for (int i=1; i<=n; i++){
            val[i] = nums[i-1];
        }
        val[0] = val[n+1] = 1;
        int[][] res = new int[n+2][n+2];
        for (int i=n-1;i>=0;i--){// 长度
            for (int j=i+2;j<n+2;j++){ // 始末位置
                System.out.println("res: "+i+":"+j);
                for (int k=i+1;k<j;k++){// 选择的点
                    int sum = val[i]*val[k]*val[j];
                    sum += res[i][k]+res[k][j];
                    res[i][j] = Math.max(sum,res[i][j]);
                }
            }
        }
        // for (int i=0;i<n;i++){// 长度
        //     for (int j=2+i;j<n+2;j++){ // 始末位置
        //         System.out.println("res: "+i+":"+j);
        //         int max = 0;
        //         for (int k=i+1;k<j;k++){// 选择的点
        //             int sum = val[i]*val[k]*val[j];
        //             sum += res[i][k]+res[k][j];
        //             max = Math.max(sum,max);
        //         }
        //         res[i][j] = max;
        //     }
        // }
        return res[0][n+1];
    }
}
