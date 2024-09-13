import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_16926_배열돌리기1 {
    static int N, M, R;
    static int[][] map;

    // <-  ^  ->  v
    static int[] dy = {0, -1, 0, 1};
    static int[] dx = {-1, 0, 1, 0};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        for (int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine().trim());
            for (int j = 0; j < M; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < R; i++)
            rotation(map);

        for (int i = 0; i < N; i++){
            for (int j = 0; j < M; j++){
                sb.append(map[i][j]).append(" ");
            }
            sb.append("\n");
        }

        System.out.println(sb);
    }

    static void rotation(int[][] map){
        int count = Math.min(N, M) / 2; // 회전 시켜야하는 횟수
        // 배열을 돌릴 때 뒤에서부터 회전시키면 코너에 있는 숫자를 안전하게 옮길 수 있다!

        int idx = 0;
        while (count > idx){
            int temp = map[idx][idx];

            moveHorizon(map, idx, 0);
            moveVertical(map, idx, 1);
            moveHorizon(map, idx, 2);
            moveVertical(map, idx, 3);

            map[idx+1][idx] = temp;

            idx++;
        }

    }

    static void moveHorizon(int[][] map, int idx, int dir){
        if (dir == 0) {   // <-
            int sx = idx;
            int ex = M - idx - 1;

            while (sx < ex) {
                map[idx][sx] = map[idx][sx+1];
                sx++;
            }
        } else if (dir == 2) {  // ->
            int sx = M - idx - 1;
            int ex = idx;

            while (sx > ex) {
                map[N-idx-1][sx] = map[N-idx-1][sx-1];
                sx--;
            }
        }
    }

    static void moveVertical(int[][] map, int idx, int dir){
        if (dir == 1) {   // ^
            int sy = idx;
            int ey = N - idx - 1;

            while (sy < ey) {
                map[sy][M-idx-1] = map[sy+1][M-idx-1];
                sy++;
            }
        } else if (dir == 3) {  // v
            int sy = N - idx - 1;
            int ey = idx;

            while (sy > ey) {
                map[sy][idx] = map[sy-1][idx];
                sy--;
            }
        }
    }

}