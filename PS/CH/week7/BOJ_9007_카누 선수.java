//BOJ_9007_카누 선수

import java.io.*;
import java.util.*;

public class Main {
    static int N, K;
    static int[][] w;

    public static void main(String[] args) throws IOException {


        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder answer = new StringBuilder();


        int T = Integer.parseInt(br.readLine());

        for (int t = 0; t < T; t++) {
            st = new StringTokenizer(br.readLine());
            K = Integer.parseInt(st.nextToken());
            N = Integer.parseInt(st.nextToken());

            w = new int[4][N];

            for (int i = 0; i < 4; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    w[i][j] = Integer.parseInt(st.nextToken());
                }
            }


            long[] leftSum = new long[N * N];
            long[] rightSum = new long[N * N];
            int idx = 0;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    leftSum[idx] = (long) (w[0][i] + w[1][j]);
                    rightSum[idx++] = (long) (w[2][i] + w[3][j]);
                }
            }

            Arrays.sort(rightSum);

            long min = Long.MAX_VALUE;
            for (long s : leftSum) {
                long number = lower(rightSum, s);

                if (Math.abs(K - (s + number)) < Math.abs(K - min)) {
                    min = s + number;
                } else if (Math.abs(K - (s + number)) == Math.abs(K - min)) {
                    min = Math.min(min, s + number);
                }
                if (min == K) break;
            }
            answer.append(min).append("\n");
        }
        System.out.println(answer);

    }

    public static long lower(long[] numbers, long key) {
        int left = 0, right = numbers.length;
        while (left < right) {
            int mid = (left + right) / 2;

            if (numbers[mid] + key < K) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }


        if (left == 0) {
            return numbers[left];
        } else if (left == numbers.length) {
            return numbers[left - 1];
        } else {

            //left와 left-1을 비교해야한다.
            long sum1 = Math.abs((K - numbers[left] - key));
            long sum2 = Math.abs((K - numbers[left - 1] - key));

            if (sum1 >= sum2) {
                return numbers[left - 1];
            } else {
                return numbers[left];
            }
        }

    }
}