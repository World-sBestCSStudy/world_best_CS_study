import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static long ans;
    static int[] tree;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(br.readLine());

        tree = new int[4 * N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            int num = Integer.parseInt(st.nextToken());

            update(1, 1, N, num);

            if (i != 0)
                ans += query(1, 1, N, num + 1);
        }

        System.out.println(ans);
    }

    static void update(int node, int left, int right, int idx) {
        if (idx > right || idx < left)
            return;

        if (left == right) {
            tree[node] = 1;
            return;
        }

        int mid = (left + right) / 2;
        update(node << 1, left, mid, idx);
        update(node << 1 | 1, mid + 1, right, idx);

        tree[node] = tree[node << 1] + tree[node << 1 | 1];
    }


    static int query(int node, int left, int right, int qleft) {
        if (right < qleft)
            return 0;

        if (qleft <= left)
            return tree[node];

        int mid = (left + right) / 2;
        int lR = query(node << 1, left, mid, qleft);
        int rR = query(node << 1 | 1, mid + 1, right, qleft);


        return lR + rR;
    }
}
