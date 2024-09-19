import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BOJ_24230_트리색칠하기 {
    static int N, cnt;
    static ArrayList<ArrayList<Integer>> adjList;
    static int[] colors; //칠해야 하는 색상
    static StringTokenizer st;
    static boolean[] visit;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        adjList = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            adjList.add(new ArrayList<>());
        }
        colors = new int[N+1];
        visit = new boolean[N+1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            colors[i] = Integer.parseInt(st.nextToken());
        }
        for (int i = 0; i < N-1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            adjList.get(a).add(b);
            adjList.get(b).add(a);
        }
        visit[1] = true;
        dfs(1, 0);
        System.out.println(cnt);
    }
    static void dfs(int node, int color) {
        if (colors[node] != color) {
            cnt++;
            color = colors[node];
        }
        ArrayList<Integer> list = adjList.get(node);
        for (int i = 0; i < list.size(); i++) {
            if (!visit[list.get(i)]) {
                visit[list.get(i)] = true;
                dfs(list.get(i), color);
            }
        }
    }
}
