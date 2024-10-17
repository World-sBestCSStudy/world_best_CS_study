//BOJ_1800_인터넷설치

import java.io.*;
import java.util.*;

public class Main {
    static List<List<Node>> graph;
    static int n, p, k;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        p = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        graph = new ArrayList<>();
        for(int i =0; i<n+1; i++) graph.add(new ArrayList<>());

        int left=0, right = Integer.MIN_VALUE;
        for(int i =0; i<p; i++){
            st=new StringTokenizer(br.readLine());
            int x= Integer.parseInt(st.nextToken());
            int y= Integer.parseInt(st.nextToken());
            int cost= Integer.parseInt(st.nextToken());

            graph.get(x).add(new Node(y,cost));
            graph.get(y).add(new Node(x,cost));
            right=Math.max(right,cost);
        }


        right +=1;
        boolean flag= false;
        while(left<right){
            int mid = (left+right)/2;

            if(dijkstra(mid,k)){
                flag = true;
                right=mid;
            }else{
                left = mid+1;
            }
        }
        System.out.println(!flag?-1:right);


    }

    public static boolean dijkstra(int mid,int k){

        //각각의 단위가 최소값이 되어야한다.
        PriorityQueue<Node> q= new PriorityQueue<>((o1,o2)-> Integer.compare(o1.cost,o2.cost));
        int[] dist = new int[n+1];

        q.offer(new Node(1,0));
        Arrays.fill(dist,Integer.MAX_VALUE);
        dist[1] = 0;

        while(!q.isEmpty()){
            Node node = q.poll();

            for(Node thisNode : graph.get(node.x)){
                int nDist = dist[node.x];
                if(thisNode.cost > mid){
                    nDist++;
                }
                if(dist[thisNode.x] > nDist){
                    dist[thisNode.x] = nDist;
                    q.offer(new Node(thisNode.x, nDist));
                }
            }
        }

        return dist[n]<=k;
    }

    static class Node {
        int x, cost;

        public Node(int x, int cost) {
            this.x = x;
            this.cost = cost;
        }
    }
}


