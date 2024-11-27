// BOJ 2056. 작업

import java.io.*;
import java.util.*;

public class boj_2056 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();
    static int N, max, ans = 0;
    static int[] job, indegree, dp;
    static List<List<Integer>> adjList = new ArrayList<>();
    static Queue<Integer> q = new ArrayDeque<>();

    // 선행관계 -> 위상정렬
    // 선행관계가 없는 작업도 있음(ex. 1번 작업) => 얘네는 동시에 수행가능
    // 모든 작업을 수행하는데 필요한 최소시간
    public static void main(String[] args) throws Exception{
        N = Integer.parseInt(br.readLine().trim());

        job = new int[N+1];
        indegree = new int[N+1];
        dp = new int[N+1];    // ** dp를 활용해야함. 자연스럽게 visit 배열이 필요없어진다!
        for (int i = 0; i <= N; i++){
            adjList.add(new ArrayList<>());
        }

        for (int i = 1; i <= N; i++){
            st = new StringTokenizer(br.readLine().trim());
            job[i] = Integer.parseInt(st.nextToken());
            int size = Integer.parseInt(st.nextToken());
            for (int j = 0; j < size; j++){
                int next = Integer.parseInt(st.nextToken());
                adjList.get(i).add(next);       // ** 입력을 반대로 받았었음...
                indegree[next]++;
            }
        }

        topologySort();
        // 단순히 각 단계에서 최댓값만을 선택해서 더해주는 방식은 안된다. 가중치(수행시간) 때문에!
        // ** 가중치가 있는 값은 bfs 처럼 queue의 size를 구해서 탐색하면 안된다.
        // ** 선행 관계에 대해 수행하되, 각각의 작업을 독립적으로 수행해야 한다.
            
        System.out.println(ans);
    }

    static void topologySort(){
        for (int i = 1; i <= N; i++){
            if (indegree[i] == 0) {
                q.offer(i);
                dp[i] = job[i];
            }
        }

        while (!q.isEmpty()){
            int cur = q.poll();
//            System.out.print(cur +" ");

            for (int next : adjList.get(cur)){
                dp[next] = Math.max(dp[next], dp[cur] + job[next]);
                indegree[next]--;

                if (indegree[next] == 0){
                    q.offer(next);
                }

            }
//            System.out.println("=> " +max);
        }

        for (int i = 1; i <= N; i++){
            ans = Math.max(dp[i], ans);
        }
    }
}