import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N,M,K;
    static long[] sTree;
    static long[] input;
    static StringBuilder sb = new StringBuilder();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        input = new long[N+1];
        for (int i = 1; i <= N; i++) {
            input[i] = Long.parseLong(br.readLine());
        }

        sTree = new long[N * 4];
        makeSTree(1, 1, N);

        for (int i = 0; i < M+K; i++) {
            st = new StringTokenizer(br.readLine());
            int cmd = Integer.parseInt(st.nextToken());
            if (cmd == 1) {
                int b = Integer.parseInt(st.nextToken());
                long c = Long.parseLong(st.nextToken());
                change(1, 1, N, b, c);
            }else {
                int b = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                sb.append(query(1, 1, N, b, c)).append("\n");
            }
        }
        System.out.println(sb);
    }

    private static long query(int node, int left, int right, int start, int end) {
        if (left > end || right < start) return 0;
        if (left >= start && right <= end) return sTree[node];
        int mid = (left + right) / 2;
        long sumLeft = query(node*2, left, mid, start, end);
        long sumRight = query(node*2+1, mid+1, right, start, end);

        return sumLeft + sumRight;
    }

    static void makeSTree(int node, int left, int right) {
        if (left == right) {
            sTree[node] = input[left];
            return;
        }
        int mid = (left + right) / 2;
        makeSTree(node * 2, left, mid);
        makeSTree(node * 2 + 1, mid + 1, right);

        sTree[node] = sTree[node*2] + sTree[node*2 + 1];
    }

    static void change(int node, int left, int right, int idx, long num) {
        if (left > idx || right < idx) return;

        if (left == right) {
            sTree[node] = num;
            return;
        }
        int mid = (left + right) / 2;
        change(node * 2, left, mid, idx, num);
        change(node * 2 + 1, mid + 1, right, idx, num);

        sTree[node] = sTree[node*2] + sTree[node*2 + 1];
    }

}
