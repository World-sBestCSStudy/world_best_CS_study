import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BOJ_13265_색칠하기 {
    static int n,m;
    static ArrayList<ArrayList<Integer>> adjList;
    static StringTokenizer st;
    static int[] colors; //1, 2
    static StringBuilder sb = new StringBuilder();
    static boolean flag;
    public static void main(String[] args) throws Exception{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int T = Integer.parseInt(br.readLine());

        for (int t = 0; t < T; t++) {
            st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
            adjList = new ArrayList<>();
            for (int i = 0; i <= n; i++) {
                adjList.add(new ArrayList<>());
            }
            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                adjList.get(a).add(b);
                adjList.get(b).add(a);
            }
            colors = new int[n+1];
            flag = true;

            for (int i = 1; i <= n; i++) {
                if (colors[i] == 0) {
                    colors[i] = 1;
                    dfs(i, 1);
                }
            }

            if (flag) sb.append("possible").append("\n");
            else sb.append("impossible").append("\n");
        }
        System.out.println(sb);
    }

    static void dfs(int node, int color) {
        if (!flag) return;
        ArrayList<Integer> list = adjList.get(node);

        for (int i = 0; i < list.size(); i++) {
            int num = list.get(i);
            if (colors[num] == color) { //연결된 게 같은 색상의 원이라면
                flag = false;
                return;
            }else if (colors[num] == 0) { //색상이 없는 원이라면
                colors[num] = color == 1? 2 : 1;
                dfs(num, color == 1? 2 : 1);
            }//다른 색상이 칠해져 있다면
        }
    }
}
