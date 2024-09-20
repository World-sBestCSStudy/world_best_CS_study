package algorithm2024.sep.day20;

import java.io.*;
import java.util.*;

public class BOJ_2668_숫자고르기 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {
        int N = Integer.parseInt(br.readLine());
        int[] arr = new int[N + 1];
        int cnt = 0;
        int[] check = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            arr[i] = Integer.parseInt(br.readLine());
            if (arr[i] == i) check[i] = 1;
        }

        for (int i = 1; i <= N; i++) {
            if (check[i] == 0) {
                HashSet<Integer> upSet = new HashSet<>();
                HashSet<Integer> downSet = new HashSet<>();
                checkArr(arr, i, upSet, downSet, check);
            }
            if(check[i]==1){
                cnt++;
            }
        }

        sb.append(cnt).append("\n");
        for (int i = 1; i <= N; i++) {
            if (check[i] == 1) {
                sb.append(i).append("\n");
            }
        }
        System.out.println(sb);

    }

    static void checkArr(int[] arr, int i, HashSet<Integer> upSet, HashSet<Integer> downSet, int[] check) {
        upSet.add(i);
        downSet.add(arr[i]);
        if (upSet.contains(arr[i])) {
            if (upSet.size() == downSet.size()) {
//                System.out.println(upSet);
                for (int n : upSet) {
                    check[n] = 1;
                }
            }
            return;
        }
        if (check[arr[i]] == 0) {
//            System.out.println(i);
            checkArr(arr, arr[i], upSet, downSet, check);
        }
    }
}
