import java.io.*;
import java.util.*;

public class BOJ_6987_월드컵 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int ans;
    static int[][] match = new int[15][2];  // 6C2
    static int[] win, draw, lose;
    public static void main(String[] args) throws Exception{
        // 각 팀이 서로 한 번씩 경기한다. -> 6C2 = 15게임
        // 0 0 0 0 0 1 1 1 1 2 2 2 3 3 4
        // 1 2 3 4 5 2 3 4 5 3 4 5 4 5 5
        int idx = 0;
        for (int i = 0; i < (6-1); i++){
            for (int j = i+1; j < 6; j++){
                match[idx][0] = i;
                match[idx][1] = j;
                idx++;
            }
        }

        win = new int[6];
        draw = new int[6];
        lose = new int[6];

        for (int tc = 0; tc < 4; tc++){
            // 입력
            int sum = 0;

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < 6; i++){
                sum += win[i] = Integer.parseInt(st.nextToken());
                sum += draw[i] = Integer.parseInt(st.nextToken());
                sum += lose[i] = Integer.parseInt(st.nextToken());
            }

            if (sum != 30){
                sb.append("0 ");
                continue;
            }


            if (dfs(0))
                sb.append("1 ");
            else
                sb.append("0 ");

        }

        // 출력
        System.out.println(sb);
    }

    static boolean dfs(int idx){
        // complete code
        if (idx == 15){
            return true;
        }

        // 경기 진행
        int teamA = match[idx][0];
        int teamB = match[idx][1];

        // A win && B lose
        if (win[teamA] > 0 && lose[teamB] > 0){
            win[teamA]--;
            lose[teamB]--;

            if (dfs(idx + 1)) return true;

            win[teamA]++;
            lose[teamB]++;
        }

        // A lose && B win
        if (lose[teamA] > 0 && win[teamB] > 0) {
            lose[teamA]--;
            win[teamB]--;

            if (dfs(idx + 1)) return true;

            lose[teamA]++;
            win[teamB]++;
        }

        // draw A & B
        if (draw[teamA] > 0 && draw[teamB] > 0) {
            draw[teamA]--;
            draw[teamB]--;

            if (dfs(idx + 1)) return true;

            draw[teamA]++;
            draw[teamB]++;
        }

        return false;
    }
}
