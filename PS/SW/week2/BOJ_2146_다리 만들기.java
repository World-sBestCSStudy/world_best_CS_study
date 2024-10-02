import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N, ans = Integer.MAX_VALUE;
    static int[][] map;
    static int[][] land;
    static boolean[][] visit;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, -1, 0, 1};
    static StringTokenizer st;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(br.readLine());

        map = new int[N][N];
        land = new int[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        check();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (land[i][j] != 0) {
                    create(i, j);
                }
            }
        }

        System.out.println(ans);
    }

    static void check() {
        int num = 1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j] == 1 && land[i][j] == 0) {
                    Deque<int[]> queue = new ArrayDeque<>();
                    land[i][j] = num;
                    queue.add(new int[]{i, j});

                    while (!queue.isEmpty()) {
                        int[] cur = queue.poll();
                        int cr = cur[0];
                        int cc = cur[1];

                        for (int dir = 0; dir < 4; dir++) {
                            int nr = cr + dr[dir];
                            int nc = cc + dc[dir];

                            if (canGo(nr, nc) && map[nr][nc] == 1 && land[nr][nc] == 0) {
                                land[nr][nc] = num;
                                queue.add(new int[]{nr, nc});
                            }
                        }
                    }
                    num++;
                }
            }
        }
    }

    static void create(int r, int c) {
        int num = land[r][c];
        Deque<int[]> queue = new ArrayDeque<>();
        visit = new boolean[N][N];
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

                    if (canGo(nr, nc)) {
                        if (map[nr][nc] == 0 && !visit[nr][nc]) {
                            visit[nr][nc] = true;
                            queue.add(new int[]{nr, nc});
                        }

                        if (land[nr][nc] != 0 && land[nr][nc] != num) {
                            ans = Math.min(ans, depth);
                            return;
                        }
                    }
                }
            }
        }
    }

    static boolean canGo(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < N;
    }
}
