import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Queue;

public class Main {
    static StringBuilder sb = new StringBuilder();
    static int T, a, b;
    static int[] check = new int[10000];
    static int[] before = new int[10000];
    static char[] cmd = {'D', 'S', 'L', 'R'};
    static Queue<Integer> queue = new ArrayDeque<>();
    static Deque<Integer> stack = new ArrayDeque<>();

    public static void main(String[] args) throws Exception {
        Reader in = new Reader();
        T = in.nextInt();


        for (int i = 0; i < T; i++) {
            a = in.nextInt();
            b = in.nextInt();
            Arrays.fill(before, -1);
            Arrays.fill(check, -1);

            bfs();
        }

        System.out.println(sb);
    }

    static void bfs() {
        queue.clear();
        queue.add(a);

        while (!queue.isEmpty()) {
            int cur = queue.poll();

            int D = cur * 2 % 10000;
            int S = cur - 1 == -1 ? 9999 : cur - 1;
            int L = L(cur);
            int R = R(cur);

            if (D != a && before[D] == -1) {
                before[D] = cur;
                check[D] = 0;

                if (D == b) {
                    result();
                    return;
                }

                queue.add(D);
            }

            if (S != a && before[S] == -1) {
                before[S] = cur;
                check[S] = 1;

                if (S == b) {
                    result();
                    return;
                }
                queue.add(S);
            }

            if (L != a && before[L] == -1) {
                before[L] = cur;
                check[L] = 2;

                if (L == b) {
                    result();
                    return;
                }
                queue.add(L);
            }

            if (R != a && before[R] == -1) {
                before[R] = cur;
                check[R] = 3;

                if (R == b) {
                    result();
                    return;
                }
                queue.add(R);
            }
        }
    }

    static int L(int num) {
        int first = num % 1000 * 10;
        int second = num / 1000;

        return first + second;
    }

    static int R(int num) {
        int first = num % 10 * 1000;
        int second = num / 10;

        return first + second;
    }

    static void result() {
        int temp = b;
        while (temp != a) {
            stack.push(check[temp]);

            temp = before[temp];
        }

        while (!stack.isEmpty()) {
            sb.append(cmd[stack.pop()]);
        }

        sb.append("\n");
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
