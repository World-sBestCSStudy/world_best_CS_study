//SWEA_포탑 부수기

import java.io.*;
import java.util.*;

public class Main {

    //마지막으로 공격한 라운드 기록, attackRound[i][j]가 클수록 최근에 공격한것이다
    static int[][] attackRound;

    static int[] dxL = {0, 1, 0, -1}, dyL = {1, 0, -1, 0};
    static int[] dxB = {1, 0, -1, -1, -1, 0, 1, 1}, dyB = {-1, -1, -1, 0, 1, 1, 1, 0};
    static int N, M, K;
    static int[][] board;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        board = new int[N][M];
        attackRound = new int[N][M];


        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        for (int i = 0; i < K; i++) {
            int[][] copy = new int[N][M];
            for (int x = 0; x < N; x++) copy[x] = board[x].clone();

            int[] a = shooter(i);
            int[] b = targeting();
            board[a[0]][a[1]] += N + M;

            List<Node> route = dijkstra(a[0], a[1], b[0], b[1]);
            if (!route.isEmpty()) {
                for (int x = 1; x < route.size(); x++) {
                    Node node = route.get(x);
                    board[node.x][node.y] -= (board[a[0]][a[1]] / 2);
                }
                board[b[0]][b[1]] -= board[a[0]][a[1]];
            } else {
                shootBomb(a[0], a[1], b[0], b[1], board[a[0]][a[1]]);
            }


            int count = 0;
            for (int x = 0; x < N; x++) {
                for (int y = 0; y < M; y++) {
                    if (board[x][y] > 0) count++;
                    if ((x == a[0] && y == a[1]) || board[x][y] <= 0 || board[x][y] < copy[x][y]) continue;
                    board[x][y] += 1;
                }
            }

            if (count <= 1) break;

        }

        int answer = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                answer = Math.max(answer, board[i][j]);
            }
        }
        System.out.println(answer);
    }

    public static int[] shooter(int k) {
        int exp = Integer.MAX_VALUE;
        int recent = Integer.MIN_VALUE;
        int sum = 0;
        int[] result = {-1, -1};
        for (int j = M - 1; j >= 0; j--) {
            for (int i = N - 1; i >= 0; i--) {
                if (board[i][j] <= 0) continue;
                if (exp > board[i][j]) {
                    exp = board[i][j];
                    recent = attackRound[i][j];
                    result[0] = i;
                    result[1] = j;
                    sum = i + j;
                } else if (exp == board[i][j] && attackRound[i][j] > recent) {
                    recent = attackRound[i][j];
                    result[0] = i;
                    result[1] = j;
                } else if (exp == board[i][j] && attackRound[i][j] == recent && sum < i + j) {
                    recent = attackRound[i][j];
                    result[0] = i;
                    result[1] = j;
                    sum = i + j;
                }
            }
        }
        attackRound[result[0]][result[1]] = k + 1;
        return result;
    }

    public static int[] targeting() {
        int exp = Integer.MIN_VALUE;
        int recent = Integer.MIN_VALUE;
        int sum = Integer.MAX_VALUE;
        int[] result = {-1, -1};
        for (int j = 0; j < M; j++) {
            for (int i = 0; i < N; i++) {
                if (board[i][j] <= 0) continue;

                if (exp < board[i][j]) {
                    exp = board[i][j];
                    recent = attackRound[i][j];
                    result[0] = i;
                    result[1] = j;
                    sum = i + j;
                } else if (exp == board[i][j] && attackRound[i][j] < recent) {
                    recent = attackRound[i][j];
                    result[0] = i;
                    result[1] = j;
                    sum = i + j;
                } else if (exp == board[i][j] && attackRound[i][j] == recent && sum > i + j) {
                    result[0] = i;
                    result[1] = j;
                    sum = i + j;
                }
            }
        }
        return result;
    }


    public static void shootBomb(int x1, int y1, int x2, int y2, int exp) {
        board[x2][y2] -= exp;

        for (int i = 0; i < 8; i++) {
            int nx = x2 + dxB[i];
            int ny = y2 + dyB[i];


            if (nx < 0) nx = N - 1;
            else if (nx >= N) nx = 0;
            if (ny < 0) ny = M - 1;
            else if (ny >= M) ny = 0;

            if (x1 == nx && y1 == ny) continue;
            if (board[nx][ny] > 0) {
                board[nx][ny] -= (exp / 2);
            }

        }
    }

    public static List<Node> dijkstra(int x1, int y1, int x2, int y2) {
        Queue<Node> q = new LinkedList<>();
        Node[][] routes = new Node[N][M];
        routes[x1][y1] = new Node(-1, -1);
        int[][] dist = new int[N][M];
        for (int i = 0; i < N; i++) Arrays.fill(dist[i], Integer.MAX_VALUE);
        dist[x1][y1] = 0;
        q.offer(new Node(x1, y1, 0));

        while (!q.isEmpty()) {
            Node node = q.poll();

            if (dist[node.x][node.y] < node.cost) continue;
            for (int i = 0; i < 4; i++) {
                int nx = node.x + dxL[i];
                int ny = node.y + dyL[i];

                if (nx < 0) nx = N - 1;
                else if (nx >= N) nx = 0;
                if (ny < 0) ny = M - 1;
                else if (ny >= M) ny = 0;

                if (board[nx][ny] > 0 && dist[nx][ny] > dist[node.x][node.y] + 1) {
                    routes[nx][ny] = new Node(node.x, node.y);
                    dist[nx][ny] = dist[node.x][node.y] + 1;
                    q.offer(new Node(nx, ny, dist[nx][ny]));
                }
            }

        }

        Stack<Node> stack = new Stack();
        List<Node> result = new ArrayList<>();
        while (true) {
            Node node = routes[x2][y2];
            if (node == null) return result;
            if (node.x == -1 && node.y == -1) break;
            stack.push(node);
            x2 = node.x;
            y2 = node.y;
        }

        while (!stack.isEmpty()) {
            Node node = stack.pop();
            result.add(node);
        }
        return result;
    }

    static class Node {
        int x;
        int y;
        int cost;

        public Node(int x, int y, int cost) {
            this.x = x;
            this.y = y;
            this.cost = cost;
        }

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}