import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static int n;
    static int[] input;
    static int[] tree;
    static Map<Integer, Integer> map = new HashMap<>();

    public static void main(String[] args) throws Exception {
        n = Integer.parseInt(br.readLine());

        input = new int[n + 1];
        tree = new int[4 * n];
        input[0] = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            input[i] = Integer.parseInt(br.readLine());
        }

        //값 좌표 압축
        int[] temp = Arrays.copyOf(input, n + 1);
        Arrays.sort(temp);
        for (int i = 1; i <= n; i++) {
            map.put(temp[i], i);
        }

        for (int i = 1; i <= n; i++) {
            update(1, 1, n, map.get(input[i]));

            sb.append(query(1, 1, n, map.get(input[i]) - 1)).append("\n");
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

    static int query(int node, int left, int right, int qright) {
        if (qright < left)
            return 0;

        if (right <= qright) {
            return tree[node];
        }

        int mid = (left + right) >> 1;
        int lR = query(node << 1, left, mid, qright);
        int rR = query(node << 1 | 1, mid + 1, right, qright);

        return lR + rR;
    }
}