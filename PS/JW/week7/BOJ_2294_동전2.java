import java.io.*;
import java.util.*;

public class BOJ_2294_동전2 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception{
        st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());   // 1~100
        int k = Integer.parseInt(st.nextToken());   // 1~10000

        int[] coin = new int[n];
        for (int i = 0; i < n; i++){
            coin[i] = Integer.parseInt(br.readLine());
        }
        Arrays.sort(coin);

        int[] dp = new int[k+1];
        Arrays.fill(dp, 10001);
        dp[0] = 0;
        for (int i = 0; i < n; i++){
            for (int j = coin[i]; j <= k; j++){
                dp[j] = Math.min(dp[j], dp[j - coin[i]] + 1);
            }
        }

        System.out.println(dp[k] == 10001 ? -1 : dp[k]);
    }
}