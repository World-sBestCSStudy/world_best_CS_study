//BOJ_1941_소문난 칠공주

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int N;
    static char[][] board;
    static int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};
    static int answer = 0;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = 5;
        board = new char[N][N];

        for (int i = 0; i < N; i++) {
            board[i] = br.readLine().toCharArray();
        }

        boolean[][] v = new boolean[N][N];
        dfs(v, 0, 0, 0);
        System.out.println(answer);

    }

    public static void dfs(boolean[][] v, int start, int depth, int s) {
        if(depth-s>=4) return;
        if (depth == 7) {
            if (bfs(v, start-1)) answer++;
            return;
        }

        for (int i = start; i < N * N; i++) {
            if (!v[i / N][i % N]) {
                v[i / N][i % N] = true;
                dfs(v, i + 1, depth + 1, board[i / N][i % N] == 'S' ? s + 1 : s);
                v[i / N][i % N] = false;
            }
        }
    }

    public static boolean bfs(boolean[][] v, int start) {
        Queue<Node> q = new LinkedList<>();
        boolean[][] lv = new boolean[N][N];
        int count = 1;

        q.offer(new Node(start / N, start % N));
        lv[start / N][start % N] = true;

        while (!q.isEmpty()) {
            Node node = q.poll();

            for (int i = 0; i < 4; i++) {
                int nx = node.x + dx[i];
                int ny = node.y + dy[i];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;

                if (v[nx][ny] && !lv[nx][ny]) {
                    count++;
                    lv[nx][ny] = true;
                    q.offer(new Node(nx, ny));
                }
            }
        }

        return count == 7;
    }

    static class Node {
        int x, y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}