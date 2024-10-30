public class Main {
    static int N, M, seq = 1;
    static int[] input;

    static class Node {
        Node left;
        Node right;
        long value;
    }

    static Node[] pst = new Node[100001];
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {
        Reader in = new Reader();

        N = in.nextInt();
        input = new int[N];
        for (int i = 0; i < N; i++) {
            input[i] = in.nextInt();
        }

        pst[0] = new Node();
        init(pst[0], 0, N - 1);

        M = in.nextInt();
        for (int i = 0; i < M; i++) {
            int cmd = in.nextInt();

            //업데이트
            if (cmd == 1) {
                int idx = in.nextInt() - 1;
                int value = in.nextInt();

                pst[seq] = new Node();
                update(pst[seq - 1], pst[seq], 0, N - 1, idx, value);
                seq++;
            }
            //출력 쿼리
            else {
                int k = in.nextInt();
                int qLeft = in.nextInt() - 1;
                int qRight = in.nextInt() - 1;
                sb.append(query(pst[k], 0, N - 1, qLeft, qRight)).append("\n");
            }
        }
        System.out.println(sb);
    }

    static void init(Node cur, int left, int right) {
        if (left == right) {
            cur.value = input[left];
            return;
        }

        int mid = (left + right) >> 1;
        cur.left = new Node();
        cur.right = new Node();
        init(cur.left, left, mid);
        init(cur.right, mid + 1, right);

        cur.value = cur.left.value + cur.right.value;
    }

    static void update(Node prev, Node cur, int left, int right, int idx, int value) {
        if (left == right) {
            cur.value = value;
            return;
        }

        int mid = (left + right) >> 1;
        if (idx < left || idx > mid) {
            cur.left = prev.left;

            cur.right = new Node();
            update(prev.right, cur.right, mid + 1, right, idx, value);
        } else {
            cur.right = prev.right;

            cur.left = new Node();
            update(prev.left, cur.left, left, mid, idx, value);
        }

        cur.value = cur.left.value + cur.right.value;
    }

    static long query(Node cur, int left, int right, int qLeft, int qRight) {
        if (qLeft > right || qRight < left) {
            return 0;
        }

        if (qLeft <= left && qRight >= right) {
            return cur.value;
        }

        int mid = (left + right) >> 1;
        long lR = query(cur.left, left, mid, qLeft, qRight);
        long rR = query(cur.right, mid + 1, right, qLeft, qRight);

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
