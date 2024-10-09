//SWEA_메이즈러너

import org.w3c.dom.css.CSSImportRule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] dx = {-1, 1, 0, 0}, dy = {0, 0, -1, 1};
    static int N, M, K;
    static int[][] board;
    static List<Node> player;
    static Node door;

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


        player = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            board[x][y] += 11;
            player.add(new Node(x, y));
        }

        st = new StringTokenizer(br.readLine());
        door = new Node(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1);
        board[door.x][door.y] = 10;


        int move = 0;
        for (int i = 0; i < K; i++) {
            if (player.isEmpty()) break;

            move += updateDist();

            int x = 0, y = 0;
            int size = 0;
            for (int j = 1; j < N+1; j++) {
                int[] point = findRect(j);
                size = j;
                x = point[0];
                y = point[1];
                if (point[0] != -1 && point[1] != -1) break;
            }
            if(x==-1 && y==-1) continue;
            rotate(x, y, size);

        }
        StringBuilder answer= new StringBuilder();
        answer.append(move).append("\n").append(door.x+1).append(" ").append(door.y+1);
        System.out.println(answer);
    }

    public static void print() {
        for (int i = 0; i < N; i++) System.out.println(Arrays.toString(board[i]));
        System.out.println();
    }

    public static int updateDist() {
        int move = 0;
        for (int i = player.size() - 1; i >= 0; i--) {

            Node node = player.get(i);
            int nowDist = calDist(node.x, node.y, door.x, door.y);
            int dir = -1;
            for (int j = 0; j < 4; j++) {
                int nx = node.x + dx[j];
                int ny = node.y + dy[j];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N || (board[nx][ny] >= 1 && board[nx][ny] <= 9)) continue;

                int newDist = calDist(nx, ny, door.x, door.y);
                if (nowDist > newDist) {
                    nowDist = newDist;
                    dir = j;
                }
                if (nowDist == 0) {
                    player.remove(i);
                    break;
                }
            }

            if (dir == -1) continue;

            node.x += dx[dir];
            node.y += dy[dir];
            move++;
        }


        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] >= 11) board[i][j] = 0;
            }
        }

        for (Node node : player) board[node.x][node.y] += 11;
        return move;
    }


    public static int calDist(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    public static void rotate(int x, int y, int size) {
        int[] temp = new int[size * size];
        int idx = 0;
        for (int j = y; j < y + size; j++) {
            for (int i = x + size - 1; i >= x; i--) {
                temp[idx++] = board[i][j];
            }
        }

        idx = 0;
        for (int i = x; i < x + size; i++) {
            for (int j = y; j < y + size; j++) {
                board[i][j] = temp[idx++];
                if (board[i][j] >= 1 && board[i][j] <= 9) board[i][j]--;
            }
        }

        player.clear();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] >= 11) {
                    for (int z = 0; z < board[i][j] / 11; z++) player.add(new Node(i, j));
                } else if (board[i][j] == 10) {
                    door.x = i;
                    door.y = j;
                }
            }
        }
    }

    public static int[] findRect(int size) {
        for (int i = 0; i <= N - size; i++) {
            for (int j = 0; j <= N - size; j++) {
                boolean isDoor = false, isPlayer = false;
                for (int x = i; x < i + size; x++) {
                    for (int y = j; y < j + size; y++) {
                        if (board[x][y] == 10) isDoor = true;
                        if (board[x][y] >= 11) isPlayer = true;
                        if (isDoor && isPlayer) return new int[]{i, j};
                    }
                }
            }
        }
        return new int[]{-1, -1};
    }

    static class Node {
        int x, y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}