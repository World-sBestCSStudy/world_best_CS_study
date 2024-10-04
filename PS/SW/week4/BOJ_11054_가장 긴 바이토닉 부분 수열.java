import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N, ans;
    static int[] input;
    static int[] left;
    static int[] right;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(br.readLine());

        input = new int[N];
        left = new int[N];
        right = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        input[0] = Integer.parseInt(st.nextToken());
        Arrays.fill(left, 1);
        Arrays.fill(right, 1);

        for (int i = 1; i < N; i++) {
            input[i] = Integer.parseInt(st.nextToken());
            for (int j = 0; j < i; j++) {
                if (input[i] > input[j]) {
                    left[i] = Math.max(left[i], left[j] + 1);
                }
            }
        }

        for (int i = N - 2; i >= 0; i--) {
            for (int j = N - 1; j > i; j--) {
                if (input[i] > input[j]) {
                    right[i] = Math.max(right[i], right[j] + 1);
                }
            }
        }

        for (int i = 0; i < N; i++) {
            ans = Math.max(ans, left[i] + right[i] - 1);
        }

        System.out.println(ans);
    }
}
