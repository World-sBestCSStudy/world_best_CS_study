//SWEA_2112_보호필름
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Solution {
    static int[][] board;
    static int[][] copy;
    static int D, W, K;
    static int min;
    static List<Integer> list;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder answer = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {
            st = new StringTokenizer(br.readLine());
            D = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            list = new ArrayList<>();
            min = Integer.MAX_VALUE;
            board = new int[D][W];
            copy = new int[D][W];


            for (int i = 0; i < D; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < W; j++) {
                    board[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            init();
            select(new boolean[D], 0, 0);
            answer.append("#").append(t).append(" ").append(min).append("\n");
        }

        System.out.println(answer);
    }

    public static void select(boolean[] v, int start, int t) {
        if(min <= t) return;

        if(push()){
            min=Math.min(min,t);
            return;
        }
        for(int i =start; i<D; i++){
            if(!v[i]){
                v[i] = true;

                change(i, 0);
                select(v,i+1,t+1);

                change(i,1);
                select(v,i+1,t+1);


                for(int j=0; j<W; j++){
                    copy[i][j] = board[i][j];
                }

                v[i] = false;
            }
        }

    }


    public static void change(int x, int flag) {
        for (int i = 0; i < W; i++) {
            copy[x][i] = flag;
        }

    }

    public static boolean push() {
        boolean result = true;
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < W; i++) {
            q.clear();
            boolean flag = false;
            for (int j = 0; j < D; j++) {
                q.offer(copy[j][i]);
            }

            int prev = q.poll();
            int count = 1;

            while (!q.isEmpty()) {
                int next = q.poll();
                if (prev == next) {
                    count++;
                } else {
                    prev = next;
                    count = 1;
                }
                if (count >= K) {
                    flag = true;
                }
            }
            if(!flag) return false;
        }

        return true;
    }

    public static void init() {
        for (int i = 0; i < D; i++) {
            for (int j = 0; j < W; j++) {
                copy[i][j] = board[i][j];
            }
        }
    }
}

