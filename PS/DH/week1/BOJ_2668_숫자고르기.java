import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class BOJ_2668_숫자고르기 {
    static ArrayList<Integer> list = new ArrayList<>();
    static boolean[] visited;
    static int[] num;
    public static void main(String[] args) throws Exception{
        BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        num = new int[n + 1];
        for(int i = 1; i <= n; i++) {
            num[i] = Integer.parseInt(br.readLine());
        }
        visited = new boolean[n + 1];
        for(int i = 1; i <= n; i++) {
            visited[i] = true;
            dfs(i, i);
            visited[i] = false;
        }
        Collections.sort(list);
        StringBuilder sb= new StringBuilder();
        sb.append(list.size()).append("\n");
        for (Integer integer : list) {
            sb.append(integer).append("\n");
        }
        System.out.println(sb);
    }

    public static void dfs(int node, int start) {
        if(!visited[num[node]]) {
            visited[num[node]] = true;
            dfs(num[node], start);
            visited[num[node]] = false;
        }
        if(num[node] == start) list.add(start);
    }
}
