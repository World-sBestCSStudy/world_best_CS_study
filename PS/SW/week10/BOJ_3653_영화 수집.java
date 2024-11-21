public class Main {
    static int T, n, m, idx;
    static int[] location;
    static int[] tree;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {
        Reader in = new Reader();
        T = in.nextInt();

        for (int t = 0; t < T; t++) {
            n = in.nextInt();
            m = in.nextInt();

            tree = new int[4 * (n + m)];
            location = new int[n + 1];
            idx = n + 1;

            for (int i = 1; i <= n; i++) {
                location[i] = n + 1 - i;
            }

            init(1, 1, n + m);

            for (int i = 0; i < m; i++) {
                int num = in.nextInt();
                sb.append(query(1, 1, n + m, location[num] + 1)).append(" ");

                remove(1, 1, n + m, location[num]);
                location[num] = idx++;
                add(1, 1, n + m, location[num]);
            }
            sb.append("\n");
        }

        System.out.println(sb);
    }

    static void init(int node, int left, int right) {
        if (left > n)
            return;

        if (left == right) {
            tree[node] = 1;
            return;
        }

        int mid = (left + right) >> 1;
        init(node << 1, left, mid);
        init(node << 1 | 1, mid + 1, right);

        tree[node] = tree[node << 1] + tree[node << 1 | 1];
    }

    static int query(int node, int left, int right, int qLeft) {
        if (right < qLeft)
            return 0;

        if (qLeft <= left)
            return tree[node];

        int mid = (left + right) >> 1;
        int lR = query(node << 1, left, mid, qLeft);
        int rR = query(node << 1 | 1, mid + 1, right, qLeft);

        return lR + rR;
    }

    static void remove(int node, int left, int right, int idx) {
        if (left > idx || right < idx)
            return;

        if (left == right) {
            tree[node] = 0;
            return;
        }

        int mid = (left + right) >> 1;
        remove(node << 1, left, mid, idx);
        remove(node << 1 | 1, mid + 1, right, idx);

        tree[node] = tree[node << 1] + tree[node << 1 | 1];
    }

    static void add(int node, int left, int right, int idx) {
        if (left > idx || right < idx)
            return;

        if (left == right) {
            tree[node] = 1;
            return;
        }

        int mid = (left + right) >> 1;
        add(node << 1, left, mid, idx);
        add(node << 1 | 1, mid + 1, right, idx);

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
