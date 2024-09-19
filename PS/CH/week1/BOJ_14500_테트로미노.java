// BOJ_14500_테트로미노

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int n, m, answer;
    static int[][] board;
    static int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        board = new int[n][m];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        boolean[][] v = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                v[i][j] = true;
                dfs(v, i, j, board[i][j], 1, 4);
                v[i][j] = false;
            }
        }

        System.out.println(answer);

    }

    public static void dfs(boolean[][] v, int x, int y, int sum, int depth, int len) {
        if (depth == len) {
            answer = Math.max(answer, sum);
            return;
        }
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (nx < 0 || ny < 0 || nx >= n || ny >= m) continue;

            if (!v[nx][ny]) {
                v[nx][ny] = true;
                if(depth==2) dfs(v, x, y, sum+board[nx][ny], depth+1, len);
                dfs(v, nx, ny, sum + board[nx][ny], depth + 1, len);
                v[nx][ny] = false;
            }

        }
    }
}

//
