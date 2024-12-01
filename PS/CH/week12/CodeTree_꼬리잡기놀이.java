//CodeTree_꼬리잡기놀이
import java.io.*;
import java.util.*;

public class Main {
    static int N, M, K;
    //    static int[] dx = {0, -1, 0, 1}, dy = {1, 0, -1, 0};
    static int[] dx = {1, 0, -1, 0}, dy = {0, 1, 0, -1};
    static int[][] board;
    static int[][] numbering;
    static List<Node> cmd;
    static List<List<Node>> player;
    static int score = 0;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        board = new int[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }


        boolean[][] v = new boolean[N][N];
        numbering = new int[N][N];
        int id = 1;
        player = new ArrayList<>();
        player.add(new ArrayList<>());

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!v[i][j] && board[i][j] == 1) {
                    v[i][j] = true;
                    player.add(new ArrayList<>());
                    numbering[i][j] = id;
                    player.get(id).add(new Node(i, j));
                    findLine(v, id, i, j);
                    id++;
                }
            }
        }


        cmd = new ArrayList<>();
        makeCmd();

        for (int i = 0; i < K; i++) {
            sol(i);
        }

        System.out.println(score);
    }

    public static void sol(int r) {
        for (int i = 1; i < player.size(); i++) {
            move(i);
        }
        shoot(r);
    }

    public static void shoot(int r) {
        Node s = cmd.get(r % cmd.size());
        int nx = s.x;
        int ny = s.y;
        int dir = s.dir;
        while (true) {

            if (nx < 0 || ny < 0 || nx >= N || ny >= N) break;

            int id = numbering[nx][ny];
            if (id > 0) {
                for (int i = 0; i < player.get(id).size(); i++) {
                    Node node = player.get(id).get(i);
                    if (node.x == nx && node.y == ny) {
                        score += (int) Math.pow(i + 1, 2);
                        break;
                    }
                }

                Collections.reverse(player.get(id));

                Node head = player.get(id).get(0);
                Node tail = player.get(id).get(player.get(id).size() - 1);
                board[head.x][head.y] = 1;
                board[tail.x][tail.y] = 3;
                for (int i = 1; i < player.get(id).size() - 1; i++) {
                    Node node = player.get(id).get(i);
                    board[node.x][node.y] = 2;
                }

                break;
            }

            nx += dx[dir];
            ny += dy[dir];

        }
    }

    public static void makeCmd() {
        int nx = 0;
        int ny = 0;
        int dir = 0;
        int count = 0;
        while (count < N * 4) {
            cmd.add(new Node(nx, ny, (dir + 1) % 4));
            nx += dx[dir];
            ny += dy[dir];

            if (nx < 0 || ny < 0 || nx >= N || ny >= N) {
                nx -= dx[dir];
                ny -= dy[dir];
                dir = (dir + 1) % 4;
            }

            count++;
        }
    }

    public static void findLine(boolean[][] v, int id, int x, int y) {
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;

            if (!v[nx][ny] && (
                    (board[x][y] == 1 && board[nx][ny] == 2) ||
                            (board[x][y] == 2 && (board[nx][ny] == 2 || board[nx][ny] == 3)))
            ) {
                v[nx][ny] = true;
                numbering[nx][ny] = id;
                player.get(id).add(new Node(nx, ny));
                findLine(v, id, nx, ny);
            }
        }
    }

    public static void move(int id) {
        boolean[][] v = new boolean[N][N];
        List<Node> next = new ArrayList<>();

        for (Node node : player.get(id)) {

            for (int i = 0; i < 4; i++) {
                int nx = node.x + dx[i];
                int ny = node.y + dy[i];
                if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;

                int now = board[node.x][node.y];
                if (now == 1 && (board[nx][ny] == 4 || board[nx][ny] == 3)) {
                    v[node.x][node.y] = true;
                    next.add(new Node(nx, ny));
                } else if (now == 2 && v[nx][ny] && (board[nx][ny] == 2 || board[nx][ny] == 1)) {
                    v[node.x][node.y] = true;
                    next.add(new Node(nx, ny));
                } else if (now == 3 && board[nx][ny] == 2) {
                    v[node.x][node.y] = true;
                    next.add(new Node(nx, ny));
                }
            }
        }


        for (Node node : player.get(id)) {
            board[node.x][node.y] = 4;
            numbering[node.x][node.y] = 0;
        }

        Node head = next.get(0);
        Node tail = next.get(next.size() - 1);

        board[head.x][head.y] = 1;
        board[tail.x][tail.y] = 3;
        numbering[head.x][head.y] = id;
        numbering[tail.x][tail.y] = id;

        player.get(id).clear();
        player.get(id).add(head);

        for (int i = 1; i < next.size() - 1; i++) {
            Node node = next.get(i);
            player.get(id).add(node);
            board[node.x][node.y] = 2;
            numbering[node.x][node.y] = id;
        }

        player.get(id).add(tail);

    }

    static class Node {
        int x, y, dir;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Node(int x, int y, int dir) {
            this.x = x;
            this.y = y;
            this.dir = dir;
        }
    }


}
