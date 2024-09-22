package algorithm2024.sep.day22;

import java.io.*;
import java.util.*;

public class BOJ_22251_빌런호석 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int N, K, P;
    static String X;

    static boolean[][] nums = {
            {true, true, true, false, true, true, true},
            {false, false, true, false, false, true, false},
            {true, false, true, true, true, false, true},
            {true, false, true, true, false, true, true},
            {false, true, true, true, false, true, false},
            {true, true, false, true, false, true, true},
            {true, true, false, true, true, true, true},
            {true, false, true, false, false, true, false},
            {true, true, true, true, true, true, true, true},
            {true, true, true, true, false, true, true}
    };


    public static void main(String[] args) throws Exception {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        P = Integer.parseInt(st.nextToken());
        X = st.nextToken();

//        X의 앞자리에 0을 채워주는 작업
        while(X.length()<K){
            X = new StringBuilder("0").append(X).toString();
        }

//        X를 숫자의 배열로 변경
        int[] floor = new int[K];
        for (int i = 0; i < X.length(); i++) {
            floor[i] = X.charAt(i) - '0';
        }

        int ans =0 ;
        for (int i = 1; i <= N; i++) {
//            각 자리별로 숫자들과 비교해보고 바꿔야 하는 LED 수가 P이하라면 카운트.
            if (i == Integer.parseInt(X)) continue;
            int cnt = 0;
            int idx = 0;
//            첫번째 자릿수부터 변경해야 하는 횟수 카운트
            for (int j = (int) Math.pow(10, K-1); j > 0; j/=10) {
                int n = i%(j*10)/j;
                for (int k = 0; k < 7; k++) {
                    if(nums[n][k]!=nums[floor[idx]][k])cnt++;
                }
                idx++;
            }
            if(cnt<=P) ans++;
        }
        System.out.println(ans);
    }
}
