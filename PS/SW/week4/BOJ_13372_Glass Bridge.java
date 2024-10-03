import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();
    static int[] tree;
    static int T, N;

    public static void main(String[] args) throws Exception {
        T = Integer.parseInt(br.readLine());
        for (int t = 0; t < T; t++) {
            N = Integer.parseInt(br.readLine());
            tree = new int[4 * N];
            long ans = 0;

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                int num = Integer.parseInt(st.nextToken());

                update(1, 1, N, num);

                if (i != 0) {
                    ans += query(1, 1, N, num + 1);
                }
            }
            sb.append(ans).append("\n");
        }

        System.out.println(sb);
    }

    static void update(int node, int left, int right, int idx) {
        if (idx < left || idx > right)
            return;

        if (left == right) {
            tree[node] = 1;
            return;
        }

        int mid = (left + right) >> 1;

        update(node << 1, left, mid, idx);
        update(node << 1 | 1, mid + 1, right, idx);

        tree[node] = tree[node << 1] + tree[node << 1 | 1];
    }

    static int query(int node, int left, int right, int qleft) {
        if (qleft > right)
            return 0;

        if (qleft <= left)
            return tree[node];

        int mid = (left + right) >> 1;

        int lR = query(node << 1, left, mid, qleft);
        int rR = query(node << 1 | 1, mid + 1, right, qleft);

        return lR + rR;
    }
}
