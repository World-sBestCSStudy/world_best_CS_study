//BOJ_18427_함께블록쌓기
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int H = Integer.parseInt(st.nextToken());
        int mod = 10_007;

        Node[] board= new Node[N+1];
        for (int i = 1; i < N + 1; i++) {
            st = new StringTokenizer(br.readLine());
            board[i] = new Node();
            while(st.hasMoreTokens()){
                board[i].block.add(Integer.parseInt(st.nextToken()));
            }
        }

        int[][] dp = new int[N + 1][H + 1];
        dp[0][0]=1;
        for(int i = 1; i<N+1; i++){
            dp[i][0] = 1;
            for(int j =1; j<H+1; j++){

                for(int x =0; x<board[i].block.size(); x++){
                    int b = board[i].block.get(x);
                    if(j<b) continue;

                    //현재 쌓을수 있으면
                    dp[i][j] += dp[i-1][j-b];
                    dp[i][j] %=mod;

                    //안놓는 경우
                }
                dp[i][j] += dp[i-1][j];
                dp[i][j] %=mod;

            }
        }

//        int[] idx = new int[H+1];
//        for(int i =1; i<H+1; i++) idx[i] = i;
//        System.out.println(Arrays.toString(idx));
//        for (int i = 1; i < N + 1; i++) System.out.println(Arrays.toString(dp[i]));
//
//        int sum = Arrays.stream(dp[N]).sum();
        System.out.println(dp[N][H]);


    }
    static class Node{
        List<Integer> block=new ArrayList<>();
    }
}