import java.io.*;
import java.util.*;

public class BOJ_17281_야구 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();
    static int N, sum, max = 0;
    static int[][] inning;
    static int[] tgt;
    static boolean[] visit, field;
    // 9명, 하나의 이닝 = 공격 & 수비, N번의 이닝, 한 이닝 -> 3아웃 -> 이닝 종료 -> 공수 변경
    // 시작 전 타순을 정하고(9 -> 1번 타자), 순서는 계속 유지됨(이전 이닝의 마지막 타자 다음 순서부터 다음 이닝 시작)
    // 1-2-3-홈에 도착하면 1점, 이닝 시작 시 주자는 없다.
    // 공을 쳐서 얻을 수 있는 결과: (모든 주자가) 안타(1루), 2루타(2루), 3루타(3루), 홈런(홈까지 진루), 아웃(진루X, 아웃+1)
    // 1번선수 = 4번타자. 가장 많은 득점을 하는 타순은?
    public static void main(String[] args) throws Exception{
        N = Integer.parseInt(br.readLine());
        inning = new int[N][9];
        for (int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 9; j++){
                inning[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        visit = new boolean[9];
        tgt = new int[9];
        field = new boolean[4];   // home 1, 2, 3루 상태 표시
        visit[0] = true;    // 1번 타자 4번째 순서 고정
        tgt[3] = 0;

        perm(0);

        System.out.println(max);
    }

    static void perm(int tgtIdx){
        if (tgtIdx == 9) {
            simulate();
            return;
        }

        for (int i = 1; i < 9; i++){
            if (visit[i]) continue;

            visit[i] = true;
            if (tgtIdx == 3) tgtIdx++;
            tgt[tgtIdx] = i;
            perm(tgtIdx+1);
            visit[i] = false;
        }
    }

    static void simulate(){
        sum = 0;
        int idx = 0;
        for (int i = 0; i < N; i++){
            Arrays.fill(field, false);  // 필드 초기화
            int out = 0;
            while (out < 3){
                int hitter = tgt[(idx++) % 9];   // 현재 타자
                int hit = inning[i][hitter];     // 타자가 이번 게임에서 얻은 결과
                field[0] = true;

                if (hit == 0){
                    field[0] = false;
                    out++;
                } else {
                    sum += getPoint(hit);
                }

                max = Math.max(sum, max);
            }
        }

        max = Math.max(sum, max);
    }

    static int getPoint(int hit){
        int point = 0;

        for (int i = 3; i >= 0; i--){
            if (!field[i]) continue;

            if (i+hit > 3) {
                point++;
                field[i] = false;
            }
            else {
                field[i+hit] = true;
                field[i] = false;
            }
        }

        return point;
    }
}