import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int T;
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();
    static int h, w, ans;
    static char[][] map;
    static int[][] person = new int[2][2];
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, -1, 0, 1};
    //0 - 죄수1 , 1 - 죄수2, 3 - 출구에서 특정 지점까지
    static int[][][] memoi;

    public static void main(String[] args) throws Exception {
        T = Integer.parseInt(br.readLine());

        for (int t = 0; t < T; t++) {
            st = new StringTokenizer(br.readLine());
            h = Integer.parseInt(st.nextToken());
            w = Integer.parseInt(st.nextToken());

            ans = Integer.MAX_VALUE;
            map = new char[h][w];
            memoi = new int[h][w][3];
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    for (int k = 0; k < 3; k++) {
                        memoi[i][j][k] = 10000;
                    }
                }
            }

            int idx = 0;

            for (int i = 0; i < h; i++) {
                String s = br.readLine();
                for (int j = 0; j < w; j++) {
                    map[i][j] = s.charAt(j);
                    if (map[i][j] == '$') {
                        person[idx][0] = i;
                        person[idx++][1] = j;
                    }
                }
            }

            dijk();
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    if (map[i][j] != '*') {
                        if (map[i][j] == '#')
                            ans = Math.min(ans, memoi[i][j][0] + memoi[i][j][1] + memoi[i][j][2] - 2);
                        else
                            ans = Math.min(ans, memoi[i][j][0] + memoi[i][j][1] + memoi[i][j][2]);
                    }
                }
            }

            ans = Math.min(ans, memoi[person[0][0]][person[0][1]][2] + memoi[person[1][0]][person[1][1]][2]);

            sb.append(ans).append("\n");
        }
        System.out.println(sb);
    }

    static void dijk() {
        Deque<int[]> queue = new ArrayDeque<>();

        //첫번째 죄수
        queue.add(person[0]);
        memoi[person[0][0]][person[0][1]][0] = 0;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int cr = cur[0];
            int cc = cur[1];

            for (int dir = 0; dir < 4; dir++) {
                int nr = cr + dr[dir];
                int nc = cc + dc[dir];

                if (canGo(nr, nc)) {
                    if (map[nr][nc] == '#') {
                        if (memoi[nr][nc][0] > memoi[cr][cc][0] + 1) {
                            queue.add(new int[]{nr, nc});
                            memoi[nr][nc][0] = memoi[cr][cc][0] + 1;
                        }
                    } else {
                        if (memoi[nr][nc][0] > memoi[cr][cc][0]) {
                            queue.add(new int[]{nr, nc});
                            memoi[nr][nc][0] = memoi[cr][cc][0];
                        }
                    }
                }
            }
        }

        //두번째 죄수
        queue.add(person[1]);
        memoi[person[1][0]][person[1][1]][1] = 0;
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int cr = cur[0];
            int cc = cur[1];

            for (int dir = 0; dir < 4; dir++) {
                int nr = cr + dr[dir];
                int nc = cc + dc[dir];

                if (canGo(nr, nc)) {
                    if (map[nr][nc] == '#') {
                        if (memoi[nr][nc][1] > memoi[cr][cc][1] + 1) {
                            queue.add(new int[]{nr, nc});
                            memoi[nr][nc][1] = memoi[cr][cc][1] + 1;
                        }
                    } else {
                        if (memoi[nr][nc][1] > memoi[cr][cc][1]) {
                            queue.add(new int[]{nr, nc});
                            memoi[nr][nc][1] = memoi[cr][cc][1];
                        }
                    }
                }
            }
        }

        //두 죄수가 만나고 나서 바깥 지점을 향할 때
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if ((i == 0 || j == 0 || i == h - 1 || j == w - 1) && map[i][j] != '*') {
                    queue.add(new int[]{i, j});

                    if (map[i][j] == '#')
                        memoi[i][j][2] = 1;
                    else
                        memoi[i][j][2] = 0;
                }
            }
        }

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int cr = cur[0];
            int cc = cur[1];

            for (int dir = 0; dir < 4; dir++) {
                int nr = cr + dr[dir];
                int nc = cc + dc[dir];

                if (canGo(nr, nc)) {
                    if (map[nr][nc] == '#') {
                        if (memoi[nr][nc][2] > memoi[cr][cc][2] + 1) {
                            queue.add(new int[]{nr, nc});
                            memoi[nr][nc][2] = memoi[cr][cc][2] + 1;
                        }
                    } else {
                        if (memoi[nr][nc][2] > memoi[cr][cc][2]) {
                            queue.add(new int[]{nr, nc});
                            memoi[nr][nc][2] = memoi[cr][cc][2];
                        }
                    }
                }
            }
        }
    }

    static boolean canGo(int r, int c) {
        return r >= 0 && r < h && c >= 0 && c < w && map[r][c] != '*';
    }
}
