import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static int[] dp;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(br.readLine());
        dp = new int[N + 1];

        if (N % 2 == 1) {
            System.out.println(0);
        } else {
            dp[2] = 3;

            if (N >= 4)
                dp[4] = 11;

            for (int i = 6; i <= N; i += 2) {
                dp[i] = dp[i - 2] * 3 + 2;
                for (int j = 0; j <= i - 4; j += 2) {
                    dp[i] += dp[j] * 2;
                }
            }

            System.out.println(dp[N]);
        }
    }
}
