import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Main {
    static int N, M, K, cnt;
    static List<int[]>[] adlist;
    static PriorityQueue<long[]> pq = new PriorityQueue<>((e1, e2) -> e1[1] == e2[1] ? Long.compare(e2[0], e1[0]) : Long.compare(e1[1], e2[1]));
    static boolean[] visit;

    public static void main(String[] args) throws Exception {
        Reader in = new Reader();
        N = in.nextInt();
        M = in.nextInt();
        K = in.nextInt();

        adlist = new List[N + 1];
        visit = new boolean[N + 1];
        for (int i = 1; i <= N; i++) {
            adlist[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            int from = in.nextInt();
            int to = in.nextInt();
            int dis = in.nextInt();

            adlist[to].add(new int[]{from, dis});
        }

        for (int i = 0; i < K; i++) {
            pq.add(new long[]{in.nextInt(), 0});
        }

        dijk();
    }

    static void dijk() {
        while (!pq.isEmpty()) {
            long[] cur = pq.poll();
            int node = (int) cur[0];
            long dis = cur[1];

            if (visit[node])
                continue;

            visit[node] = true;
            cnt++;
            if (cnt == N) {
                System.out.println(node);
                System.out.println(dis);
                return;
            }

            for (int[] next : adlist[node]) {
                if (!visit[next[0]])
                    pq.add(new long[]{next[0], dis + next[1]});
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
