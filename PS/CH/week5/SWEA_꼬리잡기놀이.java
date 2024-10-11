//SWEA_꼬리잡기놀이

import java.io.*;
import java.util.*;

public class Main {
    static int N, M, K;
    static int[][] board;
    static int[][] user;
    static int[] dx = {0, -1, 0, 1}, dy = {1, 0, -1, 0};
    static List<List<Player>> line;
    static List<Player> prev;
    static List<Node> cmd;
    static int cmdLen;
    static int answer = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        line = new ArrayList<>();
        prev = new ArrayList<>();
        cmd = new ArrayList<>();
        user = new int[N][N];
        cmdLen = N * 4;

        board = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }


        int id = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == 1) {
                    line.add(new ArrayList<>());
                    findLine(id, i, j);
                    id++;
                }
            }
        }

        for (int i = 0; i < N; i++) cmd.add(new Node(i, 0, 0));

        for (int i = 0; i < N; i++) cmd.add(new Node(N - 1, i, 1));

        for (int i = N - 1; i >= 0; i--) cmd.add(new Node(i, N - 1, 2));

        for (int i = N - 1; i >= 0; i--) cmd.add(new Node(0, i, 3));


        for (int i = 0; i < K; i++) {
            move();
            updateMap();

            Node node = cmd.get(i % cmdLen);
            shootBall(node.x, node.y, node.dir);
            updateMap();
        }

        System.out.println(answer);
    }

    public static void shootBall(int x, int y, int dir) {
        while (true) {
            if (user[x][y] > 0 && user[x][y] < 4) break;
            x += dx[dir];
            y += dy[dir];

            if (x < 0 || y < 0 || x >= N || y >= N) return;
        }

        for (List<Player> players : line) {
            for (int i = 0; i < players.size(); i++) {
                Player p = players.get(i);
                if (p.x == x && p.y == y) {
                    answer += (i + 1) * (i + 1);

                    Collections.reverse(players);

                    players.get(0).id = 1;
                    for (int j = 1; j < players.size() - 1; j++) {
                        players.get(j).id = 2;
                    }
                    players.get(players.size() - 1).id = 3;
                    return;
                }
            }
        }
    }

    public static void move() {
        for (List<Player> players : line) {

            prev.clear();
            for (Player p : players) prev.add(new Player(p.id, p.x, p.y));

            for (int i = 0; i < players.size(); i++) {
                Player p = players.get(i);
                int[] next = findNext(p.x, p.y, i);
                players.get(i).x = next[0];
                players.get(i).y = next[1];
            }

        }
    }

    public static int[] findNext(int x, int y, int number) {
        int[] result = new int[]{-1, -1};
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (nx < 0 || ny < 0 || nx >= N || ny >= N || board[nx][ny] != 4) continue;
            if (number == 0) {
                Player p = prev.get(number + 1);
                if (!(nx == p.x && ny == p.y)) {
                    result[0] = nx;
                    result[1] = ny;
                }
            } else {
                Player p = prev.get(number - 1);
                if (nx == p.x && ny == p.y) {
                    result[0] = nx;
                    result[1] = ny;
                }
            }
        }
        return result;
    }

    public static void findLine(int id, int x, int y) {
        Queue<Player> q = new LinkedList<>();

        Player tail = new Player(1, -1, -1);
        q.offer(new Player(1, x, y));

        while (!q.isEmpty()) {
            Player p = q.poll();
            tail = p;
            line.get(id).add(p);
            board[p.x][p.y] = 4;
            for (int i = 0; i < 4; i++) {
                int nx = p.x + dx[i];
                int ny = p.y + dy[i];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;

                if (board[nx][ny] == 2) {
                    q.offer(new Player(2, nx, ny));
                }
            }
        }


        for (int i = 0; i < 4; i++) {
            int nx = tail.x + dx[i];
            int ny = tail.y + dy[i];

            if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;

            if (board[nx][ny] == 3) {
                board[nx][ny] = 4;
                line.get(id).add(new Player(3, nx, ny));
                break;
            }
        }
    }

    public static void updateMap() {
        for (int i = 0; i < N; i++) Arrays.fill(user[i], 0);

        for (int i = 0; i < N; i++) {
            user[i] = board[i].clone();
        }

        for (List<Player> players : line) {
            for (Player player : players) {
                user[player.x][player.y] = player.id;
            }
        }
    }

    static class Player {
        int id, x, y;

        public Player(int id, int x, int y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }
    }

    static class Node {
        int x, y, dir;

        public Node(int x, int y, int dir) {
            this.x = x;
            this.y = y;
            this.dir = dir;
        }
    }
}