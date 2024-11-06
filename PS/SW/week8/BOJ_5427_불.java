import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();
    static int T, w, h;
    static char[][] map;
    static boolean[][] visit;
    static Deque<int[]> fireQ = new ArrayDeque<>();
    static Deque<int[]> personQ = new ArrayDeque<>();
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, -1, 0, 1};

    public static void main(String[] args) throws Exception {
        T = Integer.parseInt(br.readLine());
        for (int t = 0; t < T; t++) {
            st = new StringTokenizer(br.readLine());
            w = Integer.parseInt(st.nextToken());
            h = Integer.parseInt(st.nextToken());

            map = new char[h][w];
            visit = new boolean[h][w];

            //맵 입력
            for (int i = 0; i < h; i++) {
                String s = br.readLine();
                for (int j = 0; j < w; j++) {
                    map[i][j] = s.charAt(j);

                    if (map[i][j] == '@') {
                        visit[i][j] = true;
                        personQ.add(new int[]{i, j});
                    }

                    if (map[i][j] == '*')
                        fireQ.add(new int[]{i, j});
                }
            }

            bfs();

            fireQ.clear();
            personQ.clear();
        }
        System.out.println(sb);
    }

    static void bfs() {
        int time = 1;

        while (!personQ.isEmpty()) {
            int fqSize = fireQ.size();
            int pqSize = personQ.size();

            for (int i = 0; i < fqSize; i++) {
                int[] cur = fireQ.poll();
                int cr = cur[0];
                int cc = cur[1];

                for (int dir = 0; dir < 4; dir++) {
                    int nr = cr + dr[dir];
                    int nc = cc + dc[dir];

                    if (!outRange(nr, nc) && map[nr][nc] != '#' && map[nr][nc] != '*') {
                        map[nr][nc] = '*';
                        fireQ.add(new int[]{nr, nc});
                    }
                }
            }

            for (int i = 0; i < pqSize; i++) {
                int[] cur = personQ.poll();
                int cr = cur[0];
                int cc = cur[1];

                for (int dir = 0; dir < 4; dir++) {
                    int nr = cr + dr[dir];
                    int nc = cc + dc[dir];

                    if (outRange(nr, nc)) {
                        sb.append(time).append("\n");
                        return;
                    } else {
                        if (!visit[nr][nc] && map[nr][nc] == '.') {
                            visit[nr][nc] = true;
                            personQ.add(new int[]{nr, nc});
                        }
                    }
                }
            }

            time++;
        }
        sb.append("IMPOSSIBLE").append("\n");
    }

    static boolean outRange(int r, int c) {
        return r < 0 || r >= h || c < 0 || c >= w;
    }
}
