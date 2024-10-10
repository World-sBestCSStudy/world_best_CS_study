//SWEA_왕실의 기사 대결
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int L, N, Q;
    static int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};

    static int[] r, c, h, w, k, originK;
    static int[] nr, nc;
    static int[][] board;
    static int[] dmg;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        L = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());

        r = new int[N];
        c = new int[N];
        h = new int[N];
        w = new int[N];
        k = new int[N];
        dmg = new int[N];
        originK=new int[N];

        nr = new int[N];
        nc = new int[N];
        board = new int[L][L];
        for (int i = 0; i < L; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < L; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            r[i] = Integer.parseInt(st.nextToken()) - 1;
            c[i] = Integer.parseInt(st.nextToken()) - 1;
            h[i] = Integer.parseInt(st.nextToken());
            w[i] = Integer.parseInt(st.nextToken());
            k[i] = Integer.parseInt(st.nextToken());
            originK[i] = k[i];
        }

        for (int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine());
            int id = Integer.parseInt(st.nextToken()) - 1;
            int dir = Integer.parseInt(st.nextToken());
            move(id,dir);
        }

        int answer = 0;
        for(int i=0;i<N; i++){
            if(k[i]<=0) continue;
            answer+=originK[i]-k[i];
        }
        System.out.println(answer);

    }

    public static void move(int id, int dir) {
        if (k[id] <= 0) return;

        if (tryMove(id, dir)) {
            for (int i = 0; i < N; i++) {
                r[i] = nr[i];
                c[i] = nc[i];
                if(i!=id) k[i] -= dmg[i];
            }
        }
    }

    public static boolean tryMove(int id, int dir) {
        Queue<Integer> q = new LinkedList<>();
        boolean[] v = new boolean[N];
        q.offer(id);

        for(int i =0; i<N; i++){
            nr[i] = r[i];
            nc[i] =c[i];
            dmg[i] = 0;
        }

        v[id] = true;
        while (!q.isEmpty()) {
            int x = q.poll();

            nr[x] += dx[dir];
            nc[x] += dy[dir];

            if (nr[x] < 0 || nc[x] < 0 || nr[x]+h[x]-1 >= L || nc[x]+w[x]-1 >= L) return false;

            for (int i = nr[x]; i < nr[x] + h[x]; i++) {
                for (int j = nc[x]; j < nc[x] + w[x]; j++) {
                    if (board[i][j] == 2) return false;
                    if (board[i][j] == 1) dmg[x]++;
                }
            }

            for (int i = 0; i < N; i++) {
                if (v[i] ||  k[i] <= 0) continue;

                if (nr[x] + h[x] - 1 < r[i] || nr[x] > r[i] + h[i] - 1 || r[i] + h[i] - 1 < nr[x] || nr[x] + h[x] - 1 < r[i])
                    continue;
                if (c[i] > nc[x] + w[x] - 1 || c[i] + w[i] - 1 < nc[x] || nc[x] > c[i] + w[i] - 1 || nc[x] + w[x] - 1 < c[i])
                    continue;


                v[i] = true;
                q.offer(i);
            }
        }
        return true;
    }
}