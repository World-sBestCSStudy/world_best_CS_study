// SWEA 1238. Contact

import java.io.*;
import java.util.*;

public class SWEA_1238_Contact {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int L, start, ans;
    static List<List<Integer>> list;
    static boolean[] visit;

    public static void main(String[] args) throws Exception{
        for (int tc = 1; tc <= 10; tc++){
            sb.append("#").append(tc).append(" ");

            list = new ArrayList<>();
            list.clear();
            for (int i = 0; i <= 100; i++){
                list.add(new ArrayList<>());
            }
            visit = new boolean[101];

            st = new StringTokenizer(br.readLine());
            L = Integer.parseInt(st.nextToken());
            start = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < L/2; i++){
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                list.get(from).add(to);
            }

            ans = start;
            bfs(start);

            sb.append(ans).append("\n");
        }

        System.out.println(sb);
    }

    static void bfs(int start){
        Queue<Integer> queue = new ArrayDeque<>();
        visit[start] = true;
        queue.add(start);

        while (!queue.isEmpty()){
            int size = queue.size();
            int tMax = 0;

            for (int i = 0; i < size; i++){
                int cur = queue.poll();

                for (int j = 0; j < list.get(cur).size(); j++){
                    int next = list.get(cur).get(j);
                    if (visit[next]) continue;

                    visit[next] = true;
                    queue.add(next);
                    tMax = Math.max(tMax, next);
                }

                if (tMax != 0){
                    ans = tMax;
                }
            }
        }
    }
}
