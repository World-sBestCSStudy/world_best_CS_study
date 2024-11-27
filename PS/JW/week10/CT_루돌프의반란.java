import java.io.*;
import java.util.*;

public class CT_루돌프의반란 {
    static int N, M, P, C, D, rr, rc, deadSanta = 0;
    static int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1}, dc = {0, 1, 1, 1, 0, -1, -1, -1};    // 루돌프 - 8방향, 산타 - 상우하좌
    static int[][] map;
    static List<Santa> santas = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        P = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());
        map = new int[N][N];

        st = new StringTokenizer(br.readLine());
        rr = Integer.parseInt(st.nextToken()) - 1;
        rc = Integer.parseInt(st.nextToken()) - 1;
        map[rr][rc] = -1;   // 루돌프

        for (int i = 0; i < P; i++) {
            st = new StringTokenizer(br.readLine());
            int num = Integer.parseInt(st.nextToken());
            int sr = Integer.parseInt(st.nextToken()) - 1;
            int sc = Integer.parseInt(st.nextToken()) - 1;

            santas.add(new Santa(num, sr, sc));
            map[sr][sc] = num;  // ** 산타의 번호는 1 ~ P
        }

        Collections.sort(santas);   // 커스텀한 compareTo를 기준으로 정렬한다.

        for (int tc = 0; tc < M; tc++) {
            rudolph_move();
            for (int i = 0; i < P; i++) {
                Santa santa = santas.get(i);

                if (!santa.live) continue;

                if (santa.stun == 0) {      // 산타 움직일 수 있는 상태(기절 X)
                    santa_move(santa);
                } else if (santa.stun > 0) {    // 산타 기절
                    santa.stun--;
                }
            }

            for (int i = 0; i < P; i++) {
                if (santas.get(i).live)
                    santas.get(i).score++;  // 턴이 끝나고 살아남은 산타들 1점 추가
            }

            if (deadSanta == P){
                break;  // 모든 산타가 탈락했다면 탈출
            }
        }

        // 출력
        for (int i = 0; i < P; i++){
            System.out.print(santas.get(i).score+" ");
        }
    }


    static void rudolph_move() {
        int num = get_near_santa();
        Santa santa = santas.get(num - 1);  // ** 산타의 번호와 인덱스는 1 차이가 난다.

        int d = getDir(rr, rc, santa.r, santa.c);  // 루돌프가 이동할 방향 구하기

        int nr = rr + dr[d];
        int nc = rc + dc[d];

        if (map[nr][nc] == num) {  // 목표로 한 산타가 있다면 (어차피 제일 가까운 산타를 찾아가기 때문에 다른 산타를 만날 수는 없다)
            // 해당 위치의 산타 +C점 & 기절
            santa.score += C;
            santa.stun = santa.stun == 0 ? santa.stun + 2 : santa.stun + 1 ;    // ** 루돌프 다음에 산타가 움직이기 때문에 해당 턴에도 못 움직이고, 그 다음턴에도 못 움직이니까 +2

            bounce_santa(num, d, C);
        }

        map[rr][rc] = 0;
        rr = nr;
        rc = nc;
        map[rr][rc] = -1;
    }

    static void santa_move(Santa santa) {
        int d = -1;
        int dist = (int) getDist(santa.r, santa.c, rr, rc);
        for (int i = 0; i < 8; i += 2){     // 상우하좌 순서대로 살펴보면서, 해당 방향으로 이동했을 때 최단거리 이동인지
            int ny = santa.r + dr[i];
            int nx = santa.c + dc[i];

            if (!inRange(ny, nx)) continue; // 이동하려는 곳이 맵 밖
            if (map[ny][nx] > 0) continue;  // 이동하려는 곳에 산타

            int moved = (int) getDist(ny, nx, rr, rc);

            if (dist > moved){
                dist = moved;
                d = i;
            }
        }

        if (d == -1) return;    // 산타가 움직일 수 없다면 이동하지 않는다.

        map[santa.r][santa.c] = 0;
        santa.r += dr[d];
        santa.c += dc[d];

        if (map[santa.r][santa.c] == -1){     // 이동할 곳에 루돌프가 있다면
            santa.score += D;
            santa.stun++;
            bounce_santa(santa.num, (d + 4) % 8, D);
        } else {                    // 빈칸이라면
            map[santa.r][santa.c] = santa.num;
        }
    }

    static int get_near_santa() {
        int min = Integer.MAX_VALUE;
        int nearSanta = -1;
        for (int r = N - 1; r >= 0; r--) {     // r,c 가 큰 곳부터 살펴보기
            for (int c = N - 1; c >= 0; c--) {
                if (map[r][c] != 0 && map[r][c] != -1) {
                    int dist = (int) getDist(rr, rc, r, c);     // 루돌프 <-> 산타 거리
                    if (min > dist) {
                        min = dist;
                        nearSanta = map[r][c];  // 맵 위에 있는 산타라면
                    }
                }
            }
        }

        return nearSanta;  // 산타번호 전달
    }

    // num 번째 산타가 d 방향으로 P만큼 튕겨나감 -> 이후 연쇄작용
    static void bounce_santa(int num, int d, int P) {
        Queue<Integer> q = new ArrayDeque<>();
        q.add(num);
        int power = P;

        while (!q.isEmpty()) {
            Santa cur = santas.get(q.poll() - 1);

            int nr = cur.r + dr[d] * power;
            int nc = cur.c + dc[d] * power;

            if (inRange(nr, nc)) {
                if (map[nr][nc] != 0) {   // 밀려난 곳에 산타가 있다면 -> 상호작용
                    q.offer(map[nr][nc]);
                    power = 1;
                }
                map[nr][nc] = cur.num;
                cur.r = nr;
                cur.c = nc;
            } else {    // 범위를 벗어난 경우 -> 탈락
                cur.live = false;
                deadSanta++;
            }
        }
    }

    static boolean inRange(int r, int c) {  // 맵 안에 있는지
        if (r >= 0 && c >= 0 && r < N && c < N) return true;
        return false;
    }

    static int getDir(int r1, int c1, int r2, int c2) {
        // 부호만 남기자. 예를 들어, dy = -1 (위로가야함), dx = -1(왼쪽으로 가야함)
        int dy = r1 == r2 ? 0 : (r2 - r1) / Math.abs(r2 - r1);
        int dx = c1 == c2 ? 0 : (c2 - c1) / Math.abs(c2 - c1);

        int result = -1;
        for (int d = 0; d < 8; d++){
            if (dr[d] == dy && dc[d] == dx)
                result = d;
        }
        return result;
    }

    static double getDist(int r1, int c1, int r2, int c2) {     // 두 좌표 사이 거리
        return Math.pow(r1 - r2, 2) + Math.pow(c1 - c2, 2);
    }

    static class Santa implements Comparable<Santa> {
        int num, r, c, score, stun;
        boolean live;

        public Santa(int num, int r, int c) {
            this.num = num;
            this.r = r;
            this.c = c;
            this.score = 0;
            this.stun = 0;
            this.live = true;
        }

        // 산타를 번호 순서대로 저장 -> num을 가지고 산타 리스트에서 바로 불러오기 위해
        // ** 자료구조 정렬
        @Override
        public int compareTo(Santa santa) {
            if (santa.num > num)
                return -1;
            else if (santa.num < num)
                return 1;
            return 0;
        }
    }
}