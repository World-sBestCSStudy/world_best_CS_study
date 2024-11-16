// BOJ 17406. 배열 돌리기 4

import java.io.*;
import java.util.*;

public class boj_17406 {

    static StringBuilder sb = new StringBuilder();
    static int N, M, K, r, c, s, ans;   // (r-s, c-s) ~ (r+s, c+s) 인 정사각형을 시계방향으로 회전
    static int[][] A, map;
    static int[] tgt;
    static boolean[] select;

    static int[] dr = {-1, 0, 1, 0};

    static List<Info> infos = new ArrayList<>();
    static class Info{
        int r, c, s;
        public Info(int r, int c, int s){
            this.r = r;
            this.c = c;
            this.s = s;
        }
    }

    // 1. 배열 연산 순서 정하기
    // 2. 각 배열의 행의 최소합 구하기
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        A = new int[N][M];
        map = new int[N][M];
        ans = Integer.MAX_VALUE;
        for (int r = 0; r < N; r++){
            st = new StringTokenizer(br.readLine());
            for (int c = 0; c < M; c++){
                A[r][c] = Integer.parseInt(st.nextToken());
            }
        }

        copyFromA();

        for (int k = 0; k < K; k++){
            st = new StringTokenizer(br.readLine());
            r = Integer.parseInt(st.nextToken()) - 1;
            c = Integer.parseInt(st.nextToken()) - 1;
            s = Integer.parseInt(st.nextToken());

            infos.add(new Info(r,c,s));
        }

        tgt = new int[infos.size()];
        select = new boolean[infos.size()];
        perm(0);

        System.out.println(ans);
    }

    static void copyFromA(){
        for (int i = 0; i < N; i++){
            for (int j = 0; j < M; j++){
                map[i][j] = A[i][j];
            }
        }
    }

    static void perm(int idx){
        if (idx == infos.size()){
//            System.out.println(Arrays.toString(tgt));
            rotate(tgt);
            return;
        }

        for (int i = 0; i < infos.size(); i++){
            if (select[i]) continue;

            tgt[idx] = i;
            select[i] = true;
            perm(idx+1);
            select[i] = false;
        }
    }

    // 정해진 연산의 순서대로 회전 시켰을 경우
    static void rotate(int[] tgt){
        for (int i = 0; i < tgt.length; i++){
            int idx = tgt[i];
            Info info = infos.get(idx);

            for (int j = 1; j <= info.s; j++){
                spin(info.r, info.c, j);
            }

        }

        // 배열의 값을 구해서 더 작은 값으로 갱신
        for (int i = 0; i < N; i++){
            int sum = 0;
            for (int j = 0; j < M; j++){
                sum += map[i][j];
                if (sum > ans) break;
            }
            if (ans > sum){
                ans = sum;
            }
        }

        copyFromA();    // 원복
    }

    // r,c 중심으로 j 떨어진 곳 시계방향 회전
    static void spin(int r, int c, int s){
        int temp = map[r-s][c-s];
        // (r-s, c-s) ~ (r+s, c+s)
        for (int i = r-s; i < r+s; i++){    // 맨 왼쪽 요소들을 한칸 아래애서 한칸 위로 올린다.
            map[i][c-s] = map[i+1][c-s];
        }
        for (int i = c-s; i < c+s; i++){    // 맨 아랫쪽 요소를 한칸씩 오른쪽에서 왼쪽으로 당긴다.
            map[r+s][i] = map[r+s][i+1];
        }
        for (int i = r+s; i > r-s; i--){    // 맨 오른쪽 요소들을 한칸 위애서 한칸 아래로 내린다.
            map[i][c+s] = map[i-1][c+s];
        }
        for (int i = c+s; i > c-s; i--) {    // 맨 윗쪽 요소를 한칸씩 왼쪽에서 오른쪽으로 민다.
            map[r-s][i] = map[r-s][i-1];
        }
        map[r-s][c-s+1] = temp;
    }

}
