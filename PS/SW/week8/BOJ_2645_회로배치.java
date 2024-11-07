import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Main {
    static int n;
    static int[] start = new int[2];
    static int[] end = new int[2];
    static int k;
    static int m;
    static List<int[]>[] routing;
    static int[][] map;
    static int[][] visit;
    static List<int[]> route = new ArrayList<>();
    static StringBuilder sb = new StringBuilder();
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, -1, 0, 1};

    public static void main(String[] args) throws Exception {
        Reader in = new Reader();

        n = in.nextInt();
        start[0] = in.nextInt();
        start[1] = in.nextInt();
        end[0] = in.nextInt();
        end[1] = in.nextInt();
        k = in.nextInt();
        m = in.nextInt();

        map = new int[n + 1][n + 1];
        visit = new int[n + 1][n + 1];
        routing = new List[m];

        for (int i = 1; i <= n; i++) {
            Arrays.fill(map[i], 1);
        }

        for (int i = 0; i < m; i++) {
            routing[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            int count = in.nextInt();
            for (int j = 0; j < count; j++) {
                int r = in.nextInt();
                int c = in.nextInt();
                routing[i].add(new int[]{r, c});
            }
        }

        preInstall();
        dijk();
        tracking();

        System.out.println(sb);
    }

    static void preInstall() {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < routing[i].size() - 1; j++) {
                int[] cur = routing[i].get(j);
                int[] next = routing[i].get(j + 1);

                int dr = next[0] - cur[0];
                dr = dr != 0 ? dr > 0 ? 1 : -1 : 0;

                int dc = next[1] - cur[1];
                dc = dc != 0 ? dc > 0 ? 1 : -1 : 0;

                while (true) {
                    map[cur[0]][cur[1]] = k;

                    if (cur[0] == next[0] && cur[1] == next[1])
                        break;

                    cur[0] += dr;
                    cur[1] += dc;
                }
            }
        }
    }

    static void dijk() {
        //preR,preC,curR,curC,distance
        PriorityQueue<int[]> pq = new PriorityQueue<>((e1, e2) -> e1[4] - e2[4]);
        pq.add(new int[]{500, 500, start[0], start[1], map[start[0]][start[1]]});

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int pr = cur[0];
            int pc = cur[1];
            int cr = cur[2];
            int cc = cur[3];
            int dis = cur[4];

            if (visit[cr][cc] != 0)
                continue;

            visit[cr][cc] = (pr - 1) * n + pc;

            if (cr == end[0] && cc == end[1]) {
                sb.append(dis).append("\n");
                return;
            }

            for (int dir = 0; dir < 4; dir++) {
                int nr = cr + dr[dir];
                int nc = cc + dc[dir];

                if (canGo(nr, nc)) {
                    pq.add(new int[]{cr, cc, nr, nc, dis + map[nr][nc]});
                }
            }
        }
    }

    static boolean canGo(int r, int c) {
        return r >= 1 && r <= n && c >= 1 && c <= n && visit[r][c] == 0;
    }

    static void tracking() {
        int cr = end[0];
        int cc = end[1];
        int pr = (visit[cr][cc] - 1) / n + 1;
        int pc = (visit[cr][cc] - 1) % n + 1;

        int dir = cr - pr;

        while (true) {
            cr = pr;
            cc = pc;

            pr = (visit[cr][cc] - 1) / n + 1;
            pc = (visit[cr][cc] - 1) % n + 1;

            if (dir != cr - pr) {
                route.add(new int[]{cr, cc});
                dir = cr - pr;
            }

            if (pr == start[0] && pc == start[1])
                break;
        }

        sb.append(2 + route.size()).append(" ").append(start[0]).append(" ").append(start[1]).append(" ");

        for (int i = route.size() - 1; i >= 0; i--) {
            sb.append(route.get(i)[0]).append(" ").append(route.get(i)[1]).append(" ");
        }

        sb.append(end[0]).append(" ").append(end[1]).append(" ");
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
