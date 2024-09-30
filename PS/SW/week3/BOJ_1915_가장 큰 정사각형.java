import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int n, m, ans;
    static int[][] dp;

    public static void main(String[] args) throws Exception {
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        dp = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            String s = br.readLine();
            for (int j = 1; j <= m; j++) {
                dp[i][j] = s.charAt(j - 1) - '0';

                if (dp[i][j] == 1) {
                    dp[i][j] = min(dp[i - 1][j - 1], dp[i - 1][j], dp[i][j - 1]) + 1;

                    ans = Math.max(ans, dp[i][j]);
                }
            }
        }

        System.out.println(ans * ans);
    }

    static int min(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }
}
