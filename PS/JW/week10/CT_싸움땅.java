import java.io.*;
import java.util.*;

public class CT_싸움땅 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int N, M, K;
    static PriorityQueue<Integer>[][] gunMap;  // ** 2차원 배열 PQ
    static int[][] playerMap;
    static int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};    // x, y
    static int[] result;
    static class Player{
        int no, x, y, d, stat, gun;
        public Player(int no, int x, int y, int d, int stat, int gun){
            this.no = no;
            this.x = x;
            this.y = y;
            this.d = d;
            this.stat = stat;
            this.gun = gun;
        }
    }
    static List<Player> players = new ArrayList<>();

    public static void main(String[] args) throws Exception{
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        gunMap = new PriorityQueue[N][N];
        playerMap = new int[N][N];
        for (int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++){
                gunMap[i][j] = new PriorityQueue<>((a, b) -> b -a);   // 내림차순의 pq 생성
                int num = Integer.parseInt(st.nextToken());
                if (num != 0)
                    gunMap[i][j].offer(num);
            }
        }

        result = new int[M+1];   // 0 dummy
        for (int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            int d = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());

            playerMap[x][y] = i+1;
            players.add(new Player(i+1, x, y, d, s, 0));
        }

        for (int k = 0; k < K; k++){
            for (int i = 0; i < players.size(); i++){
                Player player = players.get(i);
                playerMap[player.x][player.y] = 0;  // player가 있던 자리 비워주기 -> ** 채워주기
                player_go(player);
                if (playerMap[player.x][player.y] > 0){
                    fight(player.no, playerMap[player.x][player.y]);
                } else {
                    change_gun(player.no, player.x, player.y);
                    playerMap[player.x][player.y] = player.no;
                }
            }
        }

        for (int i = 1; i <= players.size(); i++){
            System.out.print(result[i]+" ");
        }
    }

    static void change_gun(int no, int x, int y){
        Player player = players.get(no-1);

        if (gunMap[x][y].isEmpty()) return;

        int max = gunMap[x][y].peek();

        if (max > player.gun){
            if (player.gun != 0)
                gunMap[x][y].offer(player.gun);
            player.gun = gunMap[x][y].poll();
        }
    }

    static void fight(int a, int b){
        Player playerA = players.get(a-1);
        Player playerB = players.get(b-1);

        int sumA = playerA.stat + playerA.gun;
        int sumB = playerB.stat + playerB.gun;

        Player winner, loser;
        if (sumA == sumB){
            winner = playerA.stat > playerB.stat ? playerA : playerB;
            loser = playerA.stat > playerB.stat ? playerB : playerA;
        } else {
            winner = sumA > sumB ? playerA : playerB;
            loser = sumA > sumB ? playerB : playerA;
        }

        result[winner.no] += Math.abs(sumA - sumB);

        gunMap[loser.x][loser.y].offer(loser.gun);
        loser.gun = 0;
        loser_go(loser);

        change_gun(winner.no, winner.x, winner.y);
        playerMap[winner.x][winner.y] = winner.no;
    }

    static void loser_go(Player p){
        for (int i = 0; i < 4; i++) {
            int nx = p.x + dx[p.d];
            int ny = p.y + dy[p.d];

            if (!inRange(nx, ny) || playerMap[nx][ny] > 0) {
                p.d = (p.d + 1) % 4;
            } else {
                p.x = nx;
                p.y = ny;
                playerMap[nx][ny] = p.no;
                change_gun(p.no, nx, ny);
                break;
            }
        }
    }

    static void player_go(Player p){
        int nx = p.x + dx[p.d];
        int ny = p.y + dy[p.d];

        if (!inRange(nx, ny)){
            p.d = (p.d+2) % 4;
            nx = p.x + dx[p.d];
            ny = p.y + dy[p.d];
        }

        p.x = nx;
        p.y = ny;
    }

    static boolean inRange(int nx, int ny){
        if (nx >= 0 && nx < N && ny >= 0 && ny < N) return true;
        return false;
    }
}