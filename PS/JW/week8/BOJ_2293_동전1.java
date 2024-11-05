import java.io.*;
import java.util.*;

public class BOJ_2293_동전1 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();
    static int n, k;
    static int[] coin, dp;
    public static void main(String[] args) throws Exception{
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        coin = new int[n];
        for (int i = 0; i < n; i++){
            coin[i] = Integer.parseInt(br.readLine());
        }

        Arrays.sort(coin);
        dp = new int[k+1];
        dp[0] = 1;
//        dp[coin[0]] = 1;  // 이걸 왜 하면 안되지..?
        for (int i = 0; i < n; i++){
            for (int j = coin[i]; j <= k; j++){
                dp[j] = dp[j] + dp[j-coin[i]];  // 이게 Math.max(dp[j], dp[j-coin[i]]+1) 은 왜 아닌거지?
            }
        }
//        System.out.println(Arrays.toString(dp));
        System.out.println(dp[k]);
    }
}
