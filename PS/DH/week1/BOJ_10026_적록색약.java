import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BOJ_10026_적록색약 {
    static int N, ans1, ans2;
    static char[][] greed;
    static char[][] greed2;
    static int[] dy = {0,0,-1,1};
    static int[] dx = {-1,1,0,0};
    static boolean[][] visit;
    public static void main(String[] args) throws Exception{
        BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        greed = new char[N][N];
        greed2 = new char[N][N];
        for (int i = 0; i < N; i++) {
            String str = br.readLine();
            for (int j = 0; j < N; j++) {
                greed[i][j] = str.charAt(j);
                greed2[i][j] = greed[i][j] == 'B'?'B':'R';
            }
        }
        visit = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!visit[i][j]) {
                    ans1++;
                    dfs(i, j, greed, greed[i][j]);
                }
            }
        }

        visit = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!visit[i][j]) {
                    ans2++;
                    dfs(i, j, greed2, greed2[i][j]);
                }
            }
        }
        System.out.println(ans1 + " " + ans2);
    }
    static void dfs(int y, int x, char[][] grid, char c) {
        visit[y][x] = true;
        for (int d = 0; d < 4; d++) {
            int ny = y + dy[d];
            int nx = x + dx[d];
            if (ny < 0 || nx < 0 || ny >= N || nx >= N || visit[ny][nx] || grid[ny][nx] != c) continue;
            dfs(ny, nx, grid, c);
        }
    }


}
