// BOJ 1463. 1로 만들기

import java.io.*;
import java.util.*;

public class boj_1463 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception{
        int N = Integer.parseInt(br.readLine());

        if (N == 1) {
            System.out.println(0);
            return;
        } else if (N == 2) {
            System.out.println(1);
            return;
        }

        int[] dp = new int[N+1];
        dp[2] = 1;
        dp[3] = 1;

        for (int i = 4; i <= N; i++){
            dp[i] = dp[i-1] + 1;

            if (i % 2 == 0){
                dp[i] = Math.min(dp[i], dp[i/2]+1);
            }
            if (i % 3 == 0){
                dp[i] = Math.min(dp[i], dp[i/3]+1);
            }
        }

        System.out.println(dp[N]);
    }
}