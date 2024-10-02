import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int R, C, ans;
    static char[][] map;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, -1, 0, 1};

    public static void main(String[] args) throws Exception {
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        map = new char[R][C];
        for (int i = 0; i < R; i++) {
            map[i] = br.readLine().toCharArray();
        }

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (map[i][j] == 'L')
                    bfs(i, j);
            }
        }

        System.out.println(ans);
    }

    static void bfs(int r, int c) {
        boolean[][] visit = new boolean[R][C];
        Deque<int[]> queue = new ArrayDeque<>();

        visit[r][c] = true;
        queue.add(new int[]{r, c});
        int depth = -1;

        while (!queue.isEmpty()) {
            int size = queue.size();
            depth++;
            for (int i = 0; i < size; i++) {
                int[] cur = queue.poll();
                int cr = cur[0];
                int cc = cur[1];

                for (int dir = 0; dir < 4; dir++) {
                    int nr = cr + dr[dir];
                    int nc = cc + dc[dir];

                    if (canGo(nr, nc) && !visit[nr][nc]) {
                        visit[nr][nc] = true;
                        queue.add(new int[]{nr, nc});
                    }
                }
            }
        }
        ans = Math.max(ans, depth);
    }

    static boolean canGo(int r, int c) {
        return r >= 0 && r < R && c >= 0 && c < C && map[r][c] == 'L';
    }
}
