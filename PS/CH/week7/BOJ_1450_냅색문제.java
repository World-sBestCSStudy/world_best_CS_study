//BOJ_1450_냅색문제

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N, C;
    static int[] w;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());


        w = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            w[i] = Integer.parseInt(st.nextToken());
        }

        List<Long> leftSum = new ArrayList<>();
        List<Long> rightSum = new ArrayList<>();

        dfs(leftSum, 0, N / 2, 0);
        dfs(rightSum, N / 2, N, 0);

        Collections.sort(rightSum);

        long answer = 0;
        for (long k : leftSum) {
            answer += upper(rightSum, C - k);
        }

        System.out.println(answer);
    }

    public static int upper(List<Long> rightSum, long key) {
        int left = 0, right = rightSum.size();
        while (left < right) {
            int mid = (left + right) / 2;

            if (rightSum.get(mid) <= key) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    public static void dfs(List<Long> sumList, int start, int end, long sum) {
        if (C < sum) return;
        if (start == end) {
            sumList.add(sum);
            return;
        }
        dfs(sumList, start + 1, end, sum);
        dfs(sumList, start + 1, end, sum + w[start]);
    }
}