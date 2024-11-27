// SWEA 1251. 하나로
import java.io.*;
import java.util.*;

public class SWEA_1251_하나로 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();
    static int T, N;
    static double E, answer;

    static PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingDouble(e -> e.cost));

    static int[] p, X, Y;
    static long[] minDist;

    // 모든 섬을 잇는 최소 환경부담금 (반올림하여 정수형태)
    // MST -> 간선 많을듯 -> prim 알고리즘
    public static void main(String[] args) throws Exception{
        T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++){
            N = Integer.parseInt(br.readLine());

            // 초기화
            minDist = new long[N];
            pq.clear();

            X = new int[N];
            Y = new int[N];

            // 섬 좌표 입력
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++){
                X[i] = Integer.parseInt(st.nextToken());
            }
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++){
                Y[i] = Integer.parseInt(st.nextToken());
            }

            E = Double.parseDouble(br.readLine());

            // 모든 간선 거리 계산
            for (int i = 0; i < N; i++){
                for (int j = i+1; j < N; j++){
                    int x1 = X[i];
                    int y1 = Y[i];
                    int x2 = X[j];
                    int y2 = Y[j];

                    double L = getLength(x1, y1, x2, y2);
                    pq.add(new Edge(i, j, L));
                }
            }

            answer = 0;
            kruskal();

            sb.append("#").append(tc).append(" ").append(Math.round(answer)).append("\n");
        }

        System.out.println(sb);
    }

    static void kruskal(){
        makeSet(N);

        int cnt = 0;
        while (cnt < N-1){
            Edge edge = pq.poll();

            if (findSet(edge.v1) != findSet(edge.v2)){
                union(edge.v1, edge.v2);
                cnt++;
                answer += edge.cost;
            }

        }
    }

    static void makeSet(int N){
        p = new int[N];
        for (int i = 0; i < N; i++){
            p[i] = i;
        }
    }

    static int findSet(int x){
        if (p[x] == x) return x;
        return p[x] = findSet(p[x]);
    }

    static boolean union(int a, int b){
        int pa = findSet(a);
        int pb = findSet(b);

        if (pa == pb) return false;

        p[pb] = pa;
        return true;
    }


    static double getLength(long x1, long y1, long x2, long y2){
        return Math.pow(Math.abs(x1-x2),2) + Math.pow(Math.abs(y1-y2),2);
    }

    static class Edge {
        int v1, v2;
        double cost;

        public Edge(int from, int to, double length){
            this.v1 = from;
            this.v2 = to;
            this.cost = length * E;
        }
    }
}
