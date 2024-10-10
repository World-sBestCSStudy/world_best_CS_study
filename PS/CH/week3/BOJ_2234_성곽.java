// BOJ_2234_성곽

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int n, m;
    static Node[][] board;
    static int[] dx = {0, -1, 0, 1}, dy = {-1, 0, 1, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        m = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());
        board = new Node[n][m];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                int w = Integer.parseInt(st.nextToken());
                board[i][j] = new Node(i, j);

                char[] temp = Integer.toBinaryString(w).toCharArray();
                int right = 3;
                for (int p = temp.length - 1; p >= 0; p--) {
                    board[i][j].wall[right--] = temp[p] - 48;
                }
            }
        }

        boolean[][] v = new boolean[n][m];
        int numbering = 0;
        int[] answer = new int[3];
        int[] roomCount = new int[2501];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (!v[i][j]) {
                    v[i][j] = true;
                    int count = bfs(v, i, j, numbering);
                    roomCount[numbering++] = count;

                    answer[0]++;
                    answer[1] = Math.max(answer[1], count);
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {

                for (int p = 0; p < 4; p++) {
                    int nx = i + dx[p];
                    int ny = j + dy[p];
                    if (nx < 0 || ny < 0 || nx >= n || ny >= m) continue;

                    if (board[i][j].numbering != board[nx][ny].numbering) {
                        answer[2] = Math.max(answer[2], roomCount[board[i][j].numbering] + roomCount[board[nx][ny].numbering]);
                    }
                }

            }
        }

        System.out.println(answer[0]+"\n"+answer[1]+"\n"+answer[2]);


    }

    public static int bfs(boolean[][] v, int x, int y, int numbering) {
        Queue<Node> q = new LinkedList<>();
        q.offer(new Node(x, y));
        int count = 1;

        while (!q.isEmpty()) {
            Node node = q.poll();
            board[node.x][node.y].numbering = numbering;
            for (int i = 0; i < 4; i++) {
                int nx = node.x + dx[i];
                int ny = node.y + dy[i];
                if (nx < 0 || ny < 0 || nx >= n || ny >= m) continue;
                if (!v[nx][ny] && board[node.x][node.y].wall[3 - i] == 0) {
                    v[nx][ny] = true;
                    count++;
                    q.offer(new Node(nx, ny));

                }
            }
        }
        return count;
    }

    static class Node {
        int x, y, numbering;
        int[] wall;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
            this.numbering = 0;
            this.wall = new int[4];
        }
    }

}
