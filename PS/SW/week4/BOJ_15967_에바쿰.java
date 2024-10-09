public class Main {
    static int N, Q1, Q2;
    static long[] tree;
    static int[] lazy;
    static int[] input;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {
        Reader in = new Reader();
        N = in.nextInt();
        Q1 = in.nextInt();
        Q2 = in.nextInt();

        tree = new long[4 * N];
        lazy = new int[4 * N];
        input = new int[N + 1];

        for (int i = 1; i <= N; i++) {
            input[i] = in.nextInt();
        }

        init(1, 1, N);

        for (int i = 0; i < Q1 + Q2; i++) {
            int cmd = in.nextInt();
            if (cmd == 1) {
                int qLeft = in.nextInt();
                int qRight = in.nextInt();

                long result = query(1, 1, N, qLeft, qRight);
                sb.append(result).append("\n");
            } else {
                int uLeft = in.nextInt();
                int uRight = in.nextInt();
                int value = in.nextInt();

                update(1, 1, N, uLeft, uRight, value);
            }
        }
        System.out.println(sb);
    }

    static void init(int node, int left, int right) {
        if (left == right) {
            tree[node] = input[left];
            return;
        }

        int mid = (left + right) >> 1;
        init(node << 1, left, mid);
        init(node << 1 | 1, mid + 1, right);

        tree[node] = tree[node << 1] + tree[node << 1 | 1];
    }

    static void update(int node, int left, int right, int uLeft, int uRight, int value) {
        lazy_update(node, left, right);

        if (right < uLeft || left > uRight)
            return;

        if (uLeft <= left && uRight >= right) {
            tree[node] += (right - left + 1) * (long) value;

            if (left != right) {
                lazy[node << 1] += value;
                lazy[node << 1 | 1] += value;
            }
            return;
        }

        int mid = (left + right) >> 1;
        update(node << 1, left, mid, uLeft, uRight, value);
        update(node << 1 | 1, mid + 1, right, uLeft, uRight, value);

        tree[node] = tree[node << 1] + tree[node << 1 | 1];
    }

    static long query(int node, int left, int right, int qLeft, int qRight) {
        lazy_update(node, left, right);

        if (right < qLeft || left > qRight)
            return 0;

        if (qLeft <= left && qRight >= right) {
            return tree[node];
        }

        int mid = (left + right) >> 1;
        long lR = query(node << 1, left, mid, qLeft, qRight);
        long rR = query(node << 1 | 1, mid + 1, right, qLeft, qRight);

        return lR + rR;
    }

    static void lazy_update(int node, int left, int right) {
        if (lazy[node] != 0) {

            tree[node] += (right - left + 1) * (long) lazy[node];

            if (left != right) {
                lazy[node << 1] += lazy[node];
                lazy[node << 1 | 1] += lazy[node];
            }

            lazy[node] = 0;
        }
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
