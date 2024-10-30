// SWEA 3124. 최소 스패닝 트리

import java.io.*;
import java.util.*;

// 주어지는 정점, 간선 수가 많음 => 효율을 위해 Prim 알고리즘 사용하자
// 근데 테케는 Kruskal이 더 오래 걸림... 왜요
public class SWEA_3124_최소스패닝트리 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int T, V, E;
    static long answer;
    static List<List<Edge>> list;
    static boolean[] visit;
    static PriorityQueue<Edge> pq = new PriorityQueue<>((e1, e2) -> e1.c - e2.c);

    public static void main(String[] args) throws Exception{
        T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= T; tc++){
            sb.append("#").append(tc).append(" ");

            // 입력
            st = new StringTokenizer(br.readLine());
            V = Integer.parseInt(st.nextToken());
            E = Integer.parseInt(st.nextToken());

            list = new ArrayList<>();
            for (int i = 0; i <= V; i++){
                list.add(new ArrayList<>());
            }
            visit = new boolean[V+1];

            for (int i = 0; i < E; i++){
                st = new StringTokenizer(br.readLine());
                int A = Integer.parseInt(st.nextToken());
                int B = Integer.parseInt(st.nextToken());
                int C = Integer.parseInt(st.nextToken());

                list.get(A).add(new Edge(B, C));
                list.get(B).add(new Edge(A, C));
            }

            // 풀이
            answer = 0;
            pq.clear();

            prim();

            sb.append(answer).append("\n");
        }
        System.out.println(sb);
    }

    static void prim(){
        int cnt = 1;    // 선택된 정점의 수
        visit[1] = true;
        pq.addAll(list.get(1)); // 1번 정점으로부터 갈 수 있는 모든 간선을 추가한다.

        while (!pq.isEmpty()){
            Edge edge = pq.poll();
            if (visit[edge.v2]) continue;

            visit[edge.v2] = true;
            answer += edge.c;
            cnt++;

            if (cnt == V) break;

            for (Edge e : list.get(edge.v2)){
                if (!visit[e.v2]) pq.add(e);
            }
        }
    }

    static class Edge {
        int v2, c;
        public Edge(int v2, int c){
            this.v2 = v2;
            this.c = c;
        }
    }
}
