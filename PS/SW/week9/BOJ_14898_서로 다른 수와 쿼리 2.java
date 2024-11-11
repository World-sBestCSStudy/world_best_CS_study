import java.util.HashMap;
import java.util.Map;

public class Main {
    static int N, Q;
    static int[] input;
    static Map<Integer, Integer> map = new HashMap<>();
    static Node[] perSeg;

    static class Node {
        Node left;
        Node right;
        int value;

    }

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {
        Reader in = new Reader();
        N = in.nextInt();

        input = new int[N + 1];
        perSeg = new Node[N + 1];

        for (int i = 1; i <= N; i++) {
            input[i] = in.nextInt();

            if (!map.containsKey(input[i]))
                map.put(input[i], 0);
        }

        perSeg[0] = new Node();
        init(perSeg[0], 1, N);

        for (int i = 1; i <= N; i++) {
            perSeg[i] = new Node();
            if (map.get(input[i]) != 0) {
                dynamic(perSeg[i - 1], perSeg[i], 1, N, map.get(input[i]), 0);
                update(perSeg[i], 1, N, i);
            } else {
                dynamic(perSeg[i - 1], perSeg[i], 1, N, i, 1);
            }

            map.put(input[i], i);
        }

        Q = in.nextInt();
        int pre = 0;
        for (int i = 0; i < Q; i++) {
            int left = in.nextInt() + pre;
            int right = in.nextInt();

            int result = query(perSeg[right], 1, N, left, right);
            sb.append(result).append("\n");
            pre = result;
        }

        System.out.println(sb);
    }

    static void init(Node cur, int left, int right) {
        if (left == right) {
            return;
        }

        int mid = (left + right) >> 1;
        cur.left = new Node();
        cur.right = new Node();

        init(cur.left, left, mid);
        init(cur.right, mid + 1, right);
    }

    static void dynamic(Node pre, Node cur, int left, int right, int idx, int value) {
        if (left == right) {
            cur.value = value;
            return;
        }

        int mid = (left + right) >> 1;
        if (idx <= mid) {
            cur.right = pre.right;
            cur.left = new Node();
            dynamic(pre.left, cur.left, left, mid, idx, value);
        } else {
            cur.left = pre.left;
            cur.right = new Node();
            dynamic(pre.right, cur.right, mid + 1, right, idx, value);
        }

        cur.value = cur.left.value + cur.right.value;
    }

    static void update(Node cur, int left, int right, int idx) {
        if (right < idx || left > idx)
            return;

        if (left == right) {
            cur.value = 1;
            return;
        }

        int mid = (left + right) >> 1;
        update(cur.left, left, mid, idx);
        update(cur.right, mid + 1, right, idx);

        cur.value = cur.left.value + cur.right.value;
    }

    static int query(Node cur, int left, int right, int qLeft, int qRight) {
        if (left > qRight || right < qLeft)
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
