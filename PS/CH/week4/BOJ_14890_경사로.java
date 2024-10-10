//BOJ_14890_경사로

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N, L;
    static int[][] board;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;


        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());

        board = new int[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++)
                board[i][j] = Integer.parseInt(st.nextToken());
        }

        int count = 0;
        for (int i = 0; i < N; i++) {
            if(colCheck(i)) count++;
        }
        for (int i = 0; i < N; i++) {
            if(rowCheck(i)) count++;
        }
        System.out.println(count);

    }

    public static boolean colCheck(int i) {
        boolean[] v = new boolean[N];
        for (int j = 0; j < N - 1; j++) {
            int diff = board[i][j] - board[i][j + 1];
            if (Math.abs(diff) > 1) return false;

            if (diff == -1) {
                int l = j + 1 - L;
                if (l < 0) return false;

                for (int x = l; x <= j; x++) {
                    if (board[i][j] != board[i][x]) return false;
                    if (v[x]) return false;
                    v[x] = true;
                }

            } else if (diff == 1) {
                int l = j + L;
                if (l >= N) return false;
                for (int x = j + 1; x <= l; x++) {
                    if (board[i][j + 1] != board[i][x]) return false;
                    if (v[x]) return false;
                    v[x] = true;
                }
            }
        }
        return true;
    }

    public static boolean rowCheck(int j) {
        boolean[] v = new boolean[N];
        for (int i = 0; i < N - 1; i++) {
            int diff = board[i][j] - board[i+1][j];
            if (Math.abs(diff) > 1) return false;

            if (diff == -1) {
                int l = i + 1 - L;
                if (l < 0) return false;
                for (int x = l; x < i + 1; x++) {
                    if (board[i][j] != board[x][j]) return false;
                    if (v[x]) return false;
                    v[x] = true;
                }

            } else if (diff == 1) {

                int l = i + L;
                if (l >= N) return false;
                for (int x = i+1; x < l + 1; x++) {
                    if (board[i+1][j] != board[x][j]) return false;
                    if (v[x]) return false;
                    v[x] = true;
                }

            }
        }
        return true;
    }
}