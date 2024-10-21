public class Main {
    static int N, M;
    static int[] input;
    static int[][] tree;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {
        Reader in = new Reader();
        N = in.nextInt();
        input = new int[N + 1];
        tree = new int[4 * N][2];

        for (int i = 1; i <= N; i++) {
            input[i] = in.nextInt();
        }

        init(1, 1, N);

        M = in.nextInt();
        for (int i = 0; i < M; i++) {
            int cmd = in.nextInt();
            if (cmd == 1) {
                int idx = in.nextInt();
                int value = in.nextInt();
                update(1, 1, N, idx, value);
            } else {
                int qLeft = in.nextInt();
                int qRight = in.nextInt();
                int[] result = query(1, 1, N, qLeft, qRight);
                sb.append(result[0] + result[1]).append("\n");
            }
        }

        System.out.println(sb);
    }

    static void init(int node, int left, int right) {
        if (left == right) {
            tree[node][0] = input[left];
            return;
        }

        int mid = (left + right) >> 1;
        init(node << 1, left, mid);
        init(node << 1 | 1, mid + 1, right);

        if (tree[node << 1][0] >= tree[node << 1 | 1][0]) {
            tree[node][0] = tree[node << 1][0];
            tree[node][1] = Math.max(tree[node << 1][1], tree[node << 1 | 1][0]);
        } else {
            tree[node][0] = tree[node << 1 | 1][0];
            tree[node][1] = Math.max(tree[node << 1][0], tree[node << 1 | 1][1]);
        }
    }

    static void update(int node, int left, int right, int idx, int value) {
        if (idx < left || idx > right)
            return;

        if (left == right) {
            tree[node][0] = value;
            return;
        }

        int mid = (left + right) >> 1;
        update(node << 1, left, mid, idx, value);
        update(node << 1 | 1, mid + 1, right, idx, value);

        if (tree[node << 1][0] >= tree[node << 1 | 1][0]) {
            tree[node][0] = tree[node << 1][0];
            tree[node][1] = Math.max(tree[node << 1][1], tree[node << 1 | 1][0]);
        } else {
            tree[node][0] = tree[node << 1 | 1][0];
            tree[node][1] = Math.max(tree[node << 1][0], tree[node << 1 | 1][1]);
        }
    }

    static int[] query(int node, int left, int right, int qLeft, int qRight) {
        if (qLeft > right || qRight < left)
            return new int[]{0, 0};

        if (qLeft <= left && qRight >= right)
            return tree[node];

        int mid = (left + right) >> 1;
        int[] lR = query(node << 1, left, mid, qLeft, qRight);
        int[] rR = query(node << 1 | 1, mid + 1, right, qLeft, qRight);

        if (lR[0] >= rR[0]) {
            return new int[]{lR[0], Math.max(lR[1], rR[0])};
        } else {
            return new int[]{rR[0], Math.max(lR[0], rR[1])};
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
