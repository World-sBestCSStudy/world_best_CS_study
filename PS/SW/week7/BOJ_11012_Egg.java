import java.util.ArrayList;
import java.util.List;

public class Main {
    static int T, N, M;
    static List<Integer>[] map = new List[100002];
    static StringBuilder sb = new StringBuilder();

    static class Node {
        Node left;
        Node right;
        int value;
    }

    static Node[] pst = new Node[100002];

    public static void main(String[] args) throws Exception {
        Reader in = new Reader();
        T = in.nextInt();

        pst[0] = new Node();
        init(pst[0], 0, 100000);

        for (int t = 0; t < T; t++) {
            N = in.nextInt();
            M = in.nextInt();

            for (int i = 1; i <= 100001; i++) {
                map[i] = new ArrayList<>();
            }

            for (int i = 0; i < N; i++) {
                int x = in.nextInt() + 1;
                map[x].add(in.nextInt());
            }

            for (int i = 1; i <= 100001; i++) {
                pst[i] = pst[i - 1];
                Node prev = pst[i];

                for (Integer y : map[i]) {
                    pst[i] = new Node();
                    update(prev, pst[i], 0, 100000, y);
                    prev = pst[i];
                }
            }

            int ans = 0;

            for (int i = 0; i < M; i++) {
                int l = in.nextInt();
                int r = in.nextInt();
                int y1 = in.nextInt();
                int y2 = in.nextInt();

                ans += query(pst[r + 1], 0, 100000, y1, y2)
                        - query(pst[l], 0, 100000, y1, y2);
            }

            sb.append(ans).append("\n");
        }
        System.out.println(sb);
    }

    static void init(Node cur, int left, int right) {
        if (left == right)
            return;

        cur.left = new Node();
        cur.right = new Node();

        int mid = (left + right) >> 1;
        init(cur.left, left, mid);
        init(cur.right, mid + 1, right);
    }

    static void update(Node prev, Node cur, int left, int right, int idx) {
        if (left == right) {
            cur.value = prev.value + 1;
            return;
        }

        int mid = (left + right) >> 1;

        if (left > idx || mid < idx) {
            cur.left = prev.left;

            cur.right = new Node();
            update(prev.right, cur.right, mid + 1, right, idx);
        } else {
            cur.right = prev.right;

            cur.left = new Node();
            update(prev.left, cur.left, left, mid, idx);
        }

        cur.value = cur.left.value + cur.right.value;
    }

    static int query(Node cur, int left, int right, int qLeft, int qRight) {
        if (right < qLeft || left > qRight)
            return 0;

        if (qLeft <= left && qRight >= right)
            return cur.value;

        int mid = (left + right) >> 1;
        int lR = query(cur.left, left, mid, qLeft, qRight);
        int rR = query(cur.right, mid + 1, right, qLeft, qRight);

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
