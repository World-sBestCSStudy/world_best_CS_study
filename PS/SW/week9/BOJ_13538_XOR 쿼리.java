import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Main {
    static int M;

    static class Tree {
        Tree left;
        Tree right;
        int value;
    }

    static List<Tree> perTree = new ArrayList<>();
    static List<Tree> perTrie = new ArrayList<>();
    static Deque<Integer> stack = new ArrayDeque<>();
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {
        Reader in = new Reader();

        perTree.add(new Tree());
        treeInit(perTree.get(0), 1, 500000);

        perTrie.add(new Tree());
        trieInit(perTrie.get(0), 0);

        M = in.nextInt();

        for (int i = 0; i < M; i++) {
            int cmd = in.nextInt();

            //배열에 추가
            if (cmd == 1) {
                int x = in.nextInt();
                Tree preTree = perTree.get(perTree.size() - 1);
                Tree preTrie = perTrie.get(perTrie.size() - 1);
                Tree curTree = new Tree();
                Tree curTrie = new Tree();

                perTree.add(curTree);
                perTrie.add(curTrie);

                updateTree(preTree, curTree, 1, 500000, x);
                updateTrie(preTrie, curTrie, x);
            } else if (cmd == 2) {
                int L = in.nextInt();
                int R = in.nextInt();
                int x = in.nextInt();

                sb.append(trieQuery(perTrie.get(L - 1), perTrie.get(R), x)).append("\n");
            } else if (cmd == 3) {
                int k = in.nextInt();
                for (int j = 0; j < k; j++) {
                    perTree.remove(perTree.size() - 1);
                    perTrie.remove(perTrie.size() - 1);
                }
            } else if (cmd == 4) {
                int L = in.nextInt();
                int R = in.nextInt();
                int x = in.nextInt();
                sb.append(countQuery(perTree.get(L - 1), perTree.get(R), 1, 500000, x)).append("\n");
            } else {
                int L = in.nextInt();
                int R = in.nextInt();
                int k = in.nextInt();
                sb.append(rankQuery(perTree.get(L - 1), perTree.get(R), 1, 500000, k, 0)).append("\n");
            }
        }

        System.out.println(sb);
    }

    static void treeInit(Tree cur, int left, int right) {
        if (left == right) {
            return;
        }

        int mid = (left + right) >> 1;
        cur.left = new Tree();
        cur.right = new Tree();
        treeInit(cur.left, left, mid);
        treeInit(cur.right, mid + 1, right);
    }

    static void trieInit(Tree cur, int count) {
        if (count == 19)
            return;

        cur.left = new Tree();
        cur.right = new Tree();
        trieInit(cur.left, count + 1);
        trieInit(cur.right, count + 1);
    }

    static void updateTree(Tree pre, Tree cur, int left, int right, int idx) {
        if (left == right) {
            cur.value = pre.value + 1;
            return;
        }

        int mid = (left + right) >> 1;
        if (idx <= mid) {
            cur.left = new Tree();
            cur.right = pre.right;
            updateTree(pre.left, cur.left, left, mid, idx);
        } else {
            cur.right = new Tree();
            cur.left = pre.left;
            updateTree(pre.right, cur.right, mid + 1, right, idx);
        }

        cur.value = cur.left.value + cur.right.value;
    }

    static void updateTrie(Tree pre, Tree cur, int num) {
        binary(num);

        for (int i = 0; i < 19; i++) {
            int temp = stack.pop();
            if (temp == 0) {
                cur.right = pre.right;
                cur.left = new Tree();
                cur.left.value = pre.left.value + 1;
                cur = cur.left;
                pre = pre.left;
            } else {
                cur.left = pre.left;
                cur.right = new Tree();
                cur.right.value = pre.right.value + 1;
                cur = cur.right;
                pre = pre.right;
            }
        }
    }

    static int trieQuery(Tree pre, Tree cur, int num) {
        binary(num);
        int ans = 0;

        for (int i = 0; i < 19; i++) {
            int temp = stack.pop();
            if (temp == 1) {
                if (cur.left.value - pre.left.value >= 1) {
                    cur = cur.left;
                    pre = pre.left;
                } else {
                    ans += 1 << 18 - i;
                    cur = cur.right;
                    pre = pre.right;
                }
            } else {
                if (cur.right.value - pre.right.value >= 1) {
                    ans += 1 << 18 - i;
                    cur = cur.right;
                    pre = pre.right;
                } else {
                    cur = cur.left;
                    pre = pre.left;
                }
            }
        }

        return ans;
    }

    static int countQuery(Tree pre, Tree cur, int left, int right, int idx) {
        if (idx < left)
            return 0;

        if (idx >= right) {
            return cur.value - pre.value;
        }

        int mid = (left + right) >> 1;
        int lR = countQuery(pre.left, cur.left, left, mid, idx);
        int rR = countQuery(pre.right, cur.right, mid + 1, right, idx);

        return lR + rR;
    }

    static int rankQuery(Tree pre, Tree cur, int left, int right, int rank, int value) {
        if (left == right) {
            return right;
        }

        int mid = (left + right) >> 1;
        if (cur.left.value - pre.left.value + value >= rank) {
            return rankQuery(pre.left, cur.left, left, mid, rank, value);
        } else {
            return rankQuery(pre.right, cur.right, mid + 1, right, rank, value + cur.left.value - pre.left.value);
        }
    }

    static void binary(int num) {
        for (int i = 0; i < 19; i++) {
            stack.push(num % 2);
            num = num >> 1;
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
