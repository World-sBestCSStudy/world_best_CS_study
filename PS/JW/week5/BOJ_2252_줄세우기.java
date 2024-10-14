package ssafy_algorithm.BOJ;    // BOJ 2252. 줄 세우기
import java.io.*;
import java.util.*;

public class BOJ_2252_줄세우기 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();
    static int N, M;
    static List<List<Integer>> adjList = new ArrayList<>();
    static int[] topo;
    static Queue<Integer> queue = new ArrayDeque<>();

    // 위상 정렬
    // 진입 차수가 0인 것들을 Queue에 넣는다.
    // -> 하나씩 꺼내서 해당 노드에서 갈 수 있는 간선 제거(진입차수도 -)
    // -> 새롭게 진입차수가 0이 된 것들을 다시 큐에 삽입 -> 반복
    public static void main(String[] args) throws Exception{
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        topo = new int[N+1];
        for (int i = 0; i <= N; i++){
            adjList.add(new ArrayList<>());
        }

        for (int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            adjList.get(a).add(b);
            topo[b]++;
        }

        for (int i = 1; i <= N; i++){
            if (topo[i] == 0)
                queue.offer(i);
        }

        // 큐에서 꺼내서 갈 수 있는 곳 하나씩 제거 -> 진입 0인 노드 큐 삽입 -> 반복
        while (!queue.isEmpty()){
            int node = queue.poll();
            sb.append(node).append(" ");

            int size = adjList.get(node).size();
            for (int i = 0; i < size; i++){
                int num = adjList.get(node).get(i);
                topo[num]--;
                if (topo[num] == 0){
                    queue.offer(num);
                }
            }

        }

        // 만약 queue가 비었는데, 모든 노드 방문 처리가 안돼있다면 cycle이 있다는 것 -> 위상정렬 불가

        System.out.println(sb);
    }
}

