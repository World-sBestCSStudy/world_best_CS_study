import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BOJ_9663_NQueen {
    static int N, ans;
    static int[] dy = {-1,-1,1,1};
    static int[] dx = {-1,1,-1,1};
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        boolean[][] chessBoard = new boolean[N][N];

        dfs(chessBoard, 0);
        System.out.println(ans);
    }

    static void dfs(boolean[][] chessBoard, int queenCnt) {
        if (queenCnt == N) {
            ans++;
            return;
        }
        for (int i = 0; i < N; i++) {
            if (isSafe(chessBoard, queenCnt, i)) {
                chessBoard[queenCnt][i] = true;
                dfs(chessBoard, queenCnt + 1);
                chessBoard[queenCnt][i] = false;
            }
        }
    }

    static boolean isSafe(boolean[][] chessBoard, int y, int x) {
        for (int i = 0; i < y; i++) {
            if (chessBoard[i][x]) return false;
        }

        for (int d = 0; d < 4; d++) {
            int ny = y;
            int nx = x;
            while(true) {
                ny += dy[d];
                nx += dx[d];
                if (ny < 0 || nx < 0 || ny >= N || nx >= N) break;
                if (chessBoard[ny][nx]) return false;
            }
        }
        return true;
    }
}
