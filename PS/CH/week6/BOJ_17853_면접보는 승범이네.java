//BOJ_17853_면접보는 승범이네

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static List<List<Node>> graph;
    static int[] start;
    static int N;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        graph = new ArrayList<>();

        for (int i = 0; i < N + 1; i++) graph.add(new ArrayList<>());
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            graph.get(y).add(new Node(x, cost));
        }

        start = new int[K];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < K; i++) {
            start[i] = Integer.parseInt(st.nextToken());
        }

        long[] dist = dijkstra();

        int idx = 0;
        long answer = 0;

        for (int i = 1; i < N + 1; i++) {
            if (answer < dist[i]) {
                answer = dist[i];
                idx = i;
            }
        }

        System.out.println(idx + "\n" + answer);

    }

    public static long[] dijkstra() {
        PriorityQueue<Node> q = new PriorityQueue<>((o1, o2) -> Long.compare(o1.cost, o2.cost));
        long[] dist = new long[N + 1];

        Arrays.fill(dist, Long.MAX_VALUE);

        for (int i = 0; i < start.length; i++) {
            q.offer(new Node(start[i], 0));
            dist[start[i]] = 0;
        }

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