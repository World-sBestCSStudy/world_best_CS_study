//BOJ_11437_LCA
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int N;
    static List<List<Integer>> tree;
    static int[] parent;
    static int[] degree;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        tree=new ArrayList<>();

        for(int i =0; i<N+1; i++) tree.add(new ArrayList<>());

        for(int i=0; i<N-1; i++){
            st=new StringTokenizer(br.readLine());
            int x= Integer.parseInt(st.nextToken());
            int y= Integer.parseInt(st.nextToken());

            tree.get(x).add(y);
            tree.get(y).add(x);
        }

        parent=new int[N+1];
        degree=new int[N+1];
        setParentAndDegree(1);

        StringBuilder answer = new StringBuilder();
        int M = Integer.parseInt(br.readLine());
        for(int i =0; i<M; i++){
            st=new StringTokenizer(br.readLine());
            int v1= Integer.parseInt(st.nextToken());
            int v2= Integer.parseInt(st.nextToken());
            answer.append(findParent(v1,v2)).append("\n");
        }
        System.out.println(answer);
    }

    public static int findParent(int v1 ,int v2){
        int depthV1 = degree[v1];
        int depthV2 = degree[v2];

        while(depthV1>depthV2){
            v1 = parent[v1];
            depthV1 = degree[v1];
        }

        while(depthV1<depthV2){
            v2 = parent[v2];
            depthV2 = degree[v2];
        }

        while(v1!=v2){
            v1=parent[v1];
            v2=parent[v2];
        }

        return v1;

    }
    public static void setParentAndDegree(int root){
        Queue<Node> q= new LinkedList<>();
        q.offer(new Node(1,1));

        degree[root] = 1;

        while(!q.isEmpty()){
            Node node = q.poll();

            for(int nx : tree.get(node.p)){
                if(degree[nx] == 0){
                    parent[nx] = node.p;
                    degree[nx] = node.d+1;
                    q.offer(new Node(nx,node.d+1));
                }
            }
        }

    }
    static class Node{
        int p,d;
        public Node(int p, int d) {
            this.p = p;
            this.d = d;
        }
    }
}

