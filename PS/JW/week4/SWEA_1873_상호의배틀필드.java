package ssafy_algorithm.SWEA;   // SWEA 1873. 상호의 배틀필드

import java.io.*;
import java.util.*;

// . *(벽돌벽) #(강철벽) -(물. 전차는 들어갈 수 없음) ^ v < >
// Up Down Left Right Shoot
public class SWEA_1873_상호의배틀필드 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int T, H, W, N, y, x;
    static char[][] map;
    static char[] move;
    static int[] dy = { -1, 1, 0, 0 };   // 상하좌우
    static int[] dx = { 0, 0, -1, 1 };
    static char[] dir = { '^', 'v', '<', '>' };
    static char[] moveDir = { 'U', 'D', 'L', 'R' };
    public static void main(String[] args) throws Exception{
        T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= T; tc++){
            sb.append("#").append(tc).append(" ");
            // 입력
            st = new StringTokenizer(br.readLine());
            H = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());

            map = new char[H][W];
            for (int i = 0; i < H; i++){
                String str = br.readLine();
                for (int j = 0; j < W; j++){
                    map[i][j] = str.charAt(j);
                    if (map[i][j] == '^' || map[i][j] == 'v' || map[i][j] == '<' || map[i][j] == '>'){
                        y = i;
                        x = j;
                    }
                }
            }

            N = Integer.parseInt(br.readLine());
            move = new char[N];
            move = br.readLine().toCharArray();

            // 실행
            for (int i = 0; i < N; i++){
                if (move[i] == 'S')
                    shoot();
                else
                    go(move[i]);
            }

            for (int i = 0; i < H; i++) {
                for (int j = 0; j < W; j++) {
                    sb.append(map[i][j]);
                }
                sb.append("\n");
            }

        }

        System.out.println(sb);

    }

    // 포탄은 벽에 충돌하거나 맵 밖으로 나갈때까지 직진
    // 1.벽돌벽 - 벽 파괴 후 평지
    // 2.강철벽, 맵 밖으로 나감 - 아무일 X
    static void shoot(){
        int d = getDirection(map[y][x]);
        int ny = y;
        int nx = x;

        while (true){
            ny += dy[d];
            nx += dx[d];

            if (!isInside(ny, nx)) break;   // 맵 밖으로 나갔다면

            if (map[ny][nx] == '*'){
                map[ny][nx] = '.';
                break;
            } else if (map[ny][nx] =='#'){
                break;
            }
        }
    }

    // 방향 전환 후 직진
    static void go(char move){
        int d = -1;
        for (int i = 0; i < 4; i++){
            if (move == moveDir[i])
                d = i;
        }
        map[y][x] = dir[d];   // 방향 바꾸고

        int ny = y + dy[d];
        int nx = x + dx[d];
        if (!isInside(ny, nx)) return;

        if (map[ny][nx] == '.'){
            map[ny][nx] = map[y][x];
            map[y][x] = '.';
            y = ny;
            x = nx;
        }
    }

    static boolean isInside(int y, int x){
        if (y < 0 || y >= H || x < 0 || x >= W)
            return false;
        return true;
    }

    static int getDirection(char tank){
        if (tank == '^')
            return 0;
        else if (tank == 'v') {
            return 1;
        } else if (tank == '<') {
            return 2;
        } else if (tank == '>') {
            return 3;
        } else
            return -1;
    }
}
