import java.util.ArrayList;
import java.util.List;

public class Main {
    static int T, n;
    static int[] tree;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {
        Reader in = new Reader();
        T = in.nextInt();

        for (int t = 0; t < T; t++) {
            n = in.nextInt();
            int order = 0;
            long ans = 0;
            List<int[]> input = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                input.add(new int[]{in.nextInt(), in.nextInt()});
            }

            input.sort((e1, e2) -> e1[1] - e2[1]);

            int before = input.get(0)[1];
            input.get(0)[1] = ++order;
            for (int i = 1; i < n; i++) {
                if (input.get(i)[1] == before)
                    input.get(i)[1] = order;
                else {
                    before = input.get(i)[1];
                    input.get(i)[1] = ++order;
                }
            }

            input.sort((e1, e2) -> e1[0] == e2[0] ? e2[1] - e1[1] : e1[0] - e2[0]);

            tree = new int[4 * order];

            for (int i = 0; i < n; i++) {
                int idx = input.get(i)[1];

                ans += query(1, 1, order, idx);
                update(1, 1, order, idx);
            }

            sb.append(ans).append("\n");
        }
        System.out.println(sb);
    }

    static int query(int node, int left, int right, int idx) {
        if (idx > right)
            return 0;

        if (idx <= left)
            return tree[node];

        int mid = (left + right) >> 1;
        int lR = query(node << 1, left, mid, idx);
        int rR = query(node << 1 | 1, mid + 1, right, idx);

        return lR + rR;
    }

    static void update(int node, int left, int right, int idx) {
        if (left > idx || right < idx)
            return;

        if (left == right) {
            tree[node]++;
            return;
        }

        int mid = (left + right) >> 1;
        update(node << 1, left, mid, idx);
        update(node << 1 | 1, mid + 1, right, idx);

        tree[node] = tree[node << 1] + tree[node << 1 | 1];
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
