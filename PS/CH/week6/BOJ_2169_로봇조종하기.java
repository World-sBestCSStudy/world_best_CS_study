//BOJ_2169_로봇조종하기

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int N, M;
    static int[][] board, dp;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        board = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int[][] temp = new int[2][M];
        dp = new int[N][M];
        dp[0][0] = board[0][0];
        for(int i =1; i<M; i++) dp[0][i] = dp[0][i-1] + board[0][i];

        for(int i =1; i<N; i++){
            temp[0][0] = dp[i-1][0] + board[i][0];
            for(int j = 1; j<M; j++) {
                temp[0][j] = Math.max(temp[0][j-1], dp[i-1][j])+ board[i][j];
            }

            temp[1][M-1] = dp[i-1][M-1] + board[i][M-1];
            for(int j = M-2; j>=0; j--){
                temp[1][j] = Math.max(temp[1][j+1],dp[i-1][j]) + board[i][j];
            }



            for(int j=0; j<M; j++){
                dp[i][j] = Math.max(temp[0][j],temp[1][j]);
            }

        }

        System.out.println(dp[N-1][M-1]);
    }
}