import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class Main {
    static int T, N;
    static StringBuilder sb = new StringBuilder();
    static Deque<Integer> stack = new ArrayDeque<>();
    static int[] LIS, memo, input;

    public static void main(String[] args) throws Exception {
        Reader in = new Reader();

        T = in.nextInt();
        for (int t = 0; t < T; t++) {
            N = in.nextInt();
            int idx = 0;
            LIS = new int[N];
            memo = new int[N];
            input = new int[N];
            Arrays.fill(LIS, Integer.MAX_VALUE);

            for (int i = 0; i < N; i++) {
                input[i] = in.nextInt();
                int location = Arrays.binarySearch(LIS, input[i]);

                if (location >= 0) {
                    memo[i] = location + 1;
                    continue;
                }

                if (idx == -1 * (location + 1)) {
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
                    stack.push(i + 1);
                    temp--;
                    value = input[i];
                    if (temp == 0)
                        break;
                }
            }

            sb.append(idx).append("\n");
            for (int i = 0; i < idx; i++) {
                sb.append(stack.pop()).append(" ");
            }
            sb.append("\n");
        }

        System.out.println(sb);
    }

    static class Reader {
        final int SIZE = 1 << 13;
        byte[] buffer = new byte[SIZE];
        int index, size;

        int nextInt() throws Exception {
            int n = 0;
            byte c;
            boolean isMinus = false;
            while ((c = read()) <= 32) {
                if (size < 0) return -1;
            }
            if (c == 45) {
                c = read();
                isMinus = true;
            }
            do n = (n << 3) + (n << 1) + (c & 15);
            while (isNumber(c = read()));
            return isMinus ? ~n + 1 : n;
        }

        boolean isNumber(byte c) {
            return 47 < c && c < 58;
        }

        byte read() throws Exception {
            if (index == size) {
                size = System.in.read(buffer, index = 0, SIZE);
                if (size < 0) buffer[0] = -1;
            }
            return buffer[index++];
        }
    }
}
