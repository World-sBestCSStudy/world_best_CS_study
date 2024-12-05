//BOJ_1884_고속도로
//PS. 기준이 두개인 다익스트라
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int K, N, M;
    static List<List<Node>> graph;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        K = Integer.parseInt(br.readLine());
        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());

        graph = new ArrayList<>();
        for (int i = 0; i < N + 1; i++) graph.add(new ArrayList<>());

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int l = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());
            graph.get(x).add(new Node(y, l, t));
        }

        int answer = dijkstra();
        System.out.println(answer);

    }

    public static int dijkstra() {
        PriorityQueue<Node> q = new PriorityQueue<>((o1, o2) -> o1.l - o2.l);

        //i지점에서 t비용으로갈때의 최소l
        int[][] dist = new int[N + 1][K + 1];
        boolean[][] v = new boolean[N + 1][K + 1];

        q.offer(new Node(1, 0, 0));
        for (int i = 0; i < N + 1; i++) Arrays.fill(dist[i], Integer.MAX_VALUE);
        dist[1][0] = 0;

        while (!q.isEmpty()) {
            Node node = q.poll();

//            if(dist[node.x][node.t] > node.l) continue;
            if(v[node.x][node.t]) continue;
            v[node.x][node.t] = true;

            for (Node thisNode : graph.get(node.x)) {
                int cost = node.t + thisNode.t;
                if (cost > K) continue;
                if (dist[thisNode.x][cost] > dist[node.x][node.t] + thisNode.l) {
                    dist[thisNode.x][cost] = dist[node.x][node.t] + thisNode.l;
                    q.offer(new Node(thisNode.x, dist[thisNode.x][cost], cost));
                }
            }
        }

        int result = Integer.MAX_VALUE;
        for (int i = 1; i < K + 1; i++) {
            result = Math.min(result, dist[N][i]);
        }
        return result == Integer.MAX_VALUE ? -1 : result;
    }

    static class Node {
        int x, l, t;

        public Node(int x, int l, int t) {
            this.x = x;
            this.l = l;
            this.t = t;
        }

    }
}

