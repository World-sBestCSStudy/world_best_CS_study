import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.TreeSet;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static int[] adlist;
    static boolean[] visitA;
    static boolean[] visitB;

    static Set<Integer> ans = new TreeSet<>();

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(br.readLine());

        adlist = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            adlist[i] = Integer.parseInt(br.readLine());
        }

        for (int i = 1; i <= N; i++) {
            visitA = new boolean[N + 1];
            visitB = new boolean[N + 1];

            visitA[i] = true;
            dfs(i, i);
        }

        System.out.println(ans.size());
        for (Integer num : ans) {
            System.out.println(num);
        }
    }

    static void dfs(int node, int start) {
        if (node < start)
            return;

        if (visitB[adlist[node]]) {
            check();
            return;
        }

        visitB[adlist[node]] = true;
        visitA[adlist[node]] = true;
        dfs(adlist[node], start);
    }

    static void check() {
        for (int i = 1; i <= N; i++) {
            if (visitA[i] != visitB[i])
                return;
        }

        for (int i = 1; i <= N; i++) {
            if (visitA[i])
                ans.add(i);
        }
    }
}