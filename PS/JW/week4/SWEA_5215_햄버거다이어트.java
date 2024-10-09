import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class SWEA_5215_햄버거다이어트 {
    static int TC, N, L, score;
    static boolean[] select;
    static List<Source> src;

    static class Source {
        int taste, kcal;
        public Source(int t, int k){
            this.taste = t;
            this.kcal = k;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuffer sb = new StringBuffer();

        TC = Integer.parseInt(st.nextToken());
        for (int tc = 1; tc <= TC; tc++){
            st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            L = Integer.parseInt(st.nextToken());

            src = new ArrayList<>();
            select = new boolean[N];
            for (int i = 0; i < N; i++){
                st = new StringTokenizer(br.readLine());
                int t = Integer.parseInt(st.nextToken());
                int k = Integer.parseInt(st.nextToken());
                src.add(new Source(t, k));
            }

            score = Integer.MIN_VALUE;
            subset(0);

            sb.append("#").append(tc).append(" ").append(score).append("\n");
        }
        System.out.println(sb);

    }

    static void subset(int idx){
        if (idx == N){
            // 선호도 계산 -> 갱신
            int sumK = 0;
            int sumT = 0;
            for (int i = 0; i < N; i++){
                if (select[i]){
                    sumT += src.get(i).taste;
                    sumK += src.get(i).kcal;
                }
            }

            if (sumK <= L){
                score = Math.max(score,sumT);
            }

            return;
        }

        select[idx] = true;
        subset(idx + 1);
        select[idx] = false;
        subset(idx + 1);
    }
}