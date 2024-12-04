//CodeTree_나무타이쿤

import java.io.*;
import java.util.*;

public class Main {
    static int N, M;
    static boolean[][] grow;
    static int[][] board;
    static Queue<Node> water;
    static int[] dx = {0, -1, -1, -1, 0, 1, 1, 1}, dy = {1, 1, 0, -1, -1, -1, 0, 1};

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        grow = new boolean[N][N];
        board = new int[N][N];
        water = new LinkedList<>();

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        water.add(new Node(N - 2, 0));
        water.add(new Node(N - 1, 0));
        water.add(new Node(N - 2, 1));
        water.add(new Node(N - 1, 1));

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int dir = Integer.parseInt(st.nextToken()) - 1;
            int len = Integer.parseInt(st.nextToken());
            sol(len, dir);
        }

        int answer = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                answer += board[i][j];
            }
        }
        System.out.println(answer);
    }

    public static void sol(int len, int dir) {

        for (int i = 0; i < N; i++) Arrays.fill(grow[i], false);

        List<Node> point = new ArrayList<>();
        while (!water.isEmpty()) {
            Node node = water.poll();

            int nx = node.x;
            int ny = node.y;
            for (int i = 0; i < len; i++) {
                nx += dx[dir];
                ny += dy[dir];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N) {

                    if (nx == N) nx = 0;
                    if (ny == N) ny = 0;
                    if (nx == -1) nx = N - 1;
                    if (ny == -1) ny = N - 1;
                }
            }

            point.add(new Node(nx, ny));
            board[nx][ny] += 1;
            grow[nx][ny] = true;

        }

        Queue<Integer> late = new LinkedList<>();
        for (Node node : point) {
            int count = 0;
            for (int j = 1; j < 8; j += 2) {
                int nx = node.x + dx[j];
                int ny = node.y + dy[j];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;

                if (board[nx][ny] > 0) count++;
            }
            late.offer(count);
        }

        for (Node node : point) {
            board[node.x][node.y] += late.poll();
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!grow[i][j] && board[i][j] >= 2) {
                    board[i][j] -= 2;
                    water.offer(new Node(i, j));
                }
            }
        }

    }

    static class Node {
        int x, y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}

