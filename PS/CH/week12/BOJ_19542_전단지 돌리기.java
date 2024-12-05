//BOJ_19542_전단지 돌리기
import java.io.*;
import java.util.*;

public class Main {
    static int N,S,D;
    static List<List<Integer>> tree;
    static int[] dist;
    static int answer;
    static boolean[] v;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st=new StringTokenizer(br.readLine());
        N=Integer.parseInt(st.nextToken());
        S=Integer.parseInt(st.nextToken());
        D=Integer.parseInt(st.nextToken());
        answer=0;

        tree=new ArrayList<>();
        dist=new int[N+1];
        v=new boolean[N+1];
        for(int i =0; i<N+1; i++) tree.add(new ArrayList<>());

        for(int i =0; i<N-1; i++){
            st=new StringTokenizer(br.readLine());
            int x= Integer.parseInt(st.nextToken());
            int y= Integer.parseInt(st.nextToken());

            tree.get(x).add(y);
            tree.get(y).add(x);
        }

        v[S] = true;
        dfs(S);
        System.out.println(answer*2);

    }

    public static int dfs(int x){
        for(int nx : tree.get(x)){
            if(!v[nx]){
                v[nx] = true;
                dist[x] = Math.max(dist[x], dfs(nx)+1);
            }

        }
        if(dist[x] >=D && x!=S) answer++;

        return dist[x];

    }

}

