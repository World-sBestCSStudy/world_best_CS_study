import java.io.*;
import java.util.*;

public class BOJ_23563_벽타기 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();
    static int H, W;
    static char[][] input;
    static int[][] map, dist;
    static boolean[][] visit;

    static int sy, sx, ey, ex, INF = Integer.MAX_VALUE;
    static int[] dy = {-1,1,0,0};   // 상하좌우 이동
    static int[] dx = {0,0,-1,1};
    // 한 칸 이동에 1초 소요
    // 벽타고 이동할 경우 0초 소요 -> 빈칸의 상하좌우 중 하나가 벽이면 벽에 인접한 칸
    // 이 칸들을 이동할 때 -> 벽타고 이동한다라고 함
    public static void main(String[] args) throws Exception{
        st = new StringTokenizer(br.readLine());
        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());

        input = new char[H][W];
        map = new int[H][W];
        visit = new boolean[H][W];
        dist = new int[H][W];
        for (int i = 0; i < H; i++){
            String str = br.readLine();
            for (int j = 0; j < W; j++){
                input[i][j] = str.charAt(j);    // #: 벽, .: 빈칸, S: 시작점, E: 끝점
                if (input[i][j] == 'S'){
                    sy = i;
                    sx = j;
                } else if (input[i][j] == 'E'){
                    ey = i;
                    ex = j;
                }
            }
        }

        // 벽에 인접한 칸을 찾아서 표시
        match();

        for (int i = 0; i < H; i++){
            Arrays.fill(dist[i], INF);
        }

        // 시작점부터 DFS로 도착점까지 탐색 후 최솟값 갱신 -> StackOverflowError
        dijkstra();

        System.out.println(dist[ey][ex]);
    }

    static void dijkstra(){
        PriorityQueue<Pos> pq = new PriorityQueue<>((p1, p2) -> p1.time - p2.time);
        dist[sy][sx] = 0;
        pq.offer(new Pos(sy, sx, 0));

        while (!pq.isEmpty()){
            Pos cur = pq.poll();

            if (visit[cur.y][cur.x]) continue;
            visit[cur.y][cur.x] = true;

            for (int d = 0; d < 4; d++){
                int ny = cur.y + dy[d];
                int nx = cur.x + dx[d];

                if (ny < 0 ||  ny >= H || nx < 0 || nx >= W || visit[ny][nx] || map[ny][nx] == -1) continue;

                int t = 1;
                if (map[cur.y][cur.x] == 1 && map[ny][nx] == 1){
                    t = 0;
                }

                if (dist[ny][nx] > dist[cur.y][cur.x] + t){
                    dist[ny][nx] = dist[cur.y][cur.x] + t;
                    pq.offer(new Pos(ny, nx, cur.time + t));    // 왜 cur.time + t 인지?? 왜 걍 t가 아닌거임
                }
            }
        }
    }

    static class Pos{
        int y, x, time;
        public Pos(int y, int x, int time){
            this.y = y;
            this.x = x;
            this.time = time;
        }

    }

    static void match(){
        for (int i = 0; i < H; i++){
            if (i == 0 || i == H-1){
                Arrays.fill(map[i], -1);
                continue;
            }

            for (int j = 0; j < W; j++){
                if (input[i][j] == '#')
                    map[i][j] = -1; // 벽
                else if (isNextWall(i,j)){
                    map[i][j] = 1;  // 벽과 인접한 칸
                } else {
                    map[i][j] = 0;  // 빈칸
                }
            }
        }
    }

    static boolean isNextWall(int y, int x){
        for (int d = 0; d < 4; d++){
            int ny = y + dy[d];
            int nx = x + dx[d];

            if (ny >= 0 &&  ny < H && nx >= 0 && nx < W && input[ny][nx] == '#')
                return true;
        }

        return false;
    }
}