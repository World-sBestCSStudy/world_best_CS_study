public class Main {
    static int N;
    static int[] input;
    static int[][] tree;
    static int M;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {
        Reader in = new Reader();

        N = in.nextInt();
        input = new int[N + 1];
        tree = new int[4 * N][];

        for (int i = 1; i <= N; i++) {
            input[i] = in.nextInt();
        }

        init(1, 1, N);

        M = in.nextInt();
        for (int i = 0; i < M; i++) {
            int qLeft = in.nextInt();
            int qRight = in.nextInt();
            int value = in.nextInt();

            sb.append(query(1, 1, N, qLeft, qRight, value)).append("\n");
        }

        System.out.println(sb);
    }

    static void init(int node, int left, int right) {
        if (left == right) {
            tree[node] = new int[]{input[left]};
            return;
        }

        int mid = (left + right) >> 1;
        init(node << 1, left, mid);
        init(node << 1 | 1, mid + 1, right);

        merge(node);
    }

    static void merge(int node) {
        int left = node << 1;
        int right = node << 1 | 1;
        tree[node] = new int[tree[left].length + tree[right].length];

        int leftIdx = 0;
        int rightIdx = 0;
        for (int i = 0; i < tree[node].length; i++) {
            if (leftIdx == tree[left].length) tree[node][i] = tree[right][rightIdx++];
            else if (rightIdx == tree[right].length) tree[node][i] = tree[left][leftIdx++];
            else if (tree[left][leftIdx] <= tree[right][rightIdx]) tree[node][i] = tree[left][leftIdx++];
            else tree[node][i] = tree[right][rightIdx++];
        }
    }

    static int query(int node, int left, int right, int qLeft, int qRight, int value) {
        if (left > qRight || right < qLeft) {
            return 0;
        }

        if (qLeft <= left && qRight >= right) {
            return search(node, value);
        }

        int mid = (left + right) >> 1;
        int lR = query(node << 1, left, mid, qLeft, qRight, value);
        int rR = query(node << 1 | 1, mid + 1, right, qLeft, qRight, value);

        return lR + rR;
    }

    static int search(int node, int value) {
        int left = 0;
        int right = tree[node].length - 1;

        while (left <= right) {
            int mid = (left + right) >> 1;

            if (tree[node][mid] > value) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return tree[node].length - right - 1;
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
