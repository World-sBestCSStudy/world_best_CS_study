// BOJ 12851. 숨바꼭질2

import java.io.*;
import java.util.*;

// X -> X+1 / X-1 / 2 * X
public class BOJ_12851_숨바꼭질2 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int N, K;
    static int minTime = Integer.MAX_VALUE, count = 0;
    static int[] time = new int[100_001];
    static Queue<Integer> queue = new LinkedList<>();

    public static void main(String[] args) throws Exception{
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        if (N >= K){    // 동생보다 누나가 앞에 있을 때 (-로만 이동해서 와야함)
            System.out.println(N-K);
            System.out.println(1);
            return;
        }

        bfs();

        System.out.println(minTime);
        System.out.println(count);
    }

    static void bfs(){
        time[N] = 1;   // 출발지의 depth 계산을 위한 1 ===> 마지막에 -1 해주어야함.
        queue.offer(N);

        while (!queue.isEmpty()) {
            int cur = queue.poll();

            if (minTime < time[cur]) return;

            for (int next : new int[]{cur - 1, cur + 1, cur * 2}) {
                if (next >= 0 && next <= 100_000) {
                    if (next == K) {
//                        System.out.println("next: " + next + " , now: " + cur);
                        minTime = time[cur];
                        count++;
                    }

                    if (time[next] == 0 || time[next] == time[cur] + 1) {
                        queue.offer(next);
                        time[next] = time[cur] + 1;
                    }


                }
            }
        }
    }
}
