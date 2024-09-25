package algorithm2024.sep.day25;

import java.io.*;
import java.util.*;

public class BOJ_2138_전구와스위치 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int N;
    static boolean[] fromLights;
    static boolean[] toLights;

    static int ans = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(br.readLine());
        fromLights = new boolean[N];
        toLights = new boolean[N];
        String[] s = br.readLine().split("");
        for (int i = 0; i < N; i++) {
            fromLights[i] = s[i].equals("1");
        }
        s = br.readLine().split("");
        for (int i = 0; i < N; i++) {
            toLights[i] = s[i].equals("1");
        }

        check(0, 0);
        System.out.println(ans==Integer.MAX_VALUE?-1:ans);
    }

    static void check(int idx, int cnt) {
//        System.out.println(Arrays.toString(fromLights));
        if (idx == N) {
            if (toLights[N - 1] == fromLights[N - 1]) {
                ans = Math.min(ans, cnt);
            }
            return;
        }
        if (idx==0||(idx > 0 && fromLights[idx - 1] != toLights[idx - 1])) {
            click(fromLights, idx);
            check(idx + 1, cnt + 1);
            click(fromLights, idx);
        }
        if (idx==0||(idx > 0 && fromLights[idx - 1] == toLights[idx - 1])) {
            check(idx + 1, cnt);
        }

    }

    static void click(boolean[] lights, int idx) {
        if (idx > 0) {
            lights[idx - 1] = !lights[idx - 1];
        }
        if (idx < N - 1) {
            lights[idx + 1] = !lights[idx + 1];
        }
        lights[idx] = !lights[idx];
    }
}
