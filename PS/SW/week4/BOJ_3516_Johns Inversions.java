import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {
    static int N;
    static int[][] input;
    static int[] tree, value;
    static Map<Integer, Integer> map = new HashMap<>();
    static long ans;

    public static void main(String[] args) throws Exception {
        Reader in = new Reader();

        N = in.nextInt();
        input = new int[N][2];
        value = new int[N];
        tree = new int[4 * N];

        for (int i = 0; i < N; i++) {
            input[i][0] = in.nextInt();
            input[i][1] = in.nextInt();
            value[i] = input[i][1];
        }

        Arrays.sort(input, (e1, e2) -> e1[0] == e2[0] ? e1[1] - e2[1] : e1[0] - e2[0]);
        Arrays.sort(value);

        int num = 2;
        map.put(value[0], 1);

        for (int i = 1; i < N; i++) {
            if (value[i] != value[i - 1])
                map.put(value[i], num++);
        }

        for (int i = 0; i < N; i++) {
            update(1, 1, N, map.get(input[i][1]));

            if (i != 0)
                ans += query(1, 1, N, map.get(input[i][1]) + 1);
        }

        System.out.println(ans);
    }

    static void update(int node, int left, int right, int idx) {
        if (idx < left || idx > right)
            return;

        if (left == right) {
            tree[node] += 1;
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

        if (qleft <= left) {
            return tree[node];
        }

        int mid = (left + right) >> 1;
        int lR = query(node << 1, left, mid, qleft);
        int rR = query(node << 1 | 1, mid + 1, right, qleft);

        return lR + rR;
    }

    static class Reader {
        final int SIZE = 1 << 13;
        byte[] buffer = new byte[SIZE];
        int index, size;

        int nextInt() throws Exception {
            int n = 0;
            byte c;
            boolean isMinus = false;
            while ((c = read()) <= 32) {
                if (size < 0) return -1;
            }
            if (c == 45) {
                c = read();
                isMinus = true;
            }
            do n = (n << 3) + (n << 1) + (c & 15);
            while (isNumber(c = read()));
            return isMinus ? ~n + 1 : n;
        }

        boolean isNumber(byte c) {
            return 47 < c && c < 58;
        }

        byte read() throws Exception {
            if (index == size) {
                size = System.in.read(buffer, index = 0, SIZE);
                if (size < 0) buffer[0] = -1;
            }
            return buffer[index++];
        }
    }
}
