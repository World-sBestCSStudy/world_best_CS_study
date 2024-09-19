package algorithm2024.sep.day18;

import java.io.*;
import java.util.*;

public class BOJ_7682_틱택토 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int[] dy = {-1, 0, 1, 1};
    static int[] dx = {1, 1, 1, 0};

    static boolean isValid(int y, int x) {
        return y >= 0 && y < 3 && x >= 0 && x < 3;
    }

    public static void main(String[] args) throws Exception {
        while (true) {
//            입력 -> 종료 조건 탐지 혹은 틱택토 게임 판 생성
            String s = br.readLine();
            if (s.equals("end")) break;
            char[][] game = new char[3][3];
            char[] stoChar = s.toCharArray();
            int cntO = 0;
            int cntX = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    game[i][j] = stoChar[i * 3 + j];
                    if (game[i][j] == 'X') cntX++;
                    if (game[i][j] == 'O') cntO++;
                }
            }

            boolean isValid = false;
            if (cntX - cntO == 1 || cntX == cntO) {
                boolean winX = false;
                boolean winO = false;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (game[i][j] != '.') {
                            for (int d = 0; d < 4; d++) {
                                if (dfs(game, i, j, d, 1)) {
                                    if (game[i][j] == 'O') {
                                        winO = true;
                                    } else {
                                        winX = true;
                                    }
                                }
                            }
                        }
                    }
                }
                if(cntX-cntO==1&&cntX+cntO==9){
                    if(winX&&!winO||(!winX&&!winO)) isValid= true;
                }else if (cntX > cntO) {
                    if (winX && !winO) isValid = true;
                } else {
                    if (winO && !winX) isValid = true;
                }


            }
            if (isValid) {
                sb.append("valid");
            } else {
                sb.append("invalid");
            }
            sb.append("\n");
        }
        System.out.print(sb);

    }

    static boolean dfs(char[][] game, int y, int x, int d, int cnt) {
        if (cnt == 3) return true;

        int ny = y + dy[d];
        int nx = x + dx[d];
        if (isValid(ny, nx) && game[ny][nx] == game[y][x]) {
            return dfs(game, ny, nx, d, cnt + 1);
        }

        return false;
    }
}
/*
OXX
XOX
XOO
 */