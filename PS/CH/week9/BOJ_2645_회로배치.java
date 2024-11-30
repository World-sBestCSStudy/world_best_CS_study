//BOJ_2645_회로배치
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N, K;
    static int[][] board;
    static int[] dx = {-1, 0, 1, 0}, dy = {0, -1, 0, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());

        board = new int[N + 1][N + 1];
        for (int i = 0; i < N + 1; i++) Arrays.fill(board[i], 1);


        st = new StringTokenizer(br.readLine());
        int sx = Integer.parseInt(st.nextToken());
        int sy = Integer.parseInt(st.nextToken());
        int ex = Integer.parseInt(st.nextToken());
        int ey = Integer.parseInt(st.nextToken());

        K = Integer.parseInt(br.readLine());

        int m = Integer.parseInt(br.readLine());

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());

            int c = Integer.parseInt(st.nextToken());
            int x1 = Integer.parseInt(st.nextToken());
            int y1 = Integer.parseInt(st.nextToken());

            for (int j = 0; j < c - 1; j++) {
                int x2 = Integer.parseInt(st.nextToken());
                int y2 = Integer.parseInt(st.nextToken());

                if (x1 == x2) {
                    for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
                        board[x1][y] = K;
                    }
                } else {
                    for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
                        board[x][y1] = K;
                    }
                }

                x1 = x2;
                y1 = y2;
            }
        }

        System.out.println(dijkstra(sx, sy, ex, ey));


    }

    public static String dijkstra(int sx, int sy, int ex, int ey) {
        PriorityQueue<Node> q = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.cost, o2.cost));
        int[][] dist = new int[N + 1][N + 1];

        int[][] routeX = new int[N + 1][N + 1];
        int[][] routeY = new int[N + 1][N + 1];


        for (int i = 0; i < N + 1; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }

        q.offer(new Node(sx, sy, 1));
        dist[sx][sy] = 1;

        while (!q.isEmpty()) {
            Node node = q.poll();

            for (int i = 0; i < 4; i++) {
                int nx = node.x + dx[i];
                int ny = node.y + dy[i];

                if (nx < 1 || ny < 1 || nx > N || ny > N) continue;

                if (dist[nx][ny] > dist[node.x][node.y] + board[nx][ny]) {
                    routeX[nx][ny] = node.x;
                    routeY[nx][ny] = node.y;
                    dist[nx][ny] = dist[node.x][node.y] + board[nx][ny];
                    q.offer(new Node(nx, ny, dist[nx][ny]));
                }
            }
        }


        int x = ex;
        int y = ey;
        Stack<Node> route = new Stack<>();
        route.push(new Node(ex,ey));
        while (true) {
            int nx = routeX[x][y];
            int ny = routeY[x][y];
            if (nx == 0 && ny == 0) break;
            route.push(new Node(nx,ny));
            x = nx;
            y = ny;
        }


        boolean xChange=false, yChange=false;
        Node prev = route.pop();
        if(prev.x == route.peek().x){
            yChange= true;
        }else{
            xChange=true;
        }

        List<Node> answer = new ArrayList<>();

        answer.add(prev);

        while(!route.isEmpty()){
            Node next = route.pop();
            if(prev.x != next.x){
                if(!xChange){
                    xChange=true;
                    yChange=false;
                    answer.add(prev);
                }
            }else if(prev.y!= next.y){
                if(!yChange){
                    xChange=false;
                    yChange=true;
                    answer.add(prev);
                }
            }

            prev=next;
        }

        answer.add(prev);



        StringBuilder sb=  new StringBuilder();
        sb.append(dist[ex][ey]).append("\n");
        sb.append(answer.size()).append(" ");
        for(Node node : answer) sb.append(node.x).append(" ").append(node.y).append(" ");

        return sb.toString();

    }

    static class Node {
        int x, y, cost;

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
