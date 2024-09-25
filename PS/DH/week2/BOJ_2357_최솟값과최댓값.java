import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static final int[] identity = new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE};
    static int[] num;
    static int[][] sTree;
    static int N, M;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        num = new int[N + 1];
        sTree = new int[4 * N][2];

        for (int i = 1; i <= N; i++) {
            num[i] = Integer.parseInt(br.readLine());
        }

        init(1, 1, N);

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int[] ans = query(1, 1, N, a, b);
            System.out.println(ans[0] + " " + ans[1]);
        }
    }

    static int[] query(int node, int left, int right, int start, int end) {
        if (left > end || right < start) return identity;

        if (left >= start && right <= end) return sTree[node];

        int mid = (left + right) / 2;
        int[] leftVal = query(node*2, left, mid, start, end);
        int[] rightVal = query(node*2 + 1, mid + 1, right, start, end);

        return new int[]{Math.min(leftVal[0], rightVal[0]), Math.max(leftVal[1], rightVal[1])};
    }


    static void init ( int node, int left, int right){
        if (left == right) {
            sTree[node][0] = num[left];
            sTree[node][1] = num[left];
            return;
        }

        int mid = (left + right) / 2;

        init(node * 2, left, mid);
        init(node * 2 + 1, mid + 1, right);
        sTree[node][0] = Math.min(sTree[node * 2][0], sTree[node * 2 + 1][0]);
        sTree[node][1] = Math.max(sTree[node * 2][1], sTree[node * 2 + 1][1]);
    }
}
