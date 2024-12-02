//BOJ_1525_퍼즐
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N = 3;
    static int[] dx = {-1, 1, 0, 0}, dy = {0, 0, -1, 1};
    static String target = "123456780";

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int[][] board = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int answer = bfs(board);
        System.out.println(answer);

    }

    public static int bfs(int[][] board) {
        Queue<Node> q = new LinkedList<>();
        Set<String> map = new HashSet<>();
        map.add(ArrtoString(board));
        int result = Integer.MAX_VALUE;


        q.offer(new Node(board, 0));

        while (!q.isEmpty()) {
            Node node = q.poll();

            if (target.equals(ArrtoString(node.board))) {
                result = node.count;
                break;
            }

            int x = 0, y = 0;

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (node.board[i][j] == 0) {
                        x = i;
                        y = j;
                    }
                }
            }

            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;

                int[][] nBoard = new int[N][N];
                for (int j = 0; j < N; j++) nBoard[j] = node.board[j].clone();

                int temp = nBoard[nx][ny];
                nBoard[nx][ny] = nBoard[x][y];
                nBoard[x][y] = temp;

                String next = ArrtoString(nBoard);
                if (!map.contains(next)) {
                    map.add(next);
                    q.offer(new Node(nBoard, node.count + 1));
                }
            }
        }

        return result == Integer.MAX_VALUE ? -1 : result;

    }

    public static String ArrtoString(int[][] board) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sb.append(board[i][j]);
            }
        }
        return sb.toString();
    }

    static class Node {
        int[][] board;
        int count;

        public Node(int[][] board, int count) {
            this.board = board;
            this.count = count;
        }
    }

}
