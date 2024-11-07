import java.util.*;

public class Main {
    static int T, N, M, K;
    static List<int[]>[] adlist;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {
        Reader in = new Reader();
        T = in.nextInt();
        for (int t = 0; t < T; t++) {
            N = in.nextInt();
            M = in.nextInt();
            K = in.nextInt();

            adlist = new List[N + 1];
            for (int i = 1; i <= N; i++) {
                adlist[i] = new ArrayList<>();
            }

            for (int i = 0; i < K; i++) {
                int from = in.nextInt();
                int to = in.nextInt();
                int cost = in.nextInt();
                int time = in.nextInt();

                adlist[from].add(new int[]{to, cost, time});
            }

            for (int i = 1; i <= N; i++) {
                adlist[i].sort((e1, e2) -> e1[2] - e2[2]);
            }

            dijk();
        }

        System.out.println(sb);
    }

    static void dijk() {
        Deque<int[]> queue = new ArrayDeque<>();
        int ans = Integer.MAX_VALUE / 2;
        int[][] dp = new int[N + 1][M + 1];
        for (int i = 1; i <= N; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE / 2);
        }

        dp[1][0] = 0;
        queue.add(new int[]{1, 0, 0});

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int node = cur[0];
            int cost = cur[1];
            int time = cur[2];

            if (time > dp[node][cost])
                continue;

            for (int[] next : adlist[node]) {
                int nextTime = dp[node][cost] + next[2];
                int nextCost = cost + next[1];
                if (nextTime > ans)
                    continue;

                if (nextCost <= M && dp[next[0]][cost + next[1]] > nextTime) {
                    for (int i = nextCost; i <= M; i++) {
                        if (dp[next[0]][i] <= nextTime)
                            break;

                        dp[next[0]][i] = nextTime;
                    }
                    queue.add(new int[]{next[0], cost + next[1], nextTime});

                    if (next[0] == N)
                        ans = nextTime;
                }
            }
        }

        sb.append(ans == Integer.MAX_VALUE / 2 ? "Poor KCM" : ans).append("\n");
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
