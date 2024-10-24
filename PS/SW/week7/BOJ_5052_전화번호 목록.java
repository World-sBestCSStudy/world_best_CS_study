import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int T, N;
    static StringBuilder sb = new StringBuilder();
    static String[] input;

    static class Node {
        Node[] child = new Node[10];
        boolean end = false;
    }

    public static void main(String[] args) throws Exception {
        T = Integer.parseInt(br.readLine());
        for (int t = 0; t < T; t++) {
            N = Integer.parseInt(br.readLine());

            Node root = new Node();
            boolean yes = true;
            input = new String[N];

            for (int i = 0; i < N; i++) {
                input[i] = br.readLine();
            }

            Arrays.sort(input);

            for (int i = 0; i < N; i++) {
                if (!yes)
                    break;
                String s = input[i];

                Node cur = root;
                for (int j = 0; j < s.length(); j++) {
                    int num = s.charAt(j) - '0';

                    if (cur.child[num] == null) {
                        cur.child[num] = new Node();
                    }

                    cur = cur.child[num];

                    if (cur.end) {
                        yes = false;
                        sb.append("NO").append("\n");
                        break;
                    }

                    if (j == s.length() - 1)
                        cur.end = true;
                }
            }

            if (yes)
                sb.append("YES").append("\n");
        }
        System.out.println(sb);
    }
}
