// BOJ 15591. MooTube

import java.io.*;
import java.util.*;
public class boj_15591 {
    static StringBuilder sb = new StringBuilder();
    static int N, Q;
    static List<List<Edge>> adjList = new ArrayList<>();
    static boolean[] visit;

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());   // N ~ 5000
        Q = Integer.parseInt(st.nextToken());   // Q ~ 5000

        for (int i = 0; i <= N; i++){
            adjList.add(new ArrayList<>());
        }

        for (int i = 0; i < N-1; i++){
            st = new StringTokenizer(br.readLine());
            int p = Integer.parseInt(st.nextToken());
            int q = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());   // r ~ 10억

            adjList.get(p).add(new Edge(q, r));
            adjList.get(q).add(new Edge(p, r));
        }

        for (int i = 0; i < Q; i++){
            st = new StringTokenizer(br.readLine());
            int k = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            // 찾기
            sb.append(bfs(k, v)+"\n");
        }
        System.out.println(sb);
    }

    static int bfs(int k, int start){
        visit = new boolean[N+1];
        int cnt = 0;
        Queue<Integer> q = new LinkedList<>();
        q.offer(start);
        visit[start] = true;

        while (!q.isEmpty()){
            int cur = q.poll();

            for (int i = 0; i < adjList.get(cur).size(); i++){
                int idx = adjList.get(cur).get(i).idx;
                int cost = adjList.get(cur).get(i).cost;

                if (cost < k || visit[idx]) continue;

                q.offer(idx);
                visit[idx] = true;
                cnt++;
            }
        }
        return cnt;
    }

    static class Edge {
        int idx, cost;
        public Edge(int q, int r){
            this.idx = q;
            this.cost = r;
        }
    }

}
