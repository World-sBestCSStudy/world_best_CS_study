import java.util.ArrayDeque;
import java.util.Deque;

public class Main {
    static int N;

    static class Node {
        Node[] child = new Node[2];
        int cnt = 0;
    }

    static Node root = new Node();
    static StringBuilder sb = new StringBuilder();
    static Deque<Integer> stack = new ArrayDeque<>();

    public static void main(String[] args) throws Exception {
        Reader in = new Reader();
        N = in.nextInt();

        //배열에 0을 기본으로 넣음
        init();

        for (int i = 0; i < N; i++) {
            int cmd = in.nextInt();
            int num = in.nextInt();

            if (cmd == 1)
                insert(num);
            else if (cmd == 2)
                delete(num);
            else
                sb.append(query(num)).append("\n");
        }
        System.out.println(sb);
    }

    static void init() {
        Node cur = root;

        for (int i = 0; i < 30; i++) {
            cur.child[0] = new Node();
            cur = cur.child[0];
            cur.cnt++;
        }
    }

    static void insert(int num) {
        for (int i = 0; i < 30; i++) {
            stack.push(num % 2);
            num /= 2;
        }

        Node cur = root;

        for (int i = 0; i < 30; i++) {
            int temp = stack.pop();

            if (cur.child[temp] == null)
                cur.child[temp] = new Node();

            cur = cur.child[temp];
            cur.cnt++;
        }
    }

    static void delete(int num) {
        for (int i = 0; i < 30; i++) {
            stack.push(num % 2);
            num /= 2;
        }

        Node cur = root;

        for (int i = 0; i < 30; i++) {
            int temp = stack.pop();

            cur = cur.child[temp];
            cur.cnt--;
        }
    }

    static int query(int num) {
        for (int i = 0; i < 30; i++) {
            stack.push(num % 2);
            num /= 2;
        }

        Node cur = root;
        int ans = 0;

        for (int i = 1; i <= 30; i++) {
            int temp = stack.pop();
            int rev = temp == 0 ? 1 : 0;

            //아래로 갈 수 있는 경우
            if (cur.child[rev] != null && cur.child[rev].cnt != 0) {
                ans += Math.pow(2, 30 - i);
                cur = cur.child[rev];
            } else cur = cur.child[temp];
        }

        return ans;
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
