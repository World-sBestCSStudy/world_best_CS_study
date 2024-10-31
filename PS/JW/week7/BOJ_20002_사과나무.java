import java.io.*;
import java.util.*;

public class BOJ_20002_사과나무 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception{
        // 입력
        int N = Integer.parseInt(br.readLine());
        int[][] input = new int[N+1][N+1];    // 0 dummy
        int max = Integer.MIN_VALUE;
        for (int i = 1; i <= N; i++){
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++){
                input[i][j] = Integer.parseInt(st.nextToken());   // 총이익 -1000~1000
                max = Math.max(max, input[i][j]);   // 1칸만으로 총이익이 최대인 경우
            }
        }

        // 누적합 배열 구하기
        int[][] output = new int[N+1][N+1];
        for (int i = 1; i <= N; i++){
            for (int j = 1; j <= N; j++){
                output[i][j] = output[i][j-1] + output[i-1][j] + input[i][j] - output[i-1][j-1];
            }
        }

        int answer = Math.max(max, output[N][N]);
        for (int k = 1; k < N; k++){   // k = 2 ~ N 까지
            for (int i = 1; i <= N-k; i++){
                for (int j = 1; j <= N-k; j++){
                    int profit = output[i+k][j+k] - output[i+k][j-1] - output[i-1][j+k] + output[i-1][j-1];
                    answer = Math.max(answer, profit);
                }
            }
        }

        System.out.println(answer);
    }
}
