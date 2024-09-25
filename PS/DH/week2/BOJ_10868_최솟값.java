import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static final int min = Integer.MAX_VALUE;
    static int[] num;
    static int[] sTree;
    static int N, M;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        num = new int[N + 1];
        sTree = new int[4 * N];

        for (int i = 1; i <= N; i++) {
            num[i] = Integer.parseInt(br.readLine());
        }

        init(1, 1, N);

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int ans = query(1, 1, N, a, b);
            System.out.println(ans);
        }
    }

    static int query(int node, int left, int right, int start, int end) {
        if (left > end || right < start) return min;

        if (left >= start && right <= end) return sTree[node];

        int mid = (left + right) / 2;
        int leftVal = query(node*2, left, mid, start, end);
        int rightVal = query(node*2 + 1, mid + 1, right, start, end);

        return Math.min(leftVal, rightVal);
    }


    static void init ( int node, int left, int right){
        if (left == right) {
            sTree[node] = num[left];
            return;
        }

        int mid = (left + right) / 2;

        init(node * 2, left, mid);
        init(node * 2 + 1, mid + 1, right);
        sTree[node] = Math.min(sTree[node * 2], sTree[node * 2 + 1]);
    }
}
