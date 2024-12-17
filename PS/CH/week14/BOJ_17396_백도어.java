//BOJ_17396_백도어

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N, M;
    static boolean[] isVisible;
    static List<List<Node>> graph;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        isVisible = new boolean[N];
        graph = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            graph.add(new ArrayList<>());
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            int v = Integer.parseInt(st.nextToken());
            if (v == 1) {
                isVisible[i] = true;
            }
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            graph.get(x).add(new Node(y, cost));
            graph.get(y).add(new Node(x, cost));
        }

        long answer = dijkstra(0);
        System.out.println(answer);

    }

    public static long dijkstra(int start) {
        PriorityQueue<Node> q = new PriorityQueue<>((o1, o2) -> Long.compare(o1.cost, o2.cost));

        long[] dist = new long[N];
        Arrays.fill(dist, Long.MAX_VALUE);

        q.offer(new Node(start, 0));
        dist[start] = 0;

        while (!q.isEmpty()) {
            Node node = q.poll();

            if(dist[node.x] < node.cost) continue;

            for (Node thisNode : graph.get(node.x)) {
                if (isVisible[thisNode.x] && thisNode.x != N - 1) continue;

                if (dist[thisNode.x] > dist[node.x] + thisNode.cost) {
                    dist[thisNode.x] = dist[node.x] + thisNode.cost;
                    q.offer(new Node(thisNode.x, dist[thisNode.x]));
                }
            }
        }


        return dist[N - 1] == Long.MAX_VALUE ? -1 : dist[N - 1];

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

