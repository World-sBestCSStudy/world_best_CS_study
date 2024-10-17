import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N, K, ans;
    static int[] input, diff;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(br.readLine());
        K = Integer.parseInt(br.readLine());

        if (K >= N) {
            System.out.println(0);
            return;
        }

        input = new int[N];
        diff = new int[N - 1];
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < N; i++) {
            input[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(input);

        for (int i = 1; i < N; i++) {
            diff[i - 1] = input[i] - input[i - 1];
        }

        Arrays.sort(diff);

        for (int i = 0; i < N - K; i++) {
            ans += diff[i];
        }

        System.out.println(ans);
    }
}
