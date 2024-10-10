import java.util.ArrayDeque;
import java.util.Deque;

public class Main {
    static int n;
    static int ans = 200;
    static int[][] map;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, -1, 0, 1};

    public static void main(String[] args) throws Exception {
        Reader in = new Reader();

        n = in.nextInt();
        map = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                map[i][j] = in.nextInt();
            }
        }

        int left = 0;
        int right = map[0][0];

        while (true) {
            //더 진행해도 0,0을 포함하는 범위가 안 나오는 상황
            if (map[0][0] < left)
                break;

            if (left > right) {
                right++;
            }

            if (bfs(left, right)) {
                ans = Math.min(ans, right - left);
                left++;
            } else {
                if (right == 200)
                    break;

                right++;
            }
        }

        System.out.println(ans);
    }

    static boolean bfs(int left, int right) {
        Deque<int[]> queue = new ArrayDeque<>();
        boolean[][] visit = new boolean[n][n];
        queue.add(new int[]{0, 0});
        visit[0][0] = true;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int cr = cur[0];
            int cc = cur[1];

            for (int dir = 0; dir < 4; dir++) {
                int nr = cr + dr[dir];
                int nc = cc + dc[dir];

                if (canGo(nr, nc) && !visit[nr][nc] && map[nr][nc] >= left && map[nr][nc] <= right) {
                    if (nr == n - 1 && nc == n - 1)
                        return true;

                    visit[nr][nc] = true;
                    queue.add(new int[]{nr, nc});
                }
            }
        }

        return false;
    }

    static boolean canGo(int r, int c) {
        return r >= 0 && r < n && c >= 0 && c < n;
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
