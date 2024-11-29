import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int L, C;
    static char[] input;
    static char[] tgt;
    static Set<Character> set = new HashSet<>();
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {
        init();
        st = new StringTokenizer(br.readLine());
        L = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        input = new char[C];
        tgt = new char[L];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < C; i++) {
            input[i] = st.nextToken().charAt(0);
        }

        Arrays.sort(input);

        comb(0, 0);

        System.out.println(sb);
    }

    static void comb(int num, int idx) {
        if (idx == L) {
            if (check()) {
                for (char c : tgt) {
                    sb.append(c);
                }
                sb.append("\n");
            }
            return;
        }

        if (num == C)
            return;

        tgt[idx] = input[num];
        comb(num + 1, idx + 1);
        comb(num + 1, idx);
    }

    static boolean check() {
        int con = 0;
        int vow = 0;

        for (char c : tgt) {
            if (set.contains(c))
                vow++;
            else
                con++;
        }

        return vow >= 1 && con >= 2;
    }

    static void init() {
        set.add('a');
        set.add('e');
        set.add('i');
        set.add('o');
        set.add('u');
    }
}
