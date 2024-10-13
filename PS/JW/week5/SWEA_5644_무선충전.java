import java.io.*;
import java.util.*;

// Battery Charger - location (x,y), coverage c, perfomance p
// 거리 = |x1 - x2| + |y1 - y2|
// 겹치는 경우 선택해서 접속
// A와 B의 이동경로가 주어짐. 이때 그 범위 내에 들어오면 P만큼 충전가능하다. (두명이 같은 곳이면 나눠서 충전)
// 모든 사용자가 충전한 양의 합의 최댓값 구하기!
// A는 (1,1)에서 B는 (10,10)에서 출발. 총 이동시간 M (20 <= M <= 100)
// BC의 개수 A 1~8, C 1~4, P 10~500
// 0초부터 충전가능!! 같은 위치에 2개의 BC가 설치되는 경우는 없다. A,B가 동시에 같은 위치일 수는 있음.

public class SWEA_5644_무선충전 { // SWEA 5644. 무선 충전
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int T, M, A, ans;
    static int[] moveA, moveB;
    static List<BC> batteries = new ArrayList<>();

    static int ay, ax, by, bx;
    static int[] dy = {0, -1, 0, 1, 0}; // . ^ > v <
    static int[] dx = {0, 0, 1, 0, -1};

    static class BC{
        int x, y, c, p;
        private BC(int x, int y, int c, int p){
            this.x = x;
            this.y = y;
            this.c = c;
            this.p = p;
        }
    }

    public static void main(String[] args) throws Exception{
        T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= T; tc++){
            st = new StringTokenizer(br.readLine());
            M = Integer.parseInt(st.nextToken());   // 움직이는 횟수
            A = Integer.parseInt(st.nextToken());   // 충전기 개수

            // A와 B의 움직임 입력
            moveA = new int[M];
            moveB = new int[M];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < M; i++){
                moveA[i] = Integer.parseInt(st.nextToken());
            }
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < M; i++){
                moveB[i] = Integer.parseInt(st.nextToken());
            }

            // 충전기 입력
            batteries.clear();
            for (int a = 0; a < A; a++){
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                int p = Integer.parseInt(st.nextToken());

                batteries.add(new BC(x, y, c, p));
            }

            ans = 0;
            ay = 1; ax = 1; by = 10; bx = 10;
            charge();
            for (int i = 0; i < M; i++){
                ay += dy[moveA[i]];
                ax += dx[moveA[i]];
                by += dy[moveB[i]];
                bx += dx[moveB[i]];
                charge();
            }


            sb.append("#").append(tc).append(" ").append(ans).append("\n");
        }

        System.out.println(sb);
    }

    static void charge(){
        int max = 0;
        for (int i = 0; i < A; i++){
            for (int j = 0; j < A; j++){
                int sum = 0;

                int Pa = getP(batteries.get(i), ay, ax);
                int Pb = getP(batteries.get(j), by, bx);

                if (Pa == 0 && Pb == 0) continue;

                if (i != j){
                    sum = Pa + Pb;
                } else {
                    sum = Math.max(Pa, Pb);
                }

                max = Math.max(max, sum);
            }
        }

        ans += max;
    }

    static int getP(BC bc, int y, int x){
        if (Math.abs(bc.y-y)+Math.abs(bc.x-x) <= bc.c) return bc.p;
        return 0;
    }
}