import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_11505_구간곱구하기 {
    static long[] tree, input;
    static int N, M, K;
    static StringBuilder sb = new StringBuilder();
    static final int num = 1000000007;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        input = new long[N + 1];
        tree = new long[4 * N];

        for (int i = 1; i <= N; i++) {
            input[i] = Integer.parseInt(br.readLine());
        }

        init(1, 1, N);

        for (int i = 0; i < M + K; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            if (a == 1) {
                long c = Long.parseLong(st.nextToken());
                update(1, b, c, 1, N);
            } else {
                int c = Integer.parseInt(st.nextToken());
                sb.append(search(1, b, c, 1, N)).append("\n");
            }
        }
        System.out.println(sb);
    }

    static long search(int node, int start, int end, int left, int right) {
        if (start > right || end < left) return 1;
        if (start <= left && end >= right) return tree[node];

        int mid = (left + right) / 2;

        long leftValue = search(node * 2, start, end, left, mid);
        long rightValue = search(node * 2 + 1, start, end, mid + 1, right);

        return (leftValue * rightValue) % num;
    }

    static void update(int node, int idx, long value, int left, int right) {
        if (idx < left || idx > right) return;
        if (left == right) {
            tree[node] = value;
            return;
        }

        int mid = (left + right) / 2;

        update(node * 2, idx, value, left, mid);
        update(node * 2 + 1, idx, value, mid + 1, right);

        tree[node] = (tree[node * 2] * tree[node * 2 + 1]) % num;
    }

    static void init(int node, int left, int right) {
        if (left == right) {
            tree[node] = input[left];
            return;
        }
        int mid = (left + right) / 2;

        init(node * 2, left, mid);
        init(node * 2 + 1, mid + 1, right);

        tree[node] = (tree[node * 2] * tree[node * 2 + 1]) % num;
    }
}
