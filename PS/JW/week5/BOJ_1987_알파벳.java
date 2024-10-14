// BOJ 1987. 알파벳

import java.io.*;
import java.util.*;

public class BOJ_1987_알파벳 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int R, C, ans;
    static char[][] map;

    static boolean[] alphavet = new boolean[26];
    static boolean[][] visited;
    static int[] dr = {-1,0,1,0};   // ^ > v <
    static int[] dc = {0,1,0,-1};

    public static void main(String[] args) throws Exception{
        st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        map = new char[R][C];
        for (int r = 0; r < R; r++){
            map[r] = br.readLine().toCharArray();
        }

        visited = new boolean[R][C];
        ans = 1;
        visited[0][0] = true;
        alphavet[map[0][0]-'A'] = true;
        dfs(0,0, 1);

        System.out.println(ans);
    }

    static void dfs(int r, int c, int cnt){
        // 종료조건
        ans = Math.max(ans, cnt);

        for (int d = 0; d < 4; d++){
            int nr = r + dr[d];
            int nc = c + dc[d];

            // 범위 밖 || 방문했거나 || 이미 방문한 알파벳 -> continue
            if (nr < 0 || nr >= R || nc < 0 || nc >= C || visited[nr][nc] || alphavet[map[nr][nc]-'A']) continue;

            visited[nr][nc] = true;
            alphavet[map[nr][nc]-'A'] = true;
            dfs(nr, nc, cnt+1);
            visited[nr][nc] = false;
            alphavet[map[nr][nc]-'A'] = false;
        }
    }
}
