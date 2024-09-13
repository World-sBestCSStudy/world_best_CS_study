package algorithm2024.sep.day13;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_15989_123_더하기_4 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws  Exception{
        int T = Integer.parseInt(br.readLine());
        for(int t= 0;t<T;t++){
            int n = Integer.parseInt(br.readLine());
            int cnt = 0;
            int[][] dp = new int[10001][4];
            dp[1][1] = 1;
            dp[2][1] = 1;
            dp[2][2] = 1;
            dp[3][1] = 1;
            dp[3][2] = 1;
            dp[3][3] = 1;
            for(int i =4;i<=n;i++){
                dp[i][1] = 1;
                dp[i][2] = dp[i-2][1] + dp[i-2][2];
                dp[i][3] = dp[i-3][1] + dp[i-3][2] + dp[i-3][3];
            }
            cnt = dp[n][1] + dp[n][2] + dp[n][3];
            sb.append(cnt).append("\n");
        }
        System.out.println(sb);
    }

}
