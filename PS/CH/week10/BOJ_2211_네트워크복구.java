//BOJ_2211_네트워크복구
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N,M;
    static List<List<Node>> graph;
    static List<Node> shortRoute;
    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st=new StringTokenizer(br.readLine());
        N= Integer.parseInt(st.nextToken());
        M= Integer.parseInt(st.nextToken());

        graph=new ArrayList<>();
        shortRoute = new ArrayList<>();
        for(int i =0; i<N+1; i++){
            graph.add(new ArrayList<>());
        }

        for(int i =0; i<M; i++){
            st=new StringTokenizer(br.readLine());

            int x= Integer.parseInt(st.nextToken());
            int y= Integer.parseInt(st.nextToken());
            int cost= Integer.parseInt(st.nextToken());

            graph.get(x).add(new Node(y,cost));
            graph.get(y).add(new Node(x,cost));
        }

        dijkstra();

        StringBuilder answer = new StringBuilder();
        answer.append(shortRoute.size()).append("\n");
        for(Node node : shortRoute){
            answer.append(node.x).append(" ").append(node.cost).append("\n");
        }
        System.out.println(answer);


    }
    public static void dijkstra(){
        PriorityQueue<Node> q= new PriorityQueue<>((o1,o2)->Integer.compare(o1.cost,o2.cost));
        boolean[] v=new boolean[N+1];
        int[] dist=new int[N+1];

        int[] route = new int[N+1];

        q.offer(new Node(1,0));
        Arrays.fill(dist,Integer.MAX_VALUE);
        dist[1]=0;

        while(!q.isEmpty()){
            Node node = q.poll();

            if(v[node.x]) continue;
            v[node.x] =true;

            for(Node thisNode: graph.get(node.x)){
                if(dist[thisNode.x] > dist[node.x]+thisNode.cost){
                    dist[thisNode.x] = dist[node.x]+thisNode.cost;
                    route[thisNode.x] = node.x;
                    q.offer(new Node(thisNode.x, dist[thisNode.x]));
                }
            }
        }

        boolean[][] routeV = new boolean[N+1][N+1];
        for(int i =2; i<N+1; i++){
            int x = i;


            while(true){
                if(!routeV[x][route[x]]){
                    routeV[x][route[x]] = true;
                    shortRoute.add(new Node(x,route[x]));
                }
                x=route[x];
                if(x==1) break;
            }

        }
    }
    static class Node{
        int x,cost;

        public Node(int x, int cost) {
            this.x = x;
            this.cost = cost;
        }
    }
}
