//BOJ_2637_장난감 조립

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static List<List<Node>> graph;
    static int N,M;
    static int[] e;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());
        graph=new ArrayList<>();
        for(int i =0; i<N+1; i++){
            graph.add(new ArrayList<>());
        }
        e=new int[N+1];

        for(int i =0; i<M; i++){
            st=new StringTokenizer(br.readLine());
            int x= Integer.parseInt(st.nextToken());
            int y= Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());
            graph.get(x).add(new Node(y,k));
        }

        for(int i =0; i<graph.size(); i++){
            for(int j =0; j<graph.get(i).size(); j++){
                Node node = graph.get(i).get(j);
                e[node.x]++;
            }
        }


        int[] dist=new int[N+1];
        Queue<Integer> q= new LinkedList<>();
        for(int i =1; i<N+1; i++){
            if(e[i] == 0) q.offer(i);
        }

        dist[N]=1;
        while(!q.isEmpty()){
            int x =  q.poll();

            for(Node node : graph.get(x)){
                dist[node.x] += dist[x]*node.parts;
                e[node.x] -=1;
                if(e[node.x]==0){
                    q.offer(node.x);
                }
            }
        }

        StringBuilder answer =new StringBuilder();
        for(int i =1; i<N+1; i++){
            if(graph.get(i).size()==0) answer.append(i).append(" ").append(dist[i]).append("\n");
        }

        System.out.println(answer);




    }
    static class Node{
        int x, parts;

        public Node(int x, int parts) {
            this.x = x;
            this.parts = parts;
        }
    }
}
