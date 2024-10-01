import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BOJ_2239_스도쿠 {
    static boolean flag;
    static int[][] board = new int[9][9];
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        for (int i = 0; i < 9; i++) {
            String str = br.readLine();
            for (int j = 0; j < 9; j++) {
                board[i][j] = str.charAt(j) - '0';
            }
        }

        simul(0);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sb.append(board[i][j]);
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }

    static void simul(int idx) {
        if (idx > 80) {
            flag = true;
            return; //모든 칸을 다 채웠다면
        }

        int y = idx / 9;
        int x = idx % 9;

        if (board[y][x] != 0) simul(idx + 1);
        else {
            boolean[] check = new boolean[10];

            //가로체크
            for (int i = 0; i < 9; i++) {
                check[board[y][i]] = true;
            }
            //세로체크
            for (int i = 0; i < 9; i++) {
                check[board[i][x]] = true;
            }
            //네모체크
            int startY = y / 3 * 3;
            int startX = x / 3 * 3;
            for (int i = startY; i < startY + 3; i++) {
                for (int j = startX; j < startX + 3; j++) {
                    check[board[i][j]] = true;
                }
            }

            for (int i = 1; i <= 9; i++) {
                if (!check[i]) {
                    board[y][x] = i;
                    simul(idx + 1);
                }

                if (flag) return;
            }
            board[y][x] = 0;
        }
    }
}
