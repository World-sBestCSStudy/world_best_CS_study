import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int T, N, M;
    static StringBuilder sb = new StringBuilder();
    static String[] remove;

    static class Node {
        Map<Character, Node> child = new HashMap<>();
        boolean remove;
    }

    public static void main(String[] args) throws Exception {
        T = Integer.parseInt(br.readLine());
        for (int t = 0; t < T; t++) {
            N = Integer.parseInt(br.readLine());
            remove = new String[N];
            Node root = new Node();
            int ans = 0;

            for (int i = 0; i < N; i++) {
                remove[i] = br.readLine();
            }

            M = Integer.parseInt(br.readLine());

            //전체 파일 삭제
            if (M == 0) {
                sb.append(1).append("\n");
                continue;
            }

            for (int i = 0; i < M; i++) {
                String s = br.readLine();
                Node cur = root;
                for (int j = 0; j < s.length(); j++) {
                    if (!cur.child.containsKey(s.charAt(j)))
                        cur.child.put(s.charAt(j), new Node());

                    cur = cur.child.get(s.charAt(j));
                }
            }

            for (int i = 0; i < N; i++) {
                Node cur = root;
                boolean chk = false;

                for (int j = 0; j < remove[i].length(); j++) {
                    //뒤로는 다 지워도 되는 대상
                    if (!cur.child.containsKey(remove[i].charAt(j))) {
                        Node next = new Node();
                        next.remove = true;
                        cur.child.put(remove[i].charAt(j), next);
                        break;
                    }

                    cur = cur.child.get(remove[i].charAt(j));

                    //이미 지워진 대상
                    if (cur.remove) {
                        chk = true;
                        break;
                    }
                }

                if (!chk)
                    ans++;
            }
            sb.append(ans).append("\n");
        }
        System.out.println(sb);
    }
}
