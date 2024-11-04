import java.io.*;
import java.util.*;

public class SWEA_1767_프로세서연결하기 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int T, N, max, ans;
    static int[][] map;

    static List<int[]> core = new ArrayList<>();
    static boolean[] select;
    static int[] dy = {-1,1,0,0};
    static int[] dx = {0,0,-1,1};
    public static void main(String[] args) throws Exception{
        T = Integer.parseInt(br.readLine().trim());
        for (int tc = 1; tc <= T; tc++){
            N = Integer.parseInt(br.readLine().trim());
            map = new int[N][N];
            core.clear();
            for (int i = 0; i < N; i++){
                st = new StringTokenizer(br.readLine().trim());
                for (int j = 0; j < N; j++){
                    map[i][j] = Integer.parseInt(st.nextToken());
                    if (map[i][j] == 1){
                        if (i == 0 || i == N-1 || j == 0 || j == N-1) continue;
                        core.add(new int[]{i, j});  // 연결되지 않은 코어들
                    }
                }
            }

            select = new boolean[core.size()];
            ans = Integer.MAX_VALUE;
            max = Integer.MIN_VALUE;

            for (int i = core.size(); i > 0; i--){
                comb(0, 0, i);

                if (ans < Integer.MAX_VALUE) break;
            }

            sb.append("#").append(tc).append(" ").append(ans).append("\n");
        }

        System.out.println(sb);
    }

    static void comb(int srcIdx, int tgtIdx, int tot){
        if (tgtIdx == tot){
            dfs(0, 0);
            return;
        }

        if (srcIdx == core.size()) return;

        for (int i = srcIdx; i < core.size(); i++){
            select[i] = true;
            comb(i+1, tgtIdx+1, tot);
            select[i] = false;
        }
    }

    static void dfs(int idx, int cnt){  // 몇 번째 코어 보고있는지, 현재까지 연결한 전선 수
        if (idx == core.size()){    // 마지막 코어까지 다 봤다면
            ans = Math.min(ans, cnt);
            return;
        }

        if (!select[idx]){      // 연결할 코어가 아니면 바로 다음 코어
            dfs(idx+1, cnt);
            return;
        }

        int[] cur = core.get(idx);
        for (int d = 0; d < 4; d++){
            boolean flag = false;   // 연결 여부
            int t = 0;              // 현재코어 연결하는데 사용된 전선길이

            int ny = cur[0];
            int nx = cur[1];

            // 연결
            while (true){
                ny += dy[d];
                nx += dx[d];

                if (ny < 0 || ny >= N || nx < 0 || nx >= N){    // 범위를 벗어나면 == 벽에 닿으면 연결
                    flag = true;
                    break;
                }

                if (map[ny][nx] != 0) break;    // 중간에 전선/코어를 만나면 중지 <= 교차 X

                map[ny][nx] = 2;
                t++;
            }

            if (flag) dfs(idx+1, cnt+t);    // 연결했다면 연결된채로 다음 코어

            // 원복
            while (true){
                ny -= dy[d];
                nx -= dx[d];

                if (ny == cur[0] && nx == cur[1])   // 원래까지 돌아왔다면 탈출
                    break;

                map[ny][nx] = 0;
            }
        }
    }
}
