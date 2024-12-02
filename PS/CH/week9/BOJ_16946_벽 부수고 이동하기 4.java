//BOJ_16946_벽 부수고 이동하기 4
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int N, M;
    static int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};
    static char[][] board;
    static int[][] count;
    static boolean[][] zeroV;
    static boolean[][] oneV;

    static Queue<Node> q = new LinkedList<>();
    static Queue<Node> one = new LinkedList<>();
    static Queue<Node> zero = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        board = new char[N][M];
        count = new int[N][M];
        zeroV = new boolean[N][M];
        oneV = new boolean[N][M];

        for (int i = 0; i < N; i++) {
            board[i] = br.readLine().toCharArray();
        }


        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (board[i][j] == '0' && !zeroV[i][j]) {
                    writeCount(i, j);
                }
            }
        }

        StringBuilder answer = new StringBuilder();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (board[i][j] == '1') answer.append((count[i][j] + 1) % 10);
                else answer.append("0");
            }
            answer.append("\n");
        }
        System.out.println(answer);
    }

    public static void writeCount(int x, int y) {

        q.clear();
        one.clear();
        zero.clear();


        q.offer(new Node(x, y));
        zero.offer(new Node(x, y));

        int sum = 1;
        zeroV[x][y] = true;

        while (!q.isEmpty()) {
            Node node = q.poll();

            for (int i = 0; i < 4; i++) {
                int nx = node.x + dx[i];
                int ny = node.y + dy[i];

                if (nx < 0 || ny < 0 || nx >= N || ny >= M) continue;

                if (board[nx][ny] == '1') {
                    if (!oneV[nx][ny]) {
                        oneV[nx][ny] = true;
                        one.offer(new Node(nx, ny));
                    }
                    continue;
                }

                if (!zeroV[nx][ny]) {
                    zeroV[nx][ny] = true;
                    sum += 1;
                    q.offer(new Node(nx, ny));
                    zero.offer(new Node(nx, ny));
                }
            }
        }

        while (!zero.isEmpty()) {
            Node node = zero.poll();
            count[node.x][node.y] = sum;
        }

        while (!one.isEmpty()) {
            Node node = one.poll();
            count[node.x][node.y] += sum;
            oneV[node.x][node.y] = false;
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
