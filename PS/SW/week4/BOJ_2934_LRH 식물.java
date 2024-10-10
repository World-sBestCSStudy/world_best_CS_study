//매 식물의 위치가 들어올 때 세그먼트 트리에 저장된 L,R위치의 값을 합친 후 출력후
//L,R위치의 값 0처리
//L과R 사이의 범위의 값들에 +1 처리를 느리게 갱신

public class Main {
    static int N;
    static int[] tree = new int[400001];
    static int[] lazy = new int[400001];
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {
        Reader in = new Reader();
        N = in.nextInt();

        for (int i = 0; i < N; i++) {
            int L = in.nextInt();
            int R = in.nextInt();
            int temp = 0;

            if (i != 0) {
                temp += query(1, 1, 100000, L);
                temp += query(1, 1, 100000, R);
            }
            sb.append(temp).append("\n");

            int uLeft = L + 1;
            int uRight = R - 1;
            if (uRight >= uLeft) {
                update(1, 1, 100000, uLeft, uRight);
            }
        }

        System.out.println(sb);
    }

    static void update(int node, int left, int right, int uLeft, int uRight) {
        if (left > uRight || right < uLeft)
            return;

        if (left >= uLeft && right <= uRight) {
            lazy[node] += 1;
            return;
        }

        int mid = (left + right) >> 1;
        update(node << 1, left, mid, uLeft, uRight);
        update(node << 1 | 1, mid + 1, right, uLeft, uRight);
    }

    static int query(int node, int left, int right, int idx) {
        lazy_update(node, left, right);

        if (idx < left || idx > right)
            return 0;

        if (left == right) {
            int temp = tree[node];
            tree[node] = 0;
            return temp;
        }

        int mid = (left + right) >> 1;
        int lR = query(node << 1, left, mid, idx);
        int rR = query(node << 1 | 1, mid + 1, right, idx);

        return lR + rR;
    }

    static void lazy_update(int node, int left, int right) {
        if (lazy[node] != 0) {
            if (left != right) {
                lazy[node << 1] += lazy[node];
                lazy[node << 1 | 1] += lazy[node];
            } else
                tree[node] += lazy[node];

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
