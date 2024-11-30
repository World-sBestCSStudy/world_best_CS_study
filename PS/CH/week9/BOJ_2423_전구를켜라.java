//BOJ_2423_전구를켜라
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[][] board;
    static int N, M;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        board = new int[N][M];
        for (int i = 0; i < N; i++) {
            char[] temp = br.readLine().toCharArray();
            for (int j = 0; j < M; j++) {
                if (temp[j] == '\\') board[i][j] = 1;
            }
        }

        int count = 0;

        if (board[0][0] == 0) {
            count++;
            board[0][0] = 1;
        }

        if (board[N - 1][M - 1] == 0) {
            count++;
            board[N - 1][M - 1] = 1;
        }


        int answer = dijkstra();
        System.out.println(answer == Integer.MAX_VALUE ? "NO SOLUTION" : answer + count);

    }

    public static int dijkstra() {
        PriorityQueue<Node> q = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.cost, o2.cost));
        int[][] dist = new int[N][M];

        int[] dxOne = {0, -1, -1, 0, 1, 1}, dyOne = {-1, -1, 0, 1, 1, 0};
        int[] dxZero = {0, 1, 1, 0, -1, -1}, dyZero = {-1, -1, 0, 1, 1, 0};

        q.offer(new Node(0, 0, 0, board[0][0]));
        for (int i = 0; i < N; i++) Arrays.fill(dist[i], Integer.MAX_VALUE);
        dist[0][0] = 0;

        while (!q.isEmpty()) {
            Node node = q.poll();

            if (dist[node.x][node.y] < node.cost) continue;

            if (node.status == 1) {
                nextLine(q, dist, dxOne, dyOne, node);
            } else {
                nextLine(q, dist, dxZero, dyZero, node);
            }
        }

        return dist[N - 1][M - 1];
    }

    public static void nextLine(PriorityQueue<Node> q, int[][] dist, int[] dx, int[] dy, Node node) {
        for (int i = 0; i < 6; i++) {
            int nx = node.x + dx[i];
            int ny = node.y + dy[i];

            if (nx < 0 || ny < 0 || nx >= N || ny >= M) continue;

            //대각선 일때
            if (dx[i] != 0 && dy[i] != 0 && node.status == board[nx][ny]) {
                if (dist[nx][ny] > dist[node.x][node.y]) {
                    dist[nx][ny] = dist[node.x][node.y];
                    q.offer(new Node(nx, ny, dist[nx][ny], board[nx][ny]));
                }
            } else if (dx[i] != 0 && dy[i] != 0 && node.status != board[nx][ny]) {

                if (nx == N - 1 && ny == M - 1) continue;

                if (dist[nx][ny] > dist[node.x][node.y] + 1) {
                    dist[nx][ny] = dist[node.x][node.y] + 1;
                    q.offer(new Node(nx, ny, dist[nx][ny], node.status));
                }
            }


            //상하좌우일때
            if ((dx[i] == 0 ^ dy[i] == 0) && node.status != board[nx][ny]) {

                if (dist[nx][ny] > dist[node.x][node.y]) {
                    dist[nx][ny] = dist[node.x][node.y];
                    q.offer(new Node(nx, ny, dist[nx][ny], board[nx][ny]));
                }
            } else if ((dx[i] == 0 ^ dy[i] == 0) && node.status == board[nx][ny]) {

                if (nx == N - 1 && ny == M - 1) continue;

                if (dist[nx][ny] > dist[node.x][node.y] + 1) {
                    dist[nx][ny] = dist[node.x][node.y] + 1;
                    q.offer(new Node(nx, ny, dist[nx][ny], node.status == 1 ? 0 : 1));
                }
            }
        }

    }

    static class Node {
        int x, y, cost, status;

        public Node(int x, int y, int cost, int status) {
            this.x = x;
            this.y = y;
            this.cost = cost;
            this.status = status;
        }
    }

}
