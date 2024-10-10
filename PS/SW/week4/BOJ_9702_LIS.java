import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int T, N;
    static StringBuilder sb = new StringBuilder();
    static int[] LIS = new int[500];
    static int[] input;

    public static void main(String[] args) throws Exception {
        T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());
            input = new int[N];
            int ans = 0;

            for (int i = 0; i < N; i++) {
                input[i] = Integer.parseInt(br.readLine());
            }

            for (int start = 0; start < N; start++) {
                ans += lis(start);
            }

            System.out.printf("Case #%d: %d\n", t, ans);
        }
    }

    static int lis(int start) {
        Arrays.fill(LIS, Integer.MAX_VALUE);
        LIS[0] = input[start];
        int idx = 1;
        int temp = 1;

        for (int i = start + 1; i < N; i++) {
            int location = Arrays.binarySearch(LIS, input[i]);

            if (idx == -1 * (location + 1)) {
                LIS[idx++] = input[i];
            } else {
                LIS[-1 * (location + 1)] = input[i];
            }
            temp += idx;
        }
        return temp;
    }
}
