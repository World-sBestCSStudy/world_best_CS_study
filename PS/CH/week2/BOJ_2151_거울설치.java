// BOJ_2151_거울설치

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int n;
    static int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};
    static char[][] board;
    static List<Node> doorPoint = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        board = new char[n][n];
        for (int i = 0; i < n; i++) {
            board[i] = br.readLine().toCharArray();
            for (int j = 0; j < n; j++) {
                if (board[i][j] == '#') doorPoint.add(new Node(i, j));
            }
        }

        System.out.println(findRoute());
    }


    public static int findRoute() {
        Node start = doorPoint.get(0);
        Node end = doorPoint.get(1);

        Queue<Node> q = new LinkedList<>();
        boolean[][][] v = new boolean[4][n][n];


        q.offer(new Node(start.x, start.y, 0, 0));
        q.offer(new Node(start.x, start.y, 1, 0));
        q.offer(new Node(start.x, start.y, 2, 0));
        q.offer(new Node(start.x, start.y, 3, 0));
        while (!q.isEmpty()) {
            Node node = q.poll();

            int nx = node.x;
            int ny = node.y;

            if (end.x == nx && end.y == ny) {
                return node.count;
            }
            while (true) {
                nx += dx[node.dir];
                ny += dy[node.dir];

                if (nx < 0 || ny < 0 || nx >= n || ny >= n || board[nx][ny] == '*') break;

                if (board[nx][ny] == '!') {
                    if (!v[(node.dir + 1) % 4][nx][ny]) {
                        v[(node.dir + 1) % 4][nx][ny] = true;
                        q.offer(new Node(nx, ny, (node.dir + 1) % 4, node.count + 1));
                    }
                    if (!v[(node.dir + 3) % 4][nx][ny]) {
                        v[(node.dir + 3) % 4][nx][ny] = true;
                        q.offer(new Node(nx, ny, (node.dir + 3) % 4, node.count + 1));
                    }


                }
                if (!v[node.dir][nx][ny]) {
                    v[node.dir][nx][ny] = true;
                    q.offer(new Node(nx, ny, node.dir, node.count));
                }

            }
        }
        return -1;

    }

    static class Node {
        int x, y, dir, count;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Node(int x, int y, int dir, int count) {
            this.x = x;
            this.y = y;
            this.dir = dir;
            this.count = count;
        }
    }

}
