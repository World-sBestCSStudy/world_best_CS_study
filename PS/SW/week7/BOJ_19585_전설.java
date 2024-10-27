import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int C, N, Q;

    static class Node {
        Node[] child = new Node[26];
        boolean end;
    }

    static StringBuilder sb = new StringBuilder();
    static Set<String> nickname = new HashSet<>();

    public static void main(String[] args) throws Exception {
        StringTokenizer st = new StringTokenizer(br.readLine());
        C = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());

        Node color = new Node();

        for (int i = 0; i < C; i++) {
            String s = br.readLine();
            Node cur = color;

            for (int j = 0; j < s.length(); j++) {
                if (cur.child[s.charAt(j) - 'a'] == null)
                    cur.child[s.charAt(j) - 'a'] = new Node();

                cur = cur.child[s.charAt(j) - 'a'];

                if (j == s.length() - 1)
                    cur.end = true;
            }
        }

        for (int i = 0; i < N; i++) {
            nickname.add(br.readLine());
        }

        Q = Integer.parseInt(br.readLine());
        for (int i = 0; i < Q; i++) {
            String s = br.readLine();
            Node cur = color;
            boolean chk = false;

            for (int j = 0; j < Math.min(1001, s.length() - 1); j++) {
                if (cur.child[s.charAt(j) - 'a'] == null)
                    break;

                cur = cur.child[s.charAt(j) - 'a'];
                if (cur.end) {
                    if (nickname.contains(s.substring(j + 1))) {
                        chk = true;
                        break;
                    }
                }
            }

            if (chk) sb.append("Yes").append("\n");
            else sb.append("No").append("\n");
        }
        System.out.println(sb);
    }
}
