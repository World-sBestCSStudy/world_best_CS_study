// SWEA 1247. 최적 경로

import java.io.*;
import java.util.*;

public class SWEA_1247_최적경로 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();
    static Pos home, company;
    static List<Pos> customer;
    static int T, N, x, y, answer;
    static int[] tgt;
    static boolean[] select;


    // 회사, 집 좌표 + 2~10명의 고객 좌표 => 회사 -> 모든 고객 -> 집 경로 중 이동거리가 가장 짧은 경로 찾기
    public static void main(String[] args) throws Exception{
        T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= T; tc++){
            sb.append("#").append(tc).append(" ");

            N = Integer.parseInt(br.readLine());
            tgt = new int[N];
            select = new boolean[N];

            st = new StringTokenizer(br.readLine().trim());
            company = new Pos(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            home = new Pos(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            customer = new LinkedList<>();
            for (int i = 0; i < N; i++){
                x = Integer.parseInt(st.nextToken());
                y = Integer.parseInt(st.nextToken());
                customer.add(new Pos(x, y));
            }

            answer = Integer.MAX_VALUE;
            perm(0, 0);

            sb.append(answer).append("\n");
        }

        System.out.println(sb);
    }

    static void perm(int tgtIdx, int sum){
        if (tgtIdx == N){
            // complete code
//            int sum = distance(company, customer.get(tgt[0]));
//            for (int i = 0; i < N-1; i++){
//                sum += distance(customer.get(tgt[i]), customer.get(tgt[i+1]));
//            }

            sum += distance(customer.get(tgt[N-1]), home);
            answer = Math.min(answer, sum);
            return;
        }

        for (int i = 0; i < N; i++){
            if (select[i]) continue;

            tgt[tgtIdx] = i;

            int d = 0;
            if (tgtIdx == 0){
                d = distance(company, customer.get(tgt[0]));
            } else {
                d = distance(customer.get(tgt[tgtIdx-1]), customer.get(tgt[tgtIdx]));
            }

            if (sum + d < answer){
                select[i] = true;
                perm(tgtIdx+1,sum + d);
                select[i] = false;
            }
        }
    }

    static int distance(Pos p1, Pos p2){   // distance = |x1-x2| + |y1-y2|
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
    }

    static class Pos{
        int x, y;
        Pos(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
}


