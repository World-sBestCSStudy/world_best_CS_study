import java.io.*;
import java.util.*;

public class BOJ_3109_빵집 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int R, C, ans;
    static char[][] map;
    static int[] dy = {-1, 0, 1};   // 우측 상,중,하단 방향

    public static void main(String[] args) throws Exception{
        st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        // 입력 (0-빈칸, 1-건물)
        map = new char[R][C];
        for (int r = 0; r < R; r++){
            map[r] = br.readLine().toCharArray();
        }

        // 탐색
        for (int r = 0; r < R; r++){
             if (connect(r, 0)) ans++;
        }


        System.out.println(ans);
    }

    private static boolean connect(int y, int x){
        int nx = x + 1;
        if (nx == C-1) return true;

        for (int d = 0; d < 3; d++){
            int ny = y + dy[d];

            if (ny < 0 || ny >= R || map[ny][nx] == 'x')
                continue;

            // 어차피 파이프는 겹치거나 교차할 수 없기 때문에, 오른쪽 위에서부터 보면서 체크할 때는 성공이든 실패든 다음 칸을 볼 땐 방문처리 해야 한다
            map[ny][nx] = 'x';

            if (connect(ny, nx))
                return true;
        }

        return false;
    }

}
