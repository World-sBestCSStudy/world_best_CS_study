import java.util.ArrayDeque;
import java.util.Deque;

public class Main {
    static int N, M;

    static class Node {
        Node[] child = new Node[2];
        int value;
    }

    static StringBuilder sb = new StringBuilder();
    static Deque<Integer> stack = new ArrayDeque<>();

    public static void main(String[] args) throws Exception {
        Reader in = new Reader();
        N = in.nextInt();

        Node root = new Node();

        for (int i = 0; i < N; i++) {
            int origin = in.nextInt();
            int num = origin;
            Node cur = root;

            for (int j = 0; j < 30; j++) {
                stack.push(num % 2);
                num = num >> 1;
            }

            for (int j = 0; j < 30; j++) {
                int temp = stack.pop();

                if (cur.child[temp] == null)
                    cur.child[temp] = new Node();

                cur = cur.child[temp];
            }
            cur.value = origin;
        }

        M = in.nextInt();

        for (int i = 0; i < M; i++) {
            int num = in.nextInt();
            Node cur = root;

            for (int j = 0; j < 30; j++) {
                stack.push(num % 2);
                num = num >> 1;
            }

            for (int j = 0; j < 30; j++) {
                int temp = stack.pop();
                int rev = temp == 0 ? 1 : 0;

                if (cur.child[rev] != null)
                    cur = cur.child[rev];
                else
                    cur = cur.child[temp];
            }

            sb.append(cur.value).append(" ");
        }

        System.out.println(sb);
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
