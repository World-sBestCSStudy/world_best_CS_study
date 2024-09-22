// BOJ_2589_보물섬

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static int n, m;
    static int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};
    static char[][] board;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        board = new char[n][m];


        for (int i = 0; i < n; i++) {
            board[i] = br.readLine().toCharArray();
        }


        int answer = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == 'W') continue;
                answer = Math.max(answer, dijkstra(i, j));
            }
        }
        System.out.println(answer);

    }

    public static int dijkstra(int x, int y) {
        PriorityQueue<Node> q = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.cost, o2.cost));
        int[][] dist = new int[n][m];

        int result = 0;
        for (int i = 0; i < n; i++) Arrays.fill(dist[i], Integer.MAX_VALUE);

        q.offer(new Node(x, y, 0));
        dist[x][y] = 0;

        while (!q.isEmpty()) {
            Node node = q.poll();
            result = Math.max(result, node.cost);

            if(dist[node.x][node.y] < node.cost) continue;

            for (int i = 0; i < 4; i++) {
                int nx = dx[i] + node.x;
                int ny = dy[i] + node.y;

                if (nx < 0 || ny < 0 || nx >= n || ny >= m || board[nx][ny] != 'L') continue;

                if (dist[nx][ny] > dist[node.x][node.y] + 1) {
                    dist[nx][ny] = dist[node.x][node.y] + 1;
                    q.offer(new Node(nx, ny, dist[nx][ny]));
                }
            }
        }


        return result;

    }

    static class Node {
        int x, y, cost;

        public Node(int x, int y, int cost) {
            this.x = x;
            this.y = y;
            this.cost = cost;
        }
    }

}

