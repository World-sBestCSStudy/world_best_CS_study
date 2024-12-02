//BOJ_14461_소가 길을 건넌 이유 7
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;


public class Main {
    static int N, T;
    static int[][] board;
    static int[] dx = {-1, 0, 1, 0}, dy = {0, -1, 0, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());


        board = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dijkstra();


    }

    public static void dijkstra() {
        PriorityQueue<Node> q = new PriorityQueue<>((o1, o2) -> Long.compare(o1.cost, o2.cost));
        boolean[][][] v = new boolean[N][N][3];

        q.offer(new Node(0, 0, 0, 0));

        while (!q.isEmpty()) {
            Node node = q.poll();

            if (v[node.x][node.y][node.count]) continue;
            v[node.x][node.y][node.count] = true;

            if (node.x == N - 1 && node.y == N - 1) {
                System.out.println(node.cost);
                break;
            }


            for (int i = 0; i < 4; i++) {
                int nx = node.x + dx[i];
                int ny = node.y + dy[i];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;

                if (!v[nx][ny][(node.count+1)%3]) {
                    if (node.count == 2) q.offer(new Node(nx, ny, node.cost + board[nx][ny] + T, 0));
                    else q.offer(new Node(nx, ny, node.cost + T, node.count + 1));
                }
            }

        }

    }

    static class Node {
        int x, y, count;
        long cost;

        public Node(int x, int y, long cost, int count) {
            this.x = x;
            this.y = y;
            this.cost = cost;
            this.count = count;
        }
    }

}
