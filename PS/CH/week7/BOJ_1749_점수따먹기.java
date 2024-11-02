//BOJ_1749_점수따먹기
import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] board = new int[n+1][m+1];

        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= m; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int[][] sum = new int[n+1][m+1];

        for (int i = 1; i <= n; i++) {
            sum[i][1] = board[i][1];
            for (int j = 2; j <= m; j++) {
                sum[i][j] += sum[i][j - 1] + board[i][j];
            }
        }


        for (int i = 1; i < m+1; i++) {
            for (int j = 2; j < n+1; j++) {
                sum[j][i] += sum[j-1][i];
            }
        }




        int max = Integer.MIN_VALUE;
        for(int x1 =1; x1<n+1; x1++){
            for(int y1 = 1; y1<m+1; y1++){


                for(int x2 = x1; x2<n+1; x2++){
                    for(int y2 = y1; y2<m+1; y2++){
                        int d = 0;

                        d+=sum[x1-1][y2];
                        d+=sum[x2][y1-1];
                        d-=sum[x1-1][y1-1];

                        max = Math.max(max,sum[x2][y2] -d);
                    }
                }

            }
        }

        System.out.println(max);
    }
}