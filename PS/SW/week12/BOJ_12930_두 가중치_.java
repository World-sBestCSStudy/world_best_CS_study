import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static int[][][] weight;
    static boolean[][] check;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(br.readLine());

        weight = new int[N][N][2];
        check = new boolean[N][N];

        for (int i = 0; i < N; i++) {
            String s = br.readLine();
            for (int j = 0; j < N; j++) {
                if (s.charAt(j) == '.')
                    weight[i][j][0] = 0;
                else
                    weight[i][j][0] = s.charAt(j) - '0';
            }
        }

        for (int i = 0; i < N; i++) {
            String s = br.readLine();
            for (int j = 0; j < N; j++) {
                if (s.charAt(j) == '.')
                    weight[i][j][1] = 0;
                else
                    weight[i][j][1] = s.charAt(j) - '0';
            }
        }

        System.out.println(dijk());
    }

    static int dijk() {
        //pre,node,w1,w2,weight
        PriorityQueue<int[]> pq = new PriorityQueue<>((e1, e2) -> e1[4] - e2[4]);
        int ans = Integer.MAX_VALUE;

        for (int i = 0; i < N; i++) {
            if (weight[0][i][0] != 0)
                pq.add(new int[]{0, i,
                        weight[0][i][0], weight[0][i][1], weight[0][i][0] * weight[0][i][1]});
        }

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int pre = cur[0];
            int node = cur[1];
            int w1 = cur[2];
            int w2 = cur[3];
            int total = cur[4];

            //지나간 간선이면 넘김
            if (check[pre][node])
                continue;

            check[pre][node] = true;
            check[node][pre] = true;

            //1로 도착했을 때
            if (node == 1) {
                ans = Math.min(ans, total);
                continue;
            }

            for (int i = 0; i < N; i++) {
                if (check[node][i] || weight[node][i][0] == 0)
                    continue;

                int nw1 = w1 + weight[node][i][0];
                int nw2 = w2 + weight[node][i][1];
                pq.add(new int[]{node, i, nw1, nw2, nw1 * nw2});
            }
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}
