package algorithm2024.sep.day24;

import java.io.*;
import java.util.*;

public class BOJ_7490_0만들기 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();
    static char[] operators = {' ', '+', '-'};

    public static void main(String[] args) throws Exception {
        int T = Integer.parseInt(br.readLine());
        for (int t = 0; t < T; t++) {
            int N = Integer.parseInt(br.readLine());
            char[] operatorList = new char[N];
            operatorList[0] = '+';
            operate(N, 1, operatorList);
            sb.append("\n");
        }
        System.out.println(sb);
    }

    static void operate(int N, int idx, char[] operatorList) {
        if (idx == N) {
            int ans = 0;
            StringBuilder temp = new StringBuilder();
            for (int i = 0; i < N; i++) {
                char operator = operatorList[i];
                temp = new StringBuilder(Integer.toString(i + 1));
                while (i < N - 1 && operatorList[i+1] == ' ') {
                    temp.append(++i + 1);
                }
                switch (operator) {
                    case '+':
                        ans += Integer.parseInt(temp.toString());
                        temp = new StringBuilder();
                        break;
                    case '-':
                        ans -= Integer.parseInt(temp.toString());
                        temp = new StringBuilder();
                        break;
                }
            }
            if (ans == 0) {
                sb.append("1");
                for (int i = 1; i < operatorList.length; i++) {
                    sb.append(operatorList[i]);
                    sb.append(i + 1);
                }
                sb.append("\n");
            }
            return;
        }

        for (int i = 0; i < 3; i++) {
            operatorList[idx] = operators[i];
            operate(N, idx + 1, operatorList);
        }
    }
}