//BOJ_20183_골목 대장 호석 - 효율성 2
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static List<List<Node>> graph;
    static Long INF = Long.MAX_VALUE;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;


        st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int A = Integer.parseInt(st.nextToken());
        int B = Integer.parseInt(st.nextToken());
        long C = Long.parseLong(st.nextToken());

        graph = new ArrayList<>();
        for (int i = 0; i < N + 1; i++) graph.add(new ArrayList<>());

        long left = INF, right = 0;
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            graph.get(x).add(new Node(y, cost));
            graph.get(y).add(new Node(x, cost));

            left = Math.min(left, cost);
            right = Math.max(right, cost);
        }

        boolean flag = false;
        while (left <= right) {
            long mid = (left + right) / 2;

            boolean isCan = dijkstra(A, B, C, N, mid);

            if (isCan) {
                flag=true;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        System.out.println(flag?left:-1);

    }

    public static boolean dijkstra(int sx, int ex, long c, int N, long limit) {
        PriorityQueue<Node> q = new PriorityQueue<>((o1, o2) -> Long.compare(o1.cost, o2.cost));
        long[] dist = new long[N + 1];
        boolean[] v = new boolean[N + 1];

        q.offer(new Node(sx, 0));
        Arrays.fill(dist, INF);
        dist[sx] = 0;

        while (!q.isEmpty()) {
            Node node = q.poll();

            if (v[node.x]) continue;
            v[node.x] = true;

            for (Node thisNode : graph.get(node.x)) {
                if (thisNode.cost > limit) continue;
                if (dist[thisNode.x] > dist[node.x] + thisNode.cost) {
                    dist[thisNode.x] = dist[node.x] + thisNode.cost;
                    q.offer(new Node(thisNode.x, dist[thisNode.x]));
                }
            }
        }


        return dist[ex] <= c;
    }


    static class Node {
        int x;
        long cost;

        public Node(int x, long cost) {
            this.x = x;
            this.cost = cost;
        }
    }

}
