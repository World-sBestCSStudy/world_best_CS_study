//BOJ_5719_거의 최단 경로
import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static List<List<Node>> graph;
    static List<List<Node>> reverseGraph;
    static int N;
    static boolean[][] road;
    static int[] dist;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        StringBuilder answer = new StringBuilder();

        graph = new ArrayList<>();
        reverseGraph = new ArrayList<>();

        while (true) {
            st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            if (N == 0) break;

            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken()) + 1;
            int d = Integer.parseInt(st.nextToken()) + 1;

            road = new boolean[N + 1][N + 1];
            graph.clear();
            reverseGraph.clear();
            dist = new int[N + 1];

            for (int i = 0; i < N + 1; i++) {
                graph.add(new ArrayList<>());
                reverseGraph.add(new ArrayList<>());
            }

            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken()) + 1;
                int y = Integer.parseInt(st.nextToken()) + 1;
                int cost = Integer.parseInt(st.nextToken());
                graph.get(x).add(new Node(y, cost));
                reverseGraph.get(y).add(new Node(x, cost));
            }
            dijkstra(s, d);
            removeShortRoad(s, d);
            int almostShortDist = dijkstra(s, d);
            answer.append(almostShortDist == Integer.MAX_VALUE ? -1 : almostShortDist).append("\n");

        }
        System.out.println(answer);

    }

    public static int dijkstra(int s, int d) {
        PriorityQueue<Node> q = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.cost, o2.cost));
        boolean[] v = new boolean[N + 1];

        Arrays.fill(dist, Integer.MAX_VALUE);

        dist[s] = 0;
        q.offer(new Node(s, 0));

        while (!q.isEmpty()) {
            Node node = q.poll();

            if (v[node.x]) continue;
            v[node.x] = true;

            for (Node thisNode : graph.get(node.x)) {
                if (dist[thisNode.x] > dist[node.x] + thisNode.cost) {
                    if (road[node.x][thisNode.x]) continue;
                    dist[thisNode.x] = dist[node.x] + thisNode.cost;
                    q.offer(new Node(thisNode.x, dist[thisNode.x]));
                }
            }
        }
        return dist[d];

    }

    public static void removeShortRoad(int s, int d) {
        Queue<Integer> q = new LinkedList<>();
        boolean[] v = new boolean[N + 1];
        q.offer(d);
        v[d] = true;

        while (!q.isEmpty()) {
            int x = q.poll();

            for (Node node : reverseGraph.get(x)) {
                if (dist[x] == dist[node.x] + node.cost) {
                    road[node.x][x] = true;
                    if (!v[node.x]) {
                        v[node.x] = true;
                        q.offer(node.x);
                    }
                }
            }
        }
    }

    static class Node {
        int x, cost;

        public Node(int x, int cost) {
            this.x = x;
            this.cost = cost;
        }
    }

}


