import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static boolean[] chk = new boolean[500001];
    static int[] LIS, memo;
    static int N, idx;
    static int[][] input;
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(br.readLine());

        input = new int[N][2];
        LIS = new int[N];
        memo = new int[N];
        Arrays.fill(LIS, Integer.MAX_VALUE);

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            input[i][0] = Integer.parseInt(st.nextToken());
            input[i][1] = Integer.parseInt(st.nextToken());
            chk[input[i][0]] = true;
        }

        Arrays.sort(input, (e1, e2) -> e1[1] - e2[1]);

        for (int i = 0; i < N; i++) {
            int location = Arrays.binarySearch(LIS, input[i][0]);

            if (idx == -1 * (location + 1)) {
                LIS[idx++] = input[i][0];
                memo[i] = idx;
            } else {
                LIS[-1 * (location + 1)] = input[i][0];
                memo[i] = -1 * location;
            }
        }

        int temp = idx;
        int value = Integer.MAX_VALUE;
        for (int i = N - 1; i >= 0; i--) {
            if (memo[i] == temp && input[i][0] < value) {
                chk[input[i][0]] = false;
                temp--;
                value = input[i][0];
                if (temp == 0)
                    break;
            }
        }

        sb.append(N - idx).append("\n");
        for (int i = 0; i <= 500000; i++) {
            if (chk[i])
                sb.append(i).append("\n");
        }

        System.out.println(sb);
    }
}
