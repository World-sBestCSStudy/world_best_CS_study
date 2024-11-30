//BOJ_9370_미확인 도착지
//최단 경로가 유일하지 않기 때문에 경로역추적은 정답이 될 수 없다.
//s -> g -> h -> t도 10이고 s->h->t도 10이면, s->h->t 경로가 역추적될수 있다.

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N;
    static List<List<Node>> grpah;
    static int[] route;
    static int INF = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder answer = new StringBuilder();


        int T = Integer.parseInt(br.readLine());
        for (int t = 0; t < T; t++) {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());
            int S = Integer.parseInt(st.nextToken());
            int G = Integer.parseInt(st.nextToken());
            int H = Integer.parseInt(st.nextToken());

            grpah = new ArrayList<>();
            for (int i = 0; i < N + 1; i++) grpah.add(new ArrayList<>());
            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                int cost = Integer.parseInt(st.nextToken());

                grpah.get(x).add(new Node(y, cost));
                grpah.get(y).add(new Node(x, cost));
            }

            int[] ex = new int[K];
            for (int i = 0; i < K; i++) {
                ex[i] = Integer.parseInt(br.readLine());
            }

            int[] distS = dijkstra(S);
            int[] distG = dijkstra(G);
            int[] distH = dijkstra(H);
            List<Integer> possibleV = new ArrayList<>();
            for (int i = 0; i < K; i++) {
                int x = ex[i];

                int min = distS[x];
                long dist1 = distS[G] + distG[H] + distH[x];
                long dist2 = distS[H] + distH[G] + distG[x];

                if ((dist1 == min) || (dist2 == min)) possibleV.add(x);

            }

            Collections.sort(possibleV);
            for (int i : possibleV) answer.append(i).append(" ");
            answer.append("\n");
        }
        System.out.println(answer);

    }

    public static int[] dijkstra(int s) {
        PriorityQueue<Node> q = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.cost, o2.cost));
        int[] dist = new int[N + 1];
        route = new int[N + 1];
        boolean[] v = new boolean[N + 1];

        q.offer(new Node(s, 0));
        Arrays.fill(dist, INF);
        dist[s] = 0;

        while (!q.isEmpty()) {
            Node node = q.poll();
            if (v[node.x]) continue;
            v[node.x] = true;
            for (Node thisNode : grpah.get(node.x)) {
                if (dist[thisNode.x] > dist[node.x] + thisNode.cost) {
                    dist[thisNode.x] = dist[node.x] + thisNode.cost;
                    route[thisNode.x] = node.x;
                    q.offer(new Node(thisNode.x, dist[thisNode.x]));
                }
            }
        }

        return dist;
    }

    static class Node {
        int x, cost;

        public Node(int x, int cost) {
            this.x = x;
            this.cost = cost;
        }
    }

}
