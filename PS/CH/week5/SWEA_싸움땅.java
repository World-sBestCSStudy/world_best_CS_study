//SWEA_싸움땅

import java.io.*;
import java.util.*;

public class Main {
    static int N, M, K;
    static int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};

    static Node[][] board;
    static Player[] players;
    static int[][] loc;
    static int[] scores;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        board = new Node[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = new Node();
                board[i][j].weapon.offer(Integer.parseInt(st.nextToken()));
            }
        }

        players = new Player[M + 1];
        loc = new int[N][N];
        scores = new int[M + 1];
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            int d = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());

            loc[x][y] = i + 1;
            players[i + 1] = new Player(i + 1, x, y, s, d, 0);
        }

        for (int i = 0; i < K; i++) {
            sol();
        }

        StringBuilder answer = new StringBuilder();
        for (int i = 1; i < M + 1; i++) answer.append(scores[i]).append(" ");

        System.out.println(answer);
    }

    public static void sol() {

        for (int i = 1; i < M + 1; i++) {
            int x = players[i].x;
            int y = players[i].y;
            move(i);
            int nx = players[i].x;
            int ny = players[i].y;


            loc[x][y] = 0;
            if (loc[nx][ny] == 0) {
                loc[nx][ny] = players[i].id;
                if (!board[nx][ny].weapon.isEmpty()) {
                    board[nx][ny].weapon.offer(players[i].gun);
                    players[i].gun = board[nx][ny].weapon.poll();

                }
            } else {
                int idx = loc[nx][ny];
                int p1 = players[i].exp + players[i].gun;
                int p2 = players[idx].exp + players[idx].gun;

                boolean isWin = (p1 > p2 || (p1 == p2 && players[i].exp > players[idx].exp)) ? true : false;

                int score = Math.abs((players[i].exp + players[i].gun) - (players[idx].exp + players[idx].gun));

                if (isWin) {
                    afterFight(i, idx, nx, ny, score);

                } else {
                    afterFight(idx, i, nx, ny, score);
                }
            }
        }
    }

    public static void afterFight(int winIdx, int loseIdx, int x, int y, int score) {
        board[x][y].weapon.offer(players[loseIdx].gun);
        players[loseIdx].gun = 0;

        loseMove(loseIdx);
        loc[x][y] = winIdx;

        //i가 이겼으면 해당 칸에서 무기를 선택한다
        board[x][y].weapon.offer(players[winIdx].gun);
        players[winIdx].gun = board[x][y].weapon.poll();

        scores[winIdx] += score;
    }

    public static void loseMove(int id) {
        Player p = players[id];

        int x = p.x;
        int y = p.y;

        int dir = p.dir;
        int nx = p.x + dx[dir];
        int ny = p.y + dy[dir];


        if (nx < 0 || ny < 0 || nx >= N || ny >= N || loc[nx][ny] > 0) {
            for (int i = 0; i < 4; i++) {
                nx = p.x + dx[(dir + i) % 4];
                ny = p.y + dy[(dir + i) % 4];
                if (nx < 0 || ny < 0 || nx >= N || ny >= N || loc[nx][ny] > 0) continue;
                dir = (dir + i) % 4;
                break;
            }
        }

        players[id].x = nx;
        players[id].y = ny;
        players[id].dir = dir;

        if (!board[nx][ny].weapon.isEmpty()) {
            players[id].gun = board[nx][ny].weapon.poll();
        }

        loc[nx][ny] = players[id].id;
    }

    public static void move(int id) {
        Player p = players[id];

        int dir = p.dir;
        int nx = p.x + dx[dir];
        int ny = p.y + dy[dir];

        if (nx < 0 || ny < 0 || nx >= N || ny >= N) {
            dir = (dir + 2) % 4;
            nx = p.x + dx[dir];
            ny = p.y + dy[dir];
        }

        players[id].x = nx;
        players[id].y = ny;
        players[id].dir = dir;

    }

    static class Player {
        int id, x, y, exp, dir, gun;

        public Player(int id, int x, int y, int exp, int dir, int gun) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.exp = exp;
            this.dir = dir;
            this.gun = gun;
        }
    }

    static class Node {
        PriorityQueue<Integer> weapon = new PriorityQueue<>((o1, o2) -> Integer.compare(o2, o1));
    }
}