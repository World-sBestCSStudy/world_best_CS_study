import java.io.*;
import java.util.*;

public class BOJ_3190_뱀 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int N, K, L, curDir, time;
    static Queue<int[]> snake = new ArrayDeque<>();

    static int[][] map;
    static Queue<int[]> turns = new ArrayDeque<>();
    static int[] headPos = new int[2];

    static int[] dr = {-1, 0, 1, 0};    // 상우하좌
    static int[] dc = {0, 1, 0, -1};


    public static void main(String[] args) throws Exception{
        N = Integer.parseInt(br.readLine());
        K = Integer.parseInt(br.readLine());

        map = new int[N+1][N+1];    // 0 dummy
        for (int k = 0; k < K; k++){
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            map[r][c] = 2;  // apple
        }

        L = Integer.parseInt(br.readLine());
        for (int l = 0; l < L; l++){
            st = new StringTokenizer(br.readLine());
            int X = Integer.parseInt(st.nextToken());
            char C = st.nextToken().charAt(0);

            turns.offer(new int[]{X, C=='L'? -1: 1});     // 1:좌, 2:우
        }

        // 초기화
        map[1][1] = 1;  // snake 초기 위치
        headPos[0] = 1;
        headPos[1] = 1;
        curDir = 1;    // 초기 방향
        snake.offer(new int[]{1,1});    // 초기 뱀의 위치
        time = 0;    // 종료까지 시간
        while (true){
            time++;
            // 머리 먼저 이동
            // -> 벽/자신의 몸인지 체크 -> 게임 종료
            // -> 사과인지 체크 -> 사과 먹고 꼬리 고정 / 꼬리도 따라 이동
            if (!go()){
                System.out.println(time);
                return;
            }

            // 방향 전환해야한다면 방향 전환!
            if (!turns.isEmpty() && turns.peek()[0] == time){
                turn(turns.poll()[1]);
            }
        }

    }

    static boolean go(){
        int sr = headPos[0];
        int sc = headPos[1];

        int nr = sr + dr[curDir];
        int nc = sc + dc[curDir];

        if (!isMap(nr, nc)) return false;   // 벽에 부딫히면(= 이동해야하는 곳이 맵 밖이라면)
        if (map[nr][nc] == 1) return false; // 자신의 몸과 부딫히면

        // 머리 이동
//        System.out.println("머리 이동: "+nr +", "+nc + " & time: "+time);

        // 사과 O -> 사과 먹고 + 꼬리 고정 => 아무일도 안 일어남
        if (map[nr][nc] != 2){  // 사과 X -> 꼬리 따라옴 => 꼬리 있던 위치 제거
            int[] last = snake.poll();
//            System.out.println("꼬리 삭제: "+last[0] + ", "+last[1]);
            map[last[0]][last[1]] = 0;
        }

        map[nr][nc] = 1;
        headPos[0] = nr;
        headPos[1] = nc;
        snake.offer(new int[]{nr, nc});     // 뱀 이동 경로 기록

        return true;
    }

    static void turn(int d){
        curDir = (curDir + 4 + d) % 4;
    }

    static boolean isMap(int y, int x){
        if (y <= 0 || y > N || x <= 0 || x > N) return false;
        return true;
    }
}
