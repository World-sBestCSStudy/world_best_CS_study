// BOJ 10026. 적록색약

import java.io.*;
import java.util.*;

// ** BFS는 Queue에 넣을 때 방문체크를 해야 중복발생 X
// BFS는 방문 표시를 큐에서 뺀 다음이 아니라, 큐에 넣을 때 해야 중복 방문이 일어나지 않습니다.
// 두 칸에서 동시에 어떤 칸에 방문하려고 하면, 그 칸이 두 번 큐에 들어가게 됩니다.
// 큐에서 나온 후에 방문 체크를 해도, 이미 들어간 칸은 없어지지 않고 똑같은 칸을 두 번 처리하게 됩니다.
public class boj_10026 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int N, R, G, B, ansO, ansX;
    static char[][] map;
    static boolean[][] visit;
    static int[] dy = {-1, 1, 0, 0};
    static int[] dx = {0, 0, -1, 1};
    static Queue<Pos> queue = new ArrayDeque<>();

    public static void main(String[] args) throws Exception {
        // 입력
        N = Integer.parseInt(br.readLine());
        map = new char[N][N];
        for (int i = 0; i < N; i++) {
            map[i] = br.readLine().toCharArray();
        }

        visit = new boolean[N][N];
        ansX = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (visit[i][j]) continue;
                bfsX(map[i][j], i, j);
                ansX++;
            }
        }

        for (int i = 0; i < N; i++){
            Arrays.fill(visit[i], false);
        }

        ansO = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (visit[i][j]) continue;
                if (map[i][j] == 'B')
                    bfsX(map[i][j], i, j);
                else bfsO(map[i][j], i, j);
                ansO++;
            }
        }

        System.out.println(ansX + " " + ansO);
    }

    static class Pos{
        int y, x;

        private Pos(int y, int x){
            this.y = y;
            this.x = x;
        }
    }

    static void bfsX(char color, int y, int x){
        queue.add(new Pos(y, x));
        visit[y][x] = true;

        while (!queue.isEmpty()){
            // 갈 수 있는 곳들을 queue에 넣기
            Pos p = queue.poll();
            for (int d = 0; d < 4; d++){
                int ny = p.y + dy[d];
                int nx = p.x + dx[d];

                if (ny < 0 || ny >= N || nx < 0 || nx >= N || map[ny][nx] != map[p.y][p.x] || visit[ny][nx])
                    continue;

                queue.offer(new Pos(ny, nx));
                visit[ny][nx] = true;
            }
        }
    }

    static void bfsO(char color, int y, int x){
        // R-G 구분 X
        queue.add(new Pos(y, x));
        visit[y][x] = true;

        while (!queue.isEmpty()){
            // 갈 수 있는 곳들을 queue에 넣기
            Pos p = queue.poll();
            for (int d = 0; d < 4; d++){
                int ny = p.y + dy[d];
                int nx = p.x + dx[d];

                if (ny < 0 || ny >= N || nx < 0 || nx >= N || visit[ny][nx])
                    continue;

                if (map[ny][nx] == 'B')
                    continue;

                queue.offer(new Pos(ny, nx));
                visit[ny][nx] = true;
            }
        }
    }
}
