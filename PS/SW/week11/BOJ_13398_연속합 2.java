import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int n, ans;
    static int[] num;
    static int[][] dp;

    public static void main(String[] args) throws Exception {
        n = Integer.parseInt(br.readLine());

        num = new int[n];
        dp = new int[n][2];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            num[i] = Integer.parseInt(st.nextToken());
        }

        dp[0][0] = num[0];

        ans = num[0];

        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i - 1][0] + num[i], num[i]);
            dp[i][1] = Math.max(dp[i - 1][0], dp[i - 1][1] + num[i]);

            ans = Math.max(ans, dp[i][0]);
            ans = Math.max(ans, dp[i][1]);
        }

        System.out.println(ans);
    }
}
