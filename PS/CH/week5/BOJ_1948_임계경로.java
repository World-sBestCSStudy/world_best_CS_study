//BOJ_1948_임계경로

import java.io.*;
import java.util.*;

public class Main {
    static int N, M;
    static List<List<Node>> graph;
    static List<List<Node>> reverseGraph;
    static int[] fanOut;
    static int[] fanIn;
    static StringBuilder answer;

    static int maxDist =Integer.MIN_VALUE;
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());
        graph = new ArrayList<>();
        reverseGraph = new ArrayList<>();

        answer = new StringBuilder();
        fanOut = new int[N + 1];
        fanIn = new int[N + 1];

        for (int i = 0; i < N + 1; i++) {
            graph.add(new ArrayList<>());
            reverseGraph.add(new ArrayList<>());
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            graph.get(x).add(new Node(y, cost));
            reverseGraph.get(y).add(new Node(x,cost));
            fanOut[x]++;
            fanIn[y]++;
        }

        Queue<Integer> q = new LinkedList<>();

        //각 경로의 최대값을 저장하기 위함
        int[] dist = new int[N+1];
        for(int i =1; i<N+1; i++){
            if(fanIn[i]==0) q.offer(i);
        }

        while(!q.isEmpty()){
            int nx = q.poll();
            for(Node node : graph.get(nx)){
                fanIn[node.x]--;
                dist[node.x]=Math.max(dist[node.x], dist[nx]+node.cost);
                if(fanIn[node.x]==0){
                    q.offer(node.x);
                }
            }
        }

        for(int i =1; i<N+1; i++){
            if(fanOut[i]==0) q.offer(i);
        }

        int count =0;
        boolean[][] v= new boolean[N+1][N+1];

        while(!q.isEmpty()){
            int nx = q.poll();

            for(Node node : reverseGraph.get(nx)){
                if(!v[nx][node.x] && dist[nx] == dist[node.x] + node.cost){
                    v[nx][node.x] = true;
                    count++;
                    q.offer(node.x);
                }
            }
        }

        String answer = dist[N]+"\n"+count;
        System.out.println(answer);
    }

    static class Node {
        int x, cost;

        public Node(int x, int cost) {
            this.x = x;
            this.cost = cost;
        }
    }
}


