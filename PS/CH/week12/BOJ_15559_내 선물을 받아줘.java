//BOJ_15559_내 선물을 받아줘

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N, M;
    static char[][] board;
    static int[][] numbering;
    static int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());


        board = new char[N][M];
        numbering = new int[N][M];


        for (int i = 0; i < N; i++) {
            board[i] = br.readLine().toCharArray();
        }


        boolean[][] v= new boolean[N][M];
        int number =1;
        int answer = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if(!v[i][j]){
                    v[i][j] = true;
                    numbering[i][j] = number;
                    if(!dfs(v,i,j,number)) answer++;
                    number++;
                }
            }
        }
        System.out.println(answer);
    }

    public static boolean dfs(boolean[][] v, int x, int y,int number) {
        int dir = 0;
        if (board[x][y] == 'N') dir = 0;
        else if (board[x][y] == 'E') dir = 1;
        else if (board[x][y] == 'S') dir = 2;
        else if (board[x][y] == 'W') dir = 3;

        int nx = x + dx[dir];
        int ny = y + dy[dir];


        if(!v[nx][ny]){
            v[nx][ny] = true;
            numbering[nx][ny] =number;
            return dfs(v,nx,ny,number);
        }else{
            if(numbering[nx][ny] != number) return true;
        }
        return false;

    }
}

