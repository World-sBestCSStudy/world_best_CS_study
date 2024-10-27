import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N, ans;
    static int[] input;
    static int[][] bin;

    static class Node {
        Node[] child = new Node[2];
    }

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(br.readLine());
        input = new int[N];
        bin = new int[N][30];
        Node root = new Node();

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            input[i] = Integer.parseInt(st.nextToken());
            int num = input[i];
            Node cur = root;

            for (int j = 0; j < 30; j++) {
                bin[i][29 - j] = (num % 2);
                num = num >> 1;
            }

            for (int j = 0; j < 30; j++) {
                if (cur.child[bin[i][j]] == null)
                    cur.child[bin[i][j]] = new Node();

                cur = cur.child[bin[i][j]];
            }
        }

        for (int i = 0; i < N; i++) {
            Node cur = root;
            int xor = 0;

            for (int j = 0; j < 30; j++) {
                int rev = bin[i][j] == 0 ? 1 : 0;

                if (cur.child[rev] != null) {
                    xor += 1 << 29 - j;
                    cur = cur.child[rev];
                } else
                    cur = cur.child[bin[i][j]];
            }
            ans = Math.max(xor, ans);
        }

        System.out.println(ans);
    }
}
