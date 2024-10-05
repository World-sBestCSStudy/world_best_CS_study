import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static int ans = 1;
    static int[] input, dp;
    static Deque<Integer> stack = new ArrayDeque<>();
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        input = new int[N];
        dp = new int[N];
        Arrays.fill(dp, 1);
        input[0] = Integer.parseInt(st.nextToken());

        for (int i = 1; i < N; i++) {
            input[i] = Integer.parseInt(st.nextToken());
            for (int j = 0; j < i; j++) {
                if (input[i] > input[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);

                    ans = Math.max(ans, dp[i]);
                }
            }
        }

        int idx = 0;
        for (int i = N - 1; i >= 0; i--) {
            if (dp[i] == ans) {
                idx = i;
                break;
            }
        }

        int value = ans;
        while (true) {
            int num = input[idx];
            stack.push(num);

            if (value == 1)
                break;

            for (int i = idx - 1; i >= 0; i--) {
                if (input[i] < num && dp[i] == value - 1) {
                    value--;
                    idx = i;
                    break;
                }
            }
        }

        sb.append(ans).append("\n");
        for (int i = 0; i < ans; i++) {
            sb.append(stack.pop()).append(" ");
        }

        System.out.println(sb);
    }
}
