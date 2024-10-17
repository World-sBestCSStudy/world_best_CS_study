//BOJ_13911_집 구하기

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static List<List<Node>> graph;
    static int N;
    static PriorityQueue<Node> q;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        int E = Integer.parseInt(st.nextToken());

        graph = new ArrayList<>();
        for (int i = 0; i < N + 1; i++) graph.add(new ArrayList<>());

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());

            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            graph.get(x).add(new Node(y, cost));
            graph.get(y).add(new Node(x, cost));
        }

        boolean[] md = new boolean[N + 1];
        boolean[] sb = new boolean[N + 1];

        st = new StringTokenizer(br.readLine());
        int m = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) {
            md[Integer.parseInt(st.nextToken())] = true;
        }

        st = new StringTokenizer(br.readLine());
        int s = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < s; i++) {
            sb[Integer.parseInt(st.nextToken())] = true;
        }


        q = new PriorityQueue<>((o1, o2) -> Long.compare(o1.cost, o2.cost));
        long[] dist1 = new long[N + 1];
        Arrays.fill(dist1, Long.MAX_VALUE);
        for (int i = 1; i < N + 1; i++) {
            if (md[i]) {
                q.offer(new Node(i, 0));
                dist1[i] = 0;
            }
        }

        dist1 = dijkstra(dist1);

        long[] dist2 = new long[N + 1];
        Arrays.fill(dist2, Long.MAX_VALUE);
        for (int i = 1; i < N + 1; i++) {
            if (sb[i]) {
                q.offer(new Node(i, 0));
                dist2[i] = 0;
            }
        }

        dist2 = dijkstra(dist2);

        long answer = Long.MAX_VALUE;
        for (int i = 1; i < N + 1; i++) {
            if (md[i] || sb[i] || dist1[i] > x || dist2[i] > y || dist1[i] == Long.MAX_VALUE || dist2[i] == Long.MAX_VALUE)
                continue;
            answer = Math.min(dist1[i] + dist2[i], answer);
        }

        System.out.println(answer == Long.MAX_VALUE ? -1 : answer);

    }

    static public long[] dijkstra(long[] dist) {
        while (!q.isEmpty()) {
            Node node = q.poll();

            if (dist[node.x] < node.cost) continue;

            for (Node thisNode : graph.get(node.x)) {
                if (dist[thisNode.x] > dist[node.x] + thisNode.cost) {
                    dist[thisNode.x] = dist[node.x] + thisNode.cost;
                    q.offer(new Node(thisNode.x, dist[thisNode.x]));
                }
            }
        }

        return dist;

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