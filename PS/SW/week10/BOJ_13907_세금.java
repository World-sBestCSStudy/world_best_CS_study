import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Main {
    static int N, M, K, S, D, first = Integer.MAX_VALUE;
    static List<int[]>[] adlist;
    static int[][] dp;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {
        Reader in = new Reader();
        N = in.nextInt();
        M = in.nextInt();
        K = in.nextInt();

        adlist = new List[N + 1];
        dp = new int[N + 1][N];
        for (int i = 1; i <= N; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE / 2);
        }

        for (int i = 1; i <= N; i++) {
            adlist[i] = new ArrayList<>();
        }

        S = in.nextInt();
        D = in.nextInt();

        for (int i = 0; i < M; i++) {
            int from = in.nextInt();
            int to = in.nextInt();
            int fee = in.nextInt();

            adlist[from].add(new int[]{to, fee});
            adlist[to].add(new int[]{from, fee});
        }

        dijk();

        sb.append(dp[D][first]).append("\n");

        for (int i = 0; i < K; i++) {
            int plus = in.nextInt();
            //passNode, value
            int[] min = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};

            for (int j = 1; j <= first; j++) {
                dp[D][j] += plus * j;

                if (dp[D][j] < min[1]) {
                    min[0] = j;
                    min[1] = dp[D][j];
                }
            }

            first = min[0];
            sb.append(dp[D][first]).append("\n");
        }

        System.out.println(sb);
    }

    static void dijk() {
        //to, passnode,weight
        PriorityQueue<int[]> queue = new PriorityQueue<>((e1, e2) -> e1[2] - e2[2]);
        queue.add(new int[]{S, 0, 0});
        dp[S][0] = 0;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int node = cur[0];
            int passNode = cur[1];

            if (node == D) {
                if (first == Integer.MAX_VALUE)
                    first = passNode;
                continue;
            }

            if (passNode >= first || passNode >= N - 1)
                continue;

            for (int[] next : adlist[node]) {
                if (passNode + 1 >= first)
                    continue;

                if (dp[next[0]][passNode + 1] > dp[node][passNode] + next[1]) {
                    queue.add(new int[]{next[0], passNode + 1, dp[node][passNode] + next[1]});
                    dp[next[0]][passNode + 1] = dp[node][passNode] + next[1];
                }
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
