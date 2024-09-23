import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

//헤헤
public class Main {
    static int N, M, K;
    static ArrayList<ArrayList<int[]>> adjList;
    static int[][] map;
    static int[] friends;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        StringTokenizer st;
        for (int t = 0; t < T; t++) {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            map = new int[N + 1][N + 1];
            for (int i = 0; i < N + 1; i++) {
                Arrays.fill(map[i], Integer.MAX_VALUE / 2);
            }
            adjList = new ArrayList<>();
            for (int i = 0; i < N + 1; i++) {
                adjList.add(new ArrayList<>());
            }
            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                adjList.get(a).add(new int[]{b, c});
                adjList.get(b).add(new int[]{a, c});
            }
            K = Integer.parseInt(br.readLine());
            friends = new int[K];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < K; i++) {
                friends[i] = Integer.parseInt(st.nextToken());
            }

            for (int i = 0; i < K; i++) {
                int friend = friends[i];
                map[friend][friend] = 0;
                dijkstra(friend);
            }

            int min = Integer.MAX_VALUE;
            int roomNum = N + 1;
            for (int i = 1; i <= N; i++) {
                int sum = 0;
                for (int j = 0; j < K; j++) {
                    sum += map[friends[j]][i];
                }
                if (sum == min) {
                    roomNum = Math.min(roomNum, i);
                } else if (sum < min) {
                    roomNum = i;
                    min = sum;
                }
            }
            sb.append(roomNum).append("\n");
        }
        System.out.println(sb);
    }

    static void dijkstra(int start) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);
        ArrayList<int[]> startList = adjList.get(start);

        for (int[] node : startList) {
            pq.add(new int[]{node[0], node[1]});
        }

        while (!pq.isEmpty()) {
            int[] node = pq.poll();
            if (map[start][node[0]] > node[1]) {
                map[start][node[0]] = node[1];
                ArrayList<int[]> list = adjList.get(node[0]);
                for (int[] n : list) {
                    if (map[start][n[0]] > node[0] + n[1]) pq.add(new int[]{n[0], node[1] + n[1]});
                }
            }
        }
    }
}
