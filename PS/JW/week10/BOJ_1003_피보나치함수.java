// BOJ 1003. 피보나치 함수

import java.io.*;
import java.util.*;

public class boj_1003 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception{
        int T = Integer.parseInt(br.readLine());
        int[][] f = new int[41][2];
        f[0][0] = 1;
        f[1][1] = 1;
        f[2][0] = 1;
        f[2][1] = 1;
        for (int i = 2; i < 41; i++){
            f[i][0] = f[i-1][0] + f[i-2][0];
            f[i][1] = f[i-1][1] + f[i-2][1];
        }
        for (int tc = 1; tc <= T; tc++){
            int N = Integer.parseInt(br.readLine());

            System.out.println(f[N][0] +" "+ f[N][1]);
        }
    }
}
