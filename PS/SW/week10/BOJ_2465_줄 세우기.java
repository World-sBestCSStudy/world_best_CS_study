import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static int[] input;
    static int[] tree;
    static int[] lines;
    static Deque<Integer> stack = new ArrayDeque<>();
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(br.readLine());

        input = new int[N + 1];
        lines = new int[N];
        tree = new int[4 * N];

        for (int i = 0; i < N; i++) {
            input[i] = Integer.parseInt(br.readLine());
        }
        Arrays.sort(input);

        init(1, 1, N);

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            lines[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = N - 1; i >= 0; i--) {
            int result = query(1, 1, N, lines[i] + 1, 0);
            stack.push(input[result]);
        }

        for (int i = 0; i < N; i++) {
            sb.append(stack.pop()).append("\n");
        }

        System.out.println(sb);
    }

    static void init(int node, int left, int right) {
        if (left == right) {
            tree[node] = 1;
            return;
        }

        int mid = (left + right) >> 1;
        init(node << 1, left, mid);
        init(node << 1 | 1, mid + 1, right);

        tree[node] = tree[node << 1] + tree[node << 1 | 1];
    }

    static int query(int node, int left, int right, int rank, int value) {
        if (left == right) {
            tree[node] = 0;
            return left;
        }

        int mid = (left + right) >> 1;

        int result;
        if (rank <= tree[node << 1] + value) {
            result = query(node << 1, left, mid, rank, value);
        } else {
            result = query(node << 1 | 1, mid + 1, right, rank, value + tree[node << 1]);
        }
        tree[node] = tree[node << 1] + tree[node << 1 | 1];
        return result;
    }
}
