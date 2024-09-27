import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BOJ_9663_NQueen {
    static int N, ans = 0;
    static int[][] map;
    static int[] select;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        map = new int[N][N];
        select = new int[N];
        nQueen(0);

        System.out.println(ans);
    }

    static void nQueen(int tgtIdx){
        if (tgtIdx == N){
            ans++;
            return;
        }

        for (int i = 0; i < N; i++){
            select[tgtIdx] = i;
            if (promising(tgtIdx)){
                nQueen(tgtIdx+1);
            }
        }
    }

    static boolean promising(int tgtIdx){
        for (int i = 0; i < tgtIdx; i++){
            if (select[i] == select[tgtIdx] || tgtIdx - i == Math.abs(select[tgtIdx] - select[i]))
                return false;
        }
        return true;
    }
}
