//BOJ_15685_드래곤 커브
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    static int[] dx = {0, -1, 0, 1}, dy = {1, 0, -1, 0};
    static boolean[][] v;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        v = new boolean[101][101];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int g = Integer.parseInt(st.nextToken());

            bfs(x, y, d, g);
        }

        int count = 0;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                if (v[i][j] && v[i + 1][j] && v[i][j + 1] && v[i + 1][j + 1]) count++;
            }
        }
        System.out.println(count);

    }

    //스택.. 현재에서 dir+1
    public static void bfs(int x, int y, int d, int g) {
        List<Integer> record = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();

        v[x][y] = true;

        y += dy[d];
        x += dx[d];
        v[x][y] = true;

        stack.push(d);
        record.add(d);

        for (int r = 0; r < g; r++) {

            while (!stack.isEmpty()) {
                int dir = stack.pop();
                int ndir = (dir + 1) % 4;

                x += dx[ndir];
                y += dy[ndir];

                v[x][y] = true;

                record.add(ndir);
            }

            for (int dir : record) {
                stack.push(dir);
            }

        }
    }
}
