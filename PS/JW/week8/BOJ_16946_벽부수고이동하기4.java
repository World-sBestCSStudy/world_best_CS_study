import java.io.*;
import java.util.*;

public class BOJ_16946_벽부수고이동하기4 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();
    static int N, M;
    static int[][] map;
    static int[] dy = {-1, 1, 0, 0};    //상하좌우
    static int[] dx = {0, 0, -1, 1};

    public static void main(String[] args) throws Exception{
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        for (int i = 0; i < N; i++){
            String str = br.readLine();
            for (int j = 0; j < M; j++){
                map[i][j] = str.charAt(j)-'0';
            }
        }

        int idx = -1;
        for (int i = 0; i < N; i++){
            for (int j = 0; j < M; j++){
                if (map[i][j] == 0){    // 0인 칸들 위주로
                    count(i, j, map[i][j]);
                }
            }
        }

        for (int i = 0; i < N; i++){
            for (int j = 0; j < M; j++){
                sb.append(map[i][j] > 0 ? map[i][j] % 10 : 0);
            }
            sb.append("\n");
        }

        System.out.print(sb);
    }

    static void count(int y, int x, int val){
        int cnt = 1;
        PriorityQueue<Pos> pq = new PriorityQueue<>((p1, p2) -> p1.val - p2.val);
        pq.offer(new Pos(y, x, val));
        map[y][x] = -1;
//      y,x 가 !=0 기준 -> 1000 * 1000 * 1000 마다 visit를 초기화하면 시간 복잡도 문제가 없는지..?

        while (!pq.isEmpty()){
            Pos cur = pq.poll();

            if (cur.val != 0){
                map[cur.y][cur.x] = cur.val + cnt;  // 여기서 += cnt 했더니 값의 변경이 있어서 그런지 오류있었음
                continue;
            }

            for (int d = 0; d < 4; d++){
                int ny = cur.y + dy[d];
                int nx = cur.x + dx[d];

                if (ny < 0 || ny >= N || nx < 0 || nx >= M) continue;

                if (map[ny][nx] == 0){
                    pq.offer(new Pos(ny, nx, map[ny][nx]));
                    map[ny][nx] = -1;
                    cnt++;
                } else if (map[ny][nx] > 0){
                    pq.offer(new Pos(ny, nx, map[ny][nx]));
                }
            }
        }
    }

    static class Pos{
        int y, x, val;
        public Pos(int y, int x, int val){
            this.y = y;
            this.x = x;
            this.val = val;
        }
    }
}
