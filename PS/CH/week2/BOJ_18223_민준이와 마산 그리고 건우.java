// BOJ_18223_민준이와 마산 그리고 건우

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static List<List<Node>> graph;
    static int INF = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int v = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());
        int p = Integer.parseInt(st.nextToken());

        graph = new ArrayList<>();
        for (int i = 0; i < v + 1; i++) graph.add(new ArrayList<>());

        for (int i = 0; i < e; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            graph.get(x).add(new Node(y, cost));
            graph.get(y).add(new Node(x, cost));
        }


        String answer = "SAVE HIM";
        if (dijkstra(v, 1, p) + dijkstra(v, p, v) > dijkstra(v, 1, v)) answer = "GOOD BYE";
        System.out.println(answer);


    }

    public static int dijkstra(int v, int start, int end) {
        PriorityQueue<Node> q = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.cost, o2.cost));
        int[] dist = new int[v + 1];

        q.offer(new Node(start, 0));
        Arrays.fill(dist, INF);
        dist[start] = 0;

        while (!q.isEmpty()) {
            Node node = q.poll();

            if(dist[node.x] < node.cost) continue;
            
            for (Node thisNode : graph.get(node.x)) {
                if (dist[thisNode.x] > dist[node.x] + thisNode.cost) {
                    dist[thisNode.x] = dist[node.x] + thisNode.cost;
                    q.offer(new Node(thisNode.x, dist[thisNode.x]));
                }
            }
        }

        return dist[end];
    }

    static class Node {
        int x, cost;

        public Node(int x, int cost) {
            this.x = x;
            this.cost = cost;
        }
    }

}

//
