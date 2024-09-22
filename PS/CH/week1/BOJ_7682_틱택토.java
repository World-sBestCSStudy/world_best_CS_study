//BOJ_7682_틱택토

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    static int[] dx = {0, 1, 1, -1}, dy = {1, 1, 0, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringBuilder answer = new StringBuilder();
        while (true) {
            String input = br.readLine();
            if (input.equals("end")) break;

            char[][] board = new char[3][3];
            int x = 0, o = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    board[i][j] = input.charAt((i * 3) + j);
                    if (board[i][j] == 'X') x++;
                    else if (board[i][j] == 'O') o++;
                }
            }


//            for(int i =0; i<3; i++){
//                    System.out.println(Arrays.toString(board[i]));
//            }
//            System.out.println();

            if (!(x == o || x == o + 1)) {
                answer.append("invalid").append("\n");
                continue;
            }

            boolean xWins = false, oWins = false;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '.') continue;
                    if (board[i][j] == 'X' && find(board, i, j)) xWins = true;
                    if (board[i][j] == 'O' && find(board, i, j)) oWins = true;
                }
            }

            // 둘 다 승리할 수 없다.
            if (xWins && oWins) {
                answer.append("invalid").append("\n");
                continue;
            }

            if (xWins && x != o + 1) {
                answer.append("invalid").append("\n");
                continue;
            }

            if (oWins && x != o) {
                answer.append("invalid").append("\n");
                continue;
            }


            if (!xWins && !oWins && !(x == 5 && o == 4)) {
                answer.append("invalid").append("\n");
                continue;
            }
            answer.append("valid").append("\n");
        }
        System.out.println(answer.toString());
    }

    public static boolean find(char[][] board, int x, int y) {
        for (int i = 0; i < 4; i++) {
            int nx = x;
            int ny = y;
            int count = 1;
            while (true) {
                nx += dx[i];
                ny += dy[i];

                if (nx < 0 || ny < 0 || nx >= 3 || ny >= 3) break;
                if (board[x][y] == board[nx][ny]) count++;
                else break;
            }

            if (count == 3) return true;

        }
        return false;
    }

}

//
