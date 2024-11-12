//BOJ_16437_양 구출 작전
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        long[] dist =new long[N+1];

        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < N + 1; i++) graph.add(new ArrayList<>());

        for (int i = 1; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            char type = st.nextToken().charAt(0);
            int cost = Integer.parseInt(st.nextToken());
            int next = Integer.parseInt(st.nextToken());

            if (type == 'W') cost *= -1;

            graph.get(i+1).add(next);
            dist[i+1] = cost;
        }

        int[] e= new int[N+1];
        for(int i =1; i<N+1; i++){
            for(int j =0; j<graph.get(i).size(); j++){
                e[graph.get(i).get(j)]++;
            }
        }


        Queue<Integer> q  = new LinkedList<>();
        for(int i= 1; i<N+1; i++){
            if(e[i] == 0){
                q.offer(i);
            }
        }

        while(!q.isEmpty()){
            int x= q.poll();

            for(int nx : graph.get(x)){
                if(dist[x]>=0) dist[nx] += dist[x];
                e[nx]--;
                if(e[nx]==0){
                    dist[nx] = Math.max(dist[nx],0);
                    q.offer(nx);
                }
            }
        }

        System.out.println(dist[1]);
    }

}
