// BOJ_2146_다리 만들기

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int n;
    static int[][] board;
    static int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());

        board = new int[n][n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        boolean[][] v = new boolean[n][n];
        int number = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (!v[i][j] && board[i][j] == 1) {
                    separate(v, i, j, number);
                    number++;
                }
            }
        }

        int answer = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] > 0) {
                    answer = Math.min(answer,bridge(i,j));
                }
            }
        }
        System.out.println(answer);

    }

    public static int bridge(int x, int y) {
        int result = Integer.MAX_VALUE;
        PriorityQueue<Node> q = new PriorityQueue<>((o1,o2)-> Integer.compare(o1.cost,o2.cost));
        q.offer(new Node(x, y, 0));

       int[][] dist= new int[n][n];
       for(int i =0; i<n; i++) Arrays.fill(dist[i], Integer.MAX_VALUE);
       dist[x][y] = 0;

        while (!q.isEmpty()) {
            Node node = q.poll();
            if(dist[node.x][node.y] < node.cost) continue;
            if(board[node.x][node.y] != board[x][y] && board[node.x][node.y] > 0){
                result = node.cost;
                break;
            }
            for (int i = 0; i < 4; i++) {

                int nx = node.x + dx[i];
                int ny = node.y + dy[i];

                if (nx < 0 || ny < 0 || nx >= n || ny >= n) continue;

                if (dist[nx][ny] > dist[node.x][node.y]+1 && board[nx][ny] != board[x][y]) {
                    dist[nx][ny] = dist[node.x][node.y]+1;
                    q.offer(new Node(nx, ny, dist[nx][ny]));
                }
            }
        }
        return result-1;
    }

    public static void separate(boolean[][] v, int x, int y, int number) {
        Queue<Node> q = new LinkedList<>();
        q.offer(new Node(x, y));

        board[x][y] = number;
        v[x][y] = true;

        while (!q.isEmpty()) {
            Node node = q.poll();

            for (int i = 0; i < 4; i++) {

                int nx = node.x + dx[i];
                int ny = node.y + dy[i];

                if (nx < 0 || ny < 0 || nx >= n || ny >= n) continue;

                if (!v[nx][ny] && board[nx][ny] == 1) {
                    v[nx][ny] = true;
                    board[nx][ny] = number;
                    q.offer(new Node(nx, ny));
                }
            }
        }
    }

    static class Node {
        int x, y, cost;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Node(int x, int y, int cost) {
            this.x = x;
            this.y = y;
            this.cost = cost;
        }
    }

}
