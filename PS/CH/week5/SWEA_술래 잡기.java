//SWEA_술래 잡기

import java.io.*;
import java.util.*;

public class Main {
    static int N, M, H, K;
    static boolean[][] trees;
    static int[][] count;

    static int[] dx = {1, 0, -1, 0}, dy = {0, 1, 0, -1};
    static List<Player> rms;
    static Player hero;

    static List<Player> route;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        trees = new boolean[N][N];
        count = new int[N][N];
        rms = new ArrayList<>();
        hero = new Player(N / 2, N / 2, 0);
        route = new ArrayList<>();

        //d가 1인 경우 좌우로 움직임을, 2인 경우 상하로만 움직임
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            int d = Integer.parseInt(st.nextToken());
            if(d==2) d=0;
            rms.add(new Player(x, y, d));
            count[x][y] += 1;
        }

        for (int i = 0; i < H; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            trees[x][y] = true;
        }

        boolean[][] v = new boolean[N][N];
        v[0][0] = true;
        setRoute(v, 0, 0, 0);

        int now = route.size() / 2;

        int answer = 0;
        for (int i = 0; i < K; i++) {
            run();
            now = (now+1)%route.size();
            answer+= moveHero(now)*(i+1);

        }
        System.out.println(answer);

    }

    public static int moveHero(int now) {
        Player next = route.get(now);
        hero.x = next.x;
        hero.y = next.y;
        hero.dir = next.dir;

        return watch(hero.x,hero.y,hero.dir);


    }

    public static int watch(int x, int y, int dir) {
        int nx = x;
        int ny = y;
        int result = 0;
        for (int i = 0; i < 3; i++) {
            if (nx >= 0 && ny >= 0 && nx < N && ny < N && !trees[nx][ny] && count[nx][ny]>0) {
                result += count[nx][ny];
                count[nx][ny] = 0;
            }
            nx += dx[dir];
            ny += dy[dir];
        }

        updateRms();

        return result;

    }

    public static void updateRms() {
        for (int i = rms.size() - 1; i >= 0; i--) {
            Player p = rms.get(i);
            if (count[p.x][p.y] == 0) rms.remove(i);
        }
    }

    public static void run() {
        for (Player p : rms) {
            if (calDist(p.x, p.y, hero.x, hero.y) > 3) continue;

            int dir = p.dir;
            int nx = p.x + dx[dir];
            int ny = p.y + dy[dir];
            if (nx < 0 || ny < 0 || nx >= N || ny >= N) {
                dir = (dir + 2) % 4;
                nx = p.x + dx[dir];
                ny = p.y + dy[dir];

            }
            if (hero.x == nx && hero.y == ny) continue;
            count[p.x][p.y] -= 1;
            count[nx][ny] += 1;

            p.x = nx;
            p.y = ny;
            p.dir = dir;
        }
    }

    public static void setRoute(boolean[][] v, int x, int y, int dir) {
        if (x == N / 2 && y == N / 2) {
            return;
        }
        int nx = x + dx[dir];
        int ny = y + dy[dir];
        if (nx < 0 || ny < 0 || nx >= N || ny >= N || v[nx][ny]) {
            dir = (dir + 1) % 4;
            nx = x + dx[dir];
            ny = y + dy[dir];
        }

        v[nx][ny] = true;
        route.add(new Player(x, y, dir));
        setRoute(v, nx, ny, dir);
        route.add(new Player(nx, ny, (dir + 2) % 4));

    }

    public static int calDist(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);

    }

    static class Player {
        int x, y, dir;


        public Player(int x, int y, int dir) {
            this.x = x;
            this.y = y;
            this.dir = dir;
        }
    }

}