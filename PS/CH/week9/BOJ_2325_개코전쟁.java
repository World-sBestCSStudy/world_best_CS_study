//BOJ_2325_개코전쟁
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N, M;
    static List<List<Node>> graph;
    static Stack<int[]> shortRoute;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        graph = new ArrayList<>();
        for (int i = 0; i < N + 1; i++) graph.add(new ArrayList<>());

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            graph.get(x).add(new Node(y, cost));
            graph.get(y).add(new Node(x, cost));
        }


        shortRoute = new Stack<>();
        int dist = findShortRoute();

        while (!shortRoute.isEmpty()) {
            int[] point = shortRoute.pop();

            dist = Math.max(dist, dijkstraRemovePoint(point[0], point[1]));
        }
        System.out.println(dist);

    }

    public static int dijkstraRemovePoint(int removeX, int removeY) {
        PriorityQueue<Node> q = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.cost, o2.cost));
        int[] dist = new int[N + 1];
        boolean[] v = new boolean[N + 1];

        q.offer(new Node(1, 0));
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[1] = 0;

        while (!q.isEmpty()) {
            Node node = q.poll();

            if (v[node.x]) continue;

            v[node.x] = true;

            for (Node thisNode : graph.get(node.x)) {
                if ((thisNode.x == removeX && node.x == removeY) || (thisNode.x == removeY && node.x == removeX))
                    continue;

                if (dist[thisNode.x] > dist[node.x] + thisNode.cost) {
                    dist[thisNode.x] = dist[node.x] + thisNode.cost;
                    q.offer(new Node(thisNode.x, dist[thisNode.x]));
                }
            }
        }

        return dist[N];

    }

    public static int findShortRoute() {
        PriorityQueue<Node> q = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.cost, o2.cost));
        int[] dist = new int[N + 1];
        boolean[] v = new boolean[N + 1];
        int[] route = new int[N + 1];

        q.offer(new Node(1, 0));
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[1] = 0;

        while (!q.isEmpty()) {
            Node node = q.poll();

            if (v[node.x]) continue;

            v[node.x] = true;

            for (Node thisNode : graph.get(node.x)) {
                if (dist[thisNode.x] > dist[node.x] + thisNode.cost) {
                    dist[thisNode.x] = dist[node.x] + thisNode.cost;
                    route[thisNode.x] = node.x;
                    q.offer(new Node(thisNode.x, dist[thisNode.x]));
                }
            }
        }

        int x = N;
        while (x != 0) {
            shortRoute.push(new int[]{x, route[x]});
            x = route[x];
        }
        return dist[N];
    }


    static class Node {
        int x, cost;

        public Node(int x, int cost) {
            this.x = x;
            this.cost = cost;
        }
    }
}
