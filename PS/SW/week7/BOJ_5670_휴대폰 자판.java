import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static StringBuilder sb = new StringBuilder();

    static class Node {
        Node[] child = new Node[26];
        int childCount = 0;
        int first;
        boolean end = false;
    }

    static String input = "";
    static String[] list;

    public static void main(String[] args) throws Exception {
        while ((input = br.readLine()) != null && !(input.isEmpty())) {
            N = Integer.parseInt(input);

            Node root = new Node();
            list = new String[N];
            int sum = N;

            //trie 생성
            for (int i = 0; i < N; i++) {
                list[i] = br.readLine();
                Node cur = root;

                for (int j = 0; j < list[i].length(); j++) {
                    int temp = list[i].charAt(j) - 'a';

                    if (cur.child[temp] == null) {
                        cur.child[temp] = new Node();
                        cur.childCount++;

                        if (cur.childCount == 1)
                            cur.first = temp;
                    }

                    cur = cur.child[temp];
                }

                cur.end = true;
            }

            for (int i = 0; i < N; i++) {
                Node cur = root.child[list[i].charAt(0) - 'a'];

                for (int j = 1; j < list[i].length(); j++) {
                    if (!cur.end && cur.childCount == 1) {
                        cur = cur.child[cur.first];
                        continue;
                    }

                    cur = cur.child[list[i].charAt(j) - 'a'];
                    sum++;
                }
            }

            sb.append(String.format("%.2f", (double) sum / N)).append("\n");
        }

        System.out.println(sb);
    }
}
