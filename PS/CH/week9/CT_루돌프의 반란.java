//CT_루돌프의 반란
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int N, M, P, C, D;
    static boolean[] isDead;
    static int[] stun;
    static Node[] santas;
    static Node rudol;
    static int[] socres;

    static int[] dxR = {-1, -1, -1, 0, 1, 1, 1, 0}, dyR = {-1, 0, 1, 1, 1, 0, -1, -1};
    static int[] dxS = {-1, 0, 1, 0}, dyS = {0, 1, 0, -1};
    static int[][] board;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;


        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        P = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());
        board = new int[N][N];
        socres = new int[P + 1];

        st = new StringTokenizer(br.readLine());
        rudol = new Node(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1);
        board[rudol.x][rudol.y] = -1;

        santas = new Node[P + 1];
        for (int i = 0; i < P; i++) {
            st = new StringTokenizer(br.readLine());
            int idx = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            santas[idx] = new Node(x, y);
            board[x][y] = idx;
        }

        stun = new int[P + 1];
        isDead = new boolean[P + 1];
        for(int i =0; i<M; i++){
            moveRudol(i);
            moveSanta(i);
            boolean flag = bonus();
            if(!flag) break;
        }

        StringBuilder answer = new StringBuilder();
        for(int i =1; i<=P; i++) answer.append(socres[i]).append(" ");

        System.out.println(answer);


    }

    public static void moveRudol(int k) {
        int min = Integer.MAX_VALUE;
        int idx = -1;
        for (int i = 1; i <= P; i++) {
            if (isDead[i]) continue;

            int dist = getDist(rudol.x, rudol.y, santas[i].x, santas[i].y);

            if (min > dist) {
                min = dist;
                idx = i;
            } else if (min == dist && (santas[i].x > santas[idx].x || (santas[i].x == santas[idx].x && santas[i].y > santas[idx].y))) {
                idx = i;
            }
        }


        min = Integer.MAX_VALUE;
        int dir = 0;
        for (int i = 0; i < 8; i++) {
            int nx = rudol.x + dxR[i];
            int ny = rudol.y + dyR[i];

            if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;

            int dist = getDist(santas[idx].x, santas[idx].y, nx, ny);
            if (min > dist) {
                min = dist;
                dir = i;
            }
        }


        board[rudol.x][rudol.y] = 0;
        rudol.x = rudol.x + dxR[dir];
        rudol.y = rudol.y + dyR[dir];


        if (board[rudol.x][rudol.y] != 0) {
            collision(rudol.x, rudol.y, dir, C, k);
            board[rudol.x][rudol.y] = -1;
        } else {
            board[rudol.x][rudol.y] = -1;
        }


    }

    public static void moveSanta(int k) {
        for (int i = 1; i < P + 1; i++) {
            if (isDead[i] || stun[i] > k) continue;

            //

            int minDist = getDist(rudol.x, rudol.y, santas[i].x, santas[i].y);
            int dir = -1;
            for (int j = 0; j < 4; j++) {
                int nx = santas[i].x + dxS[j];
                int ny = santas[i].y + dyS[j];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;

                int dist = getDist(rudol.x, rudol.y, nx, ny);
                if (minDist > dist && board[nx][ny]<=0) {
                    minDist = dist;
                    dir = j;
                }

            }

            if (dir == -1 || board[santas[i].x + dxS[dir]][santas[i].y + dyS[dir]] > 0) {
                continue;
            }

            board[santas[i].x][santas[i].y] = 0;

            santas[i].x += dxS[dir];
            santas[i].y += dyS[dir];

            if(board[santas[i].x][santas[i].y]==-1){
                board[santas[i].x][santas[i].y] = i;
                collision(santas[i].x,santas[i].y,((((dir * 2) + 1) % 8) + 4) % 8, D,k);
                board[rudol.x][rudol.y] = -1;
            }else{
                board[santas[i].x][santas[i].y] = i;
            }


        }

    }

    public static boolean bonus() {
        boolean flag = false;
        for (int i = 1; i <= P; i++) {
            if (isDead[i]) continue;
            socres[i]++;
            flag=true;
        }
        return flag;
    }


    public static void collision(int x, int y, int dir, int power, int k) {

        //0: x, 1: y, 2: idx, 3: power
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{x, y, board[x][y], power});


        stun[board[x][y]] = k + 2;
        socres[board[x][y]] += power;
        board[x][y] = 0;


        while (!q.isEmpty()) {
            int[] info = q.poll();

            int nx = info[0];
            int ny = info[1];

            for (int i = 0; i < info[3]; i++) {
                nx += dxR[dir];
                ny += dyR[dir];
            }

            if (nx < 0 || ny < 0 || nx >= N || ny >= N) {
                isDead[info[2]] = true;
                break;
            }


            if (board[nx][ny] == 0) {
                board[nx][ny] = info[2];
                santas[info[2]].x = nx;
                santas[info[2]].y = ny;

            } else {
                q.offer(new int[]{nx, ny, board[nx][ny], 1});
                board[nx][ny] = info[2];
                santas[info[2]].x = nx;
                santas[info[2]].y = ny;
            }
        }

    }

    public static int getDist(int x1, int y1, int x2, int y2) {
        return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
    }

    public static void print() {
        for (int i = 0; i < N; i++) System.out.println(Arrays.toString(board[i]));
        System.out.println();
    }

    static class Node {
        int x, y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}
