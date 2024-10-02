import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N, M, max, num = 1, roomSumMax;
    static StringTokenizer st;
    static int[][] map;
    static int[][] room;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, -1, 0, 1};
    static List<Integer> count = new ArrayList<>();
    static Set<Integer>[] adlist;

    public static void main(String[] args) throws Exception {
        st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[M][N];
        room = new int[M][N];
        count.add(0);
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (room[i][j] == 0) {
                    count.add(bfs(i, j));
                    max = Math.max(max, count.get(num++));
                }
            }
        }

        adlist = new Set[num];
        for (int i = 1; i <= num - 1; i++) {
            adlist[i] = new HashSet<>();
        }

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                for (int dir = 0; dir < 4; dir++) {
                    int nr = i + dr[dir];
                    int nc = j + dc[dir];

                    if (canGo(nr, nc) && !check(i, j, dir)) {
                        adlist[room[i][j]].add(room[nr][nc]);
                    }
                }
            }
        }

        for (int i = 1; i <= num - 1; i++) {
            for (Integer nextRoom : adlist[i]) {
                if (nextRoom != i) {
                    int temp = count.get(i) + count.get(nextRoom);
                    roomSumMax = Math.max(roomSumMax, temp);
                }
            }
        }

        System.out.println(num - 1);
        System.out.println(max);
        System.out.println(roomSumMax);
    }

    static int bfs(int r, int c) {
        Deque<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{r, c});
        room[r][c] = num;
        int count = 0;
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int cr = cur[0];
            int cc = cur[1];
            count++;

            for (int dir = 0; dir < 4; dir++) {
                int nr = cr + dr[dir];
                int nc = cc + dc[dir];

                if (check(cr, cc, dir) && canGo(nr, nc) && room[nr][nc] == 0) {
                    room[nr][nc] = num;
                    queue.add(new int[]{nr, nc});
                }
            }
        }
        return count;
    }

    static boolean check(int r, int c, int dir) {
        //북쪽 2
        if (dir == 0) {
            if ((map[r][c] & 2) == 2)
                return false;
        }
        //서쪽 1
        else if (dir == 1) {
            if ((map[r][c] & 1) == 1)
                return false;
        }
        //남쪽 8
        else if (dir == 2) {
            if ((map[r][c] & 8) == 8)
                return false;
        }
        //동쪽 4
        else {
            if ((map[r][c] & 4) == 4)
                return false;
        }
        return true;
    }

    static boolean canGo(int r, int c) {
        return r >= 0 && r < M && c >= 0 && c < N;
    }
}
