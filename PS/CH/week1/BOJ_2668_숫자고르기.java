//BOJ_2668_숫자고르기

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[][] numbers;
    static boolean[] v;
    static int n;
    static List<Integer> list = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        n = Integer.parseInt(br.readLine());
        numbers = new int[2][n + 1];
        v = new boolean[n + 1];

        for (int i = 1; i < n + 1; i++) {
            numbers[0][i] = i;
            numbers[1][i] = Integer.parseInt(br.readLine());
        }

        for (int i = 1; i < n + 1; i++) {
            dfs(i, numbers[1][i]);
        }

        StringBuilder answer = new StringBuilder();
        answer.append(list.size()).append("\n");
        for (int i : list) answer.append(i).append("\n");
        System.out.println(answer);


    }

    public static void dfs(int start, int target) {
        if (start == target) {
            list.add(start);
            return;
        }
        if (!v[numbers[1][target]]) {
            v[numbers[1][target]] = true;
            dfs(start, numbers[1][target]);
            v[numbers[1][target]] = false;
        }

    }

}