// BOJ 1520. 내리막 길

import java.io.*;
import java.util.*;

public class boj_1520 {
    static int N, M;
    static int[][] map, dp;
    static int[] dr = {-1, 1, 0, 0}, dc = {0, 0, -1, 1};

    // 항상 높이가 낮은 곳으로만 이동할 수 있다.
    // 왼쪽 위 -> 오른쪽 아래까지 이동할 수 있는 경로의 수
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        dp = new int[N][M];
        for (int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine().trim());
            for (int j = 0; j < M; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
                dp[i][j] = -1;
            }
        }

        System.out.println(dfs(0, 0));
    }

    static int dfs(int r, int c){
        if (r == N-1 && c == M-1){
            return 1;
        }

        if (dp[r][c] != -1)
            return dp[r][c];

        dp[r][c] = 0;

        for (int d = 0; d < 4; d++){
            int nr = r + dr[d];
            int nc = c + dc[d];

            if (nr < 0 || nc < 0 || nr >= N || nc >= M) continue;

            if (map[nr][nc] < map[r][c])
                dp[r][c] += dfs(nr, nc);
        }

        return dp[r][c];
    }
}
