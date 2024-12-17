//BOJ_2610_회의준비

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] parent;
    static List<List<Integer>> graph;
    static int N, M;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());

        parent = new int[N + 1];
        graph = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            parent[i] = i;
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            union(x, y);
            graph.get(x).add(y);
            graph.get(y).add(x);
        }


        int[] depth = new int[N + 1];
        int[] root = new int[N + 1];
        Arrays.fill(depth, Integer.MAX_VALUE);
        for (int i = 1; i < N + 1; i++) {

            int count = 0;
            int x= findParent(i);

            for (int j = 0; j < N + 1; j++) {
                if (x==findParent(j)) {
                    count = Math.max(count,countDepth(i,j));
                }
            }

            if (depth[x] > count) {
                depth[x] = Math.min(count, depth[x]);
                root[x] = i;
            }

        }

        int grpup = 0;
        StringBuilder answer = new StringBuilder();
        List<Integer> temp =new ArrayList<>();
        for (int i = 1; i < N + 1; i++) {
            if (root[i] > 0){
                grpup++;
                temp.add(root[i]);
            }
        }

        answer.append(grpup).append("\n");
        Collections.sort(temp);
        for(int i : temp) answer.append(i).append("\n");

        System.out.println(answer);
    }

    public static void union(int x, int y) {
        x = findParent(x);
        y = findParent(y);

        if (x > y) parent[x] = y;
        else parent[y] = x;
    }

    public static int findParent(int x) {
        if (x == parent[x]) return x;
        return parent[x] = findParent(parent[x]);
    }

    public static int countDepth(int x, int y) {
        Queue<Node> q = new LinkedList<>();
        boolean[] v = new boolean[N + 1];

        q.offer(new Node(x, 0));
        v[x] = true;

        while (!q.isEmpty()) {
            Node node = q.poll();

            if (node.x == y) return node.count;

            for (int nx : graph.get(node.x)) {
                if (!v[nx]) {
                    v[nx] = true;
                    q.offer(new Node(nx, node.count + 1));
                }
            }
        }
        return 0;

    }

    static class Node {
        int x, count;

        public Node(int x, int count) {
            this.x = x;
            this.count = count;
        }
    }
}

