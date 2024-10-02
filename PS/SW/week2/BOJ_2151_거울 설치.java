import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

//거울을 45도로 설치하면 위,아래 방향은 좌,우로, 좌,우 방향은 위,아래 방향으로 전환
//거울을 설치하여 방향을 변경하거나 설치하지 않고 방향을 유지하는 식으로 탐색해서
//한 쪽 문에서 다른 쪽 문으로 거울을 최소한으로 설치하고 도착할 때를 찾아냄
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static char[][] map;
    static int[][] door = new int[2][2];
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, -1, 0, 1};

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(br.readLine());
        map = new char[N][N];

        for (int i = 0; i < N; i++) {
            map[i] = br.readLine().toCharArray();
        }

        int idx = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j] == '#') {
                    door[idx][0] = i;
                    door[idx++][1] = j;
                }
            }
        }

        System.out.println(dijk());
    }

    static int dijk() {
        //r,c,방향,거울 설치 개수
        PriorityQueue<int[]> pq = new PriorityQueue<>((e1, e2) -> e1[3] - e2[3]);

        for (int dir = 0; dir < 4; dir++) {
            int nr = door[0][0] + dr[dir];
            int nc = door[0][1] + dc[dir];

            //문에서 빛이 나갈 수 있는 방향을 찾기
            if (canGo(nr, nc)) {
                pq.add(new int[]{door[0][0], door[0][1], dir, 0});
            }
        }

        //현재 빛의 방향을 고려한 방문 배열;
        boolean[][][] visit = new boolean[N][N][4];

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int cr = cur[0];
            int cc = cur[1];
            int cd = cur[2];
            int mirror = cur[3];

            if (visit[cr][cc][cd])
                continue;

            if (cr == door[1][0] && cc == door[1][1])
                return mirror;

            visit[cr][cc][cd] = true;

            int nr = cr + dr[cd];
            int nc = cc + dc[cd];
            if (canGo(nr, nc)) {
                if (!visit[nr][nc][cd])
                    pq.add(new int[]{nr, nc, cd, mirror});

                if (map[nr][nc] == '!') {
                    //위,아래에서 좌,우로
                    if (cd == 0 || cd == 2) {
                        if (!visit[nr][nc][1])
                            pq.add(new int[]{nr, nc, 1, mirror + 1});
                        if (!visit[nr][nc][3])
                            pq.add(new int[]{nr, nc, 3, mirror + 1});
                    }

                    //좌,우에서 위,아래로
                    if (cd == 1 || cd == 3) {
                        if (!visit[nr][nc][0])
                            pq.add(new int[]{nr, nc, 0, mirror + 1});
                        if (!visit[nr][nc][2])
                            pq.add(new int[]{nr, nc, 2, mirror + 1});
                    }
                }
            }
        }
        return -1;
    }

    static boolean canGo(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < N && map[r][c] != '*';
    }
}
