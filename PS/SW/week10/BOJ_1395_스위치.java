public class Main {
    static int N, M;
    static int[] tree;
    static boolean[] lazy;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {
        Reader in = new Reader();
        N = in.nextInt();
        M = in.nextInt();

        tree = new int[4 * N];
        lazy = new boolean[4 * N];

        for (int i = 0; i < M; i++) {
            int cmd = in.nextInt();

            //반전
            if (cmd == 0) {
                int qLeft = in.nextInt();
                int qright = in.nextInt();
                update(1, 1, N, qLeft, qright);
            }
            //구간별 켜진 스위치 개수 세기
            else {
                int qLeft = in.nextInt();
                int qright = in.nextInt();
                sb.append(query(1, 1, N, qLeft, qright)).append("\n");
            }
        }

        System.out.println(sb);
    }

    static void update(int node, int left, int right, int qLeft, int qRight) {
        lazy_update(node, left, right);
        
        if (left > qRight || right < qLeft)
            return;

        if (qLeft <= left && qRight >= right) {
            tree[node] = right - left + 1 - tree[node];

            if (left != right) {
                lazy[node << 1] = !lazy[node << 1];
                lazy[node << 1 | 1] = !lazy[node << 1 | 1];
            }

            return;
        }

        int mid = (left + right) >> 1;
        update(node << 1, left, mid, qLeft, qRight);
        update(node << 1 | 1, mid + 1, right, qLeft, qRight);

        tree[node] = tree[node << 1] + tree[node << 1 | 1];
    }

    static int query(int node, int left, int right, int qLeft, int qRight) {
        lazy_update(node, left, right);

        if (left > qRight || right < qLeft)
            return 0;

        if (qLeft <= left && qRight >= right)
            return tree[node];

        int mid = (left + right) >> 1;
        int lR = query(node << 1, left, mid, qLeft, qRight);
        int rR = query(node << 1 | 1, mid + 1, right, qLeft, qRight);

        return lR + rR;
    }

    static void lazy_update(int node, int left, int right) {
        if (!lazy[node])
            return;

        tree[node] = right - left + 1 - tree[node];
        lazy[node] = false;

        if (left != right) {
            lazy[node << 1] = !lazy[node << 1];
            lazy[node << 1 | 1] = !lazy[node << 1 | 1];
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
