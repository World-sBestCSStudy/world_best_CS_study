import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Main {
    static int n, m, k, max;
    static List<int[]>[] adlist;
    static List<Integer>[] routes;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {
        Reader in = new Reader();
        n = in.nextInt();
        m = in.nextInt();
        k = in.nextInt();

        adlist = new List[n + 1];
        routes = new List[n + 1];

        for (int i = 1; i <= n; i++) {
            adlist[i] = new ArrayList<>();
            routes[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            int from = in.nextInt();
            int to = in.nextInt();
            int time = in.nextInt();

            adlist[from].add(new int[]{to, time});
        }

        dijk();

        for (int i = 1; i <= n; i++) {
            if (routes[i].size() < k)
                sb.append(-1).append("\n");
            else sb.append(routes[i].get(k - 1)).append("\n");
        }

        System.out.println(sb);
    }

    static void dijk() {
        //node, time
        PriorityQueue<int[]> pq = new PriorityQueue<>((e1, e2) -> e1[1] - e2[1]);
        pq.add(new int[]{1, 0});

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int node = cur[0];
            int time = cur[1];

            if (routes[node].size() == k)
                continue;

            routes[node].add(time);

            for (int[] next : adlist[node]) {
                if (routes[next[0]].size() < k)
                    pq.add(new int[]{next[0], time + next[1]});
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
