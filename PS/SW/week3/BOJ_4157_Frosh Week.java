import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int n;
    static int[][] input;
    static int[] sorted;
    static int[] tree;
    static long ans;

    public static void main(String[] args) throws Exception {
        n = Integer.parseInt(br.readLine());

        input = new int[n][2];
        sorted = new int[n + 1];
        tree = new int[4 * n];

        for (int i = 0; i < n; i++) {
            input[i][0] = Integer.parseInt(br.readLine());
            input[i][1] = i + 1;
        }

        //값 압축
        Arrays.sort(input, (e1, e2) -> Integer.compare(e1[0], e2[0]));
        for (int i = 0; i < n; i++) {
            sorted[input[i][1]] = i + 1;
        }

        for (int i = 1; i <= n; i++) {
            update(1, 1, n, sorted[i]);

            if (i != 1)
                ans += query(1, 1, n, sorted[i] + 1);
        }

        System.out.println(ans);
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
        if (right < qleft)
            return 0;

        if (qleft <= left) {
            return tree[node];
        }

        int mid = (left + right) >> 1;
        int lR = query(node << 1, left, mid, qleft);
        int rR = query(node << 1 | 1, mid + 1, right, qleft);

        return lR + rR;
    }
}
