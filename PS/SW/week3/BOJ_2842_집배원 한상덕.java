import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Set;
import java.util.TreeSet;

//투포인터로 예상 피로도 범위를 탐색
//해당 피로도로 모든 K가 탐색가능하다면 left를 올려서 범위를 줄이고
//탐색이 불가능하다면 right를 올려서 범위를 찾아냄
//되는 범위 중 가장 낮은 피로도 값을 가질 때가 답
//값의 범위를 줄이기 위해 가능한 값의 범위를 미리 저장
public class Main {
    static int N, count, ans = Integer.MAX_VALUE;
    static char[][] map;
    static int[][] height;
    static int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1};//위부터 반시계방향
    static int[] dc = {0, -1, -1, -1, 0, 1, 1, 1};
    static int[] start = new int[2];
    static boolean[][] visit;
    static Set<Integer> set = new TreeSet<>();
    static int[] range;

    public static void main(String[] args) throws Exception {
        Reader in = new Reader();

        N = in.nextInt();

        map = new char[N][N];
        height = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                map[i][j] = in.nextChar();

                if (map[i][j] == 'P') {
                    start[0] = i;
                    start[1] = j;
                }

                if (map[i][j] == 'K')
                    count++;
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                height[i][j] = in.nextInt();
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

    static class Reader {
        final int SIZE = 1 << 13;
        byte[] buffer = new byte[SIZE];
        int index, size;

        char nextChar() throws Exception {
            byte c;
            while ((c = read()) < 32) ; // SPACE 분리라면 <=로, SPACE 무시라면 <로
            return (char) c;
        }

        int nextInt() throws Exception {
            int n = 0;
            byte c;
            boolean isMinus = false;
            while ((c = read()) <= 32) {
                if (size < 0) return -1;
            }
            if (c == 45) {
                c = read();
                isMinus = true;
            }
            do n = (n << 3) + (n << 1) + (c & 15);
            while (isNumber(c = read()));
            return isMinus ? ~n + 1 : n;
        }

        boolean isNumber(byte c) {
            return 47 < c && c < 58;
        }

        byte read() throws Exception {
            if (index == size) {
                size = System.in.read(buffer, index = 0, SIZE);
                if (size < 0) buffer[0] = -1;
            }
            return buffer[index++];
        }
    }
}
