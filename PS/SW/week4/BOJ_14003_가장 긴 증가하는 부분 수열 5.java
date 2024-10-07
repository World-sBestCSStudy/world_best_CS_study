import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static int[] LIS;
    static int[] memo;
    static int[] input;
    static Deque<Integer> stack = new ArrayDeque<>();
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(br.readLine());

        LIS = new int[N];
        memo = new int[N];
        input = new int[N];
        Arrays.fill(LIS, Integer.MAX_VALUE);
        Arrays.fill(memo, Integer.MAX_VALUE);
        StringTokenizer st = new StringTokenizer(br.readLine());

        int idx = 1;
        input[0] = Integer.parseInt(st.nextToken());
        LIS[0] = input[0];
        memo[0] = 1;
        for (int i = 1; i < N; i++) {
            input[i] = Integer.parseInt(st.nextToken());
            int location = Arrays.binarySearch(LIS, input[i]);

            //같은 숫자가 이미 LIS에 있는 경우
            if (location >= 0)
                continue;

            if (location == -1 * (idx + 1)) {
                LIS[idx++] = input[i];
                memo[i] = idx;
            } else {
                LIS[-1 * (location + 1)] = input[i];
                memo[i] = -1 * location;
            }
        }

        int temp = idx;
        int value = Integer.MAX_VALUE;
        for (int i = N - 1; i >= 0; i--) {
            if (memo[i] == temp && input[i] < value) {
                temp--;
                stack.push(input[i]);
                value = input[i];
                if (idx == 0)
                    break;
            }
        }

        sb.append(idx).append("\n");
        for (int i = 0; i < idx; i++) {
            sb.append(stack.pop()).append(" ");
        }
        System.out.println(sb);
    }
}
