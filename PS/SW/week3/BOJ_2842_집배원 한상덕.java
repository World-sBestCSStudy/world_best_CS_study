import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

//이분 탐색으로 예상 피로도를 탐색한 후에 해당 피로도로 모든 K가 탐색가능하다면 수를 줄이고
//탐색이 불가능하다면 수를 늘리는 식으로 필요한 피로도를 찾아냄
//값의 범위를 줄이기 위해 가능한 값의 범위를 미리 저장
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N, count, ans = Integer.MAX_VALUE;
    static char[][] map;
    static int[][] height;
    static StringTokenizer st;
    static int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1};//위부터 반시계방향
    static int[] dc = {0, -1, -1, -1, 0, 1, 1, 1};
    static int[] start = new int[2];
    static boolean[][] visit;
    static Set<Integer> set = new TreeSet<>();
    static int[] range;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(br.readLine());

        map = new char[N][N];
        height = new int[N][N];

        for (int i = 0; i < N; i++) {
            String s = br.readLine();
            for (int j = 0; j < N; j++) {
                map[i][j] = s.charAt(j);

                if (map[i][j] == 'P') {
                    start[0] = i;
                    start[1] = j;
                }

                if (map[i][j] == 'K')
                    count++;
            }
        }

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                height[i][j] = Integer.parseInt(st.nextToken());

                //범위를 N으로 줄이기 위해 set에 담음
                set.add(height[i][j]);
            }
        }

        range = new int[set.size()];
        int idx = 0;
        for (Integer num : set) {
            range[idx++] = num;
        }

        search();

        System.out.println(ans);
    }

    static void search() {
        int left = 0;
        int right = 0;

        while (right < range.length) {
            if (left > right) {
                right++;
                continue;
            }
            
            boolean result = bfs(range[left], range[right]);
            if (!result)
                right++;
            else {
                ans = Math.min(ans, range[right] - range[left]);
                left++;
            }
        }
    }

    static boolean bfs(int min, int max) {
        Deque<int[]> queue = new ArrayDeque<>();
        int startH = height[start[0]][start[1]];
        if (startH < min || startH > max)
            return false;

        visit = new boolean[N][N];
        visit[start[0]][start[1]] = true;
        queue.add(start);

        int deliveryCount = 0;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int cr = cur[0];
            int cc = cur[1];

            //우편물을 배송했을 때
            if (map[cr][cc] == 'K')
                deliveryCount++;

            //우편물 배송을 완료했을 때
            if (count == deliveryCount)
                return true;

            for (int dir = 0; dir < 8; dir++) {
                int nr = cr + dr[dir];
                int nc = cc + dc[dir];

                if (canGo(nr, nc) && height[nr][nc] >= min && height[nr][nc] <= max) {
                    visit[nr][nc] = true;
                    queue.add(new int[]{nr, nc});
                }
            }
        }
        return false;
    }

    static boolean canGo(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < N && !visit[r][c];
    }
}
