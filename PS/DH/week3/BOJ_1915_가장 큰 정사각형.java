import java.io.BufferedReader;
import java.util.StringTokenizer;

public class Main {
    static int[][] dp;
    static int N, M;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new java.io.InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        dp = new int[N+1][M+1];
        for (int i = 1; i <= N; i++) {
            String str = br.readLine();
            for (int j = 1; j <= M; j++) {
                dp[i][j] = str.charAt(j-1) - '0';
            }
        }
        int ans = 0;
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                if (dp[i][j] == 0) continue;
                dp[i][j] = Math.min(dp[i-1][j], Math.min(dp[i][j-1], dp[i-1][j-1]))+1;
                ans = Math.max(ans, dp[i][j]);
            }
        }
        System.out.println(ans * ans);
    }
}
