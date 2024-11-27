//CodeTree_코드트리 빵
import java.io.*;
import java.util.*;


public class Main {
    static int N, M, endCount;
    static int[][] board;
    static boolean[][] isBlock;
    static Node[] stores;
    static Node[] customers;
    static boolean[] isEnd;
    static int[] dx = {-1, 0, 0, 1}, dy = {0, -1, 1, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        board = new int[N][N];
        isBlock = new boolean[N][N];

        stores = new Node[M];
        customers = new Node[M];
        isEnd = new boolean[M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            stores[i] = new Node(x, y);
            board[x][y] = 2;

        }


        int t = 0;
        while (endCount < M) {
            t += 1;
            sol(Math.min(t, M));
        }
        System.out.println(t);
    }

    public static void sol(int t) {
        for (int i = 0; i < t; i++) {
            if (isEnd[i] || customers[i] == null) continue;
            customers[i] = findShortPoint(i);
        }

        for (int i = 0; i < t; i++) {
            if (isEnd[i] || customers[i] == null) continue;

            int x = customers[i].x;
            int y = customers[i].y;
            if (x == stores[i].x && y == stores[i].y) {
                endCount++;
                isEnd[i] = true;
                isBlock[x][y] = true;
            }
        }

        if (t - 1 < M) {
            if (customers[t - 1] != null) return;
            customers[t - 1] = findBaseCamp(t - 1);
            isBlock[customers[t - 1].x][customers[t - 1].y] = true;
        }
    }


    public static Node findBaseCamp(int id) {
        Queue<Node> q = new LinkedList<>();
        boolean[][] v = new boolean[N][N];

        Node min = new Node(-1, -1, Integer.MAX_VALUE);

        int sx = stores[id].x;
        int sy = stores[id].y;
        q.offer(new Node(sx, sy));
        v[sx][sy] = true;

        while (!q.isEmpty()) {
            Node node = q.poll();

            if (board[node.x][node.y] == 1) {
                if (min.cost < node.cost) {
                    break;
                } else if (min.cost > node.cost) {
                    min = node;
                } else if ((min.x > node.x) || (min.x == node.x && min.y > node.y)) {
                    min = node;
                }
            }

            for (int i = 0; i < 4; i++) {
                int nx = node.x + dx[i];
                int ny = node.y + dy[i];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N || isBlock[nx][ny]) continue;

                if (!v[nx][ny]) {
                    v[nx][ny] = true;
                    q.offer(new Node(nx, ny, node.cost + 1));
                }
            }
        }

        return min;
    }


    //아무 상점이 아니라 목표상점으로 가야한다.
    public static Node findShortPoint(int id) {
        Queue<Node> q = new LinkedList<>();
        boolean[][] v = new boolean[N][N];
        int[][] routeX = new int[N][N];
        int[][] routeY = new int[N][N];

        int sx = customers[id].x;
        int sy = customers[id].y;
        int ex = stores[id].x;
        int ey = stores[id].y;
        q.offer(new Node(sx, sy));
        v[sx][sy] = true;


        while (!q.isEmpty()) {
            Node node = q.poll();

            if (node.x == ex && node.y == ey) {
                break;
            }

            for (int i = 0; i < 4; i++) {
                int nx = node.x + dx[i];
                int ny = node.y + dy[i];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N || isBlock[nx][ny]) continue;

                if (!v[nx][ny]) {
                    v[nx][ny] = true;
                    routeX[nx][ny] = node.x;
                    routeY[nx][ny] = node.y;
                    q.offer(new Node(nx, ny));
                }
            }
        }

        int x = ex;
        int y = ey;

        Stack<Node> route = new Stack<>();

        route.push(new Node(x, y));
        while (true) {
            int nx = routeX[x][y];
            int ny = routeY[x][y];

            if (nx == sx && ny == sy) break;
            route.push(new Node(nx, ny));

            x = nx;
            y = ny;
        }

        return route.pop();

    }

    static class Node {
        int x, y;
        int cost;

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
