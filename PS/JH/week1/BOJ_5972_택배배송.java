package algorithm2024.sep.day18;
import java.io.*;
import java.util.*;

public class BOJ_5972_택배배송 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int N, M;
    static ArrayList<ArrayList<Edge>> adjList = new ArrayList<>();

    static class Edge {
        int node;
        int cost;

        public Edge(int node, int cost) {
            this.node = node;
            this.cost = cost;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "node=" + node +
                    ", cost=" + cost +
                    '}';
        }
    }

    public static void main(String[] args) throws Exception{
//        입력 -> 헛간의 수 N, 길의 수 M
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        for(int i = 0;i<=N;i++){
            adjList.add(new ArrayList<>());
        }
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int A = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());
            int C = Integer.parseInt(st.nextToken());
            adjList.get(A).add(new Edge(B, C));
            adjList.get(B).add(new Edge(A, C));
        }

        PriorityQueue<Edge> pq = new PriorityQueue<>((o1,o2)->{
            return o1.cost-o2.cost;
        });
        pq.add(new Edge(1, 0));
        int[] dijkstra = new int[N+1];
        for (int i = 0; i <= N; i++) {
            dijkstra[i] = Integer.MAX_VALUE;
        }
        while(!pq.isEmpty()){
            Edge cur = pq.poll();
            for(Edge next : adjList.get(cur.node)){
                int newCost = cur.cost+next.cost;
                if (newCost < dijkstra[next.node]) {
                    dijkstra[next.node] = newCost;
                    pq.add(new Edge(next.node, newCost));
                }
            }
        }
        System.out.println(dijkstra[N]);
    }
}
