//BOJ_17142_연구소 3

import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N, M, L, T;
    static int[][] board;
    static int[] active;
    static List<Node> virus;

    static int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};
    static int answer = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        board = new int[N][N];
        active = new int[M];
        virus = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
                if (board[i][j] == 2) {
                    L += 1;
                    virus.add(new Node(i, j));
                } else if (board[i][j] == 0) {
                    T++;
                }
            }
        }

        if (T > 0) {
            boolean[] v = new boolean[L];
            selectVirus(v, 0, 0);
        } else {
            answer = 0;
        }

        System.out.println(answer==Integer.MAX_VALUE?-1:answer);

    }

    public static void spreadVirus() {
        Queue<Node> q = new LinkedList<>();
        boolean[][] v = new boolean[N][N];
        for (int i = 0; i < M; i++) {
            Node node = virus.get(active[i]);
            v[node.x][node.y] = true;
            q.offer(new Node(node.x, node.y, 0));
        }

        int maxCount = 0;

        while (!q.isEmpty()) {
            Node node = q.poll();

            for (int i = 0; i < 4; i++) {
                int nx = node.x + dx[i];
                int ny = node.y + dy[i];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;

                if (!v[nx][ny] && board[nx][ny] != 1) {
                    v[nx][ny] = true;
                    if (board[nx][ny] == 0) {
                        maxCount++;
                        if (maxCount == T) {
                            if (isAllSpread(v)) answer = Math.min(answer, node.count + 1);
                            return;
                        }

                    }
                    q.offer(new Node(nx, ny, node.count + 1));
                }
            }
        }


    }

    public static void selectVirus(boolean[] v, int start, int depth) {
        if (depth == M) {
            spreadVirus();
            return;
        }
        for (int i = start; i < L; i++) {
            if (!v[i]) {
                v[i] = true;
                active[depth] = i;
                selectVirus(v, i + 1, depth + 1);
                v[i] = false;
            }
        }

    }

    public static boolean isAllSpread(boolean[][] v) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == 0) {
                    if (!v[i][j]) return false;
                }
            }
        }
        return true;

    }

    static class Node {
        int x, y, count;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Node(int x, int y, int count) {
            this.x = x;
            this.y = y;
            this.count = count;
        }
    }

}
