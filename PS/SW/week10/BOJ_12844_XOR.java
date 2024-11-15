public class Main {
    static int N, M;
    static int[] input;
    static int[] tree;
    static int[] lazy;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {
        Reader in = new Reader();

        N = in.nextInt();
        input = new int[N];
        tree = new int[4 * N];
        lazy = new int[4 * N];

        for (int i = 0; i < N; i++) {
            input[i] = in.nextInt();
        }

        init(1, 0, N - 1);

        M = in.nextInt();
        for (int i = 0; i < M; i++) {
            int cmd = in.nextInt();

            //update
            if (cmd == 1) {
                int qLeft = in.nextInt();
                int qRight = in.nextInt();
                int value = in.nextInt();

                update(1, 0, N - 1, qLeft, qRight, value);
            }
            //구간 xor값 출력
            else {
                int qLeft = in.nextInt();
                int qRight = in.nextInt();

                sb.append(query(1, 0, N - 1, qLeft, qRight)).append("\n");
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

        tree[node] = tree[node << 1] ^ tree[node << 1 | 1];
    }

    static void update(int node, int left, int right, int qLeft, int qRight, int value) {
        lazy_update(node, left, right);

        if (left > qRight || right < qLeft)
            return;

        if (qLeft <= left && qRight >= right) {
            //홀수개인경우
            if ((right - left) % 2 == 0)
                tree[node] ^= value;

            if (left != right) {
                lazy[node << 1] ^= value;
                lazy[node << 1 | 1] ^= value;
            }

            return;
        }

        int mid = (left + right) >> 1;
        update(node << 1, left, mid, qLeft, qRight, value);
        update(node << 1 | 1, mid + 1, right, qLeft, qRight, value);

        tree[node] = tree[node << 1] ^ tree[node << 1 | 1];
    }

    static void lazy_update(int node, int left, int right) {
        if (lazy[node] != 0) {

            if ((right - left) % 2 == 0)
                tree[node] ^= lazy[node];

            if (left != right) {
                lazy[node << 1] ^= lazy[node];
                lazy[node << 1 | 1] ^= lazy[node];
            }

            lazy[node] = 0;
        }
    }

    static int query(int node, int left, int right, int qLeft, int qRight) {
        lazy_update(node, left, right);

        if (left > qRight || right < qLeft)
            return 0;

        if (qLeft <= left && qRight >= right) {
            return tree[node];
        }

        int mid = (left + right) >> 1;
        int lR = query(node << 1, left, mid, qLeft, qRight);
        int rR = query(node << 1 | 1, mid + 1, right, qLeft, qRight);

        return lR ^ rR;
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
