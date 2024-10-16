import java.util.ArrayList;
import java.util.List;

public class Main {
    static int N, M;
    static List<int[]>[] adlist;
    static StringBuilder sb = new StringBuilder();
    static boolean[] visit;

    public static void main(String[] args) throws Exception {
        Reader in = new Reader();
        N = in.nextInt();
        M = in.nextInt();

        adlist = new List[N + 1];
        for (int i = 1; i <= N; i++) {
            adlist[i] = new ArrayList<>();
        }

        for (int i = 0; i < N - 1; i++) {
            int from = in.nextInt();
            int to = in.nextInt();
            int dis = in.nextInt();

            adlist[from].add(new int[]{to, dis});
            adlist[to].add(new int[]{from, dis});
        }

        for (int i = 0; i < M; i++) {
            int start = in.nextInt();
            int end = in.nextInt();
            visit = new boolean[N + 1];

            visit[start] = true;
            dfs(start, end, 0);
        }

        System.out.println(sb);
    }

    static void dfs(int node, int end, int sum) {
        if (node == end) {
            sb.append(sum).append("\n");
            return;
        }

        for (int[] next : adlist[node]) {
            if (!visit[next[0]]) {
                visit[next[0]] = true;
                dfs(next[0], end, sum + next[1]);
            }
        }
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
