//BOJ_11562_백양로 브레이크
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] dist = new int[n][n];
        int INF = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) Arrays.fill(dist[i], Integer.MAX_VALUE);
        for (int i = 0; i < n; i++) dist[i][i] = 0;

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken())-1;
            int y = Integer.parseInt(st.nextToken())-1;
            int d = Integer.parseInt(st.nextToken());

            dist[x][y] = 0;
            if (d == 1) dist[y][x] = 0;
            else dist[y][x] = 1;
        }

        for (int x = 0; x < n; x++) {

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if(dist[i][x]==INF || dist[x][j]==INF) continue;
                    if (dist[i][j] > dist[i][x]+dist[x][j]){
                        dist[i][j] = dist[i][x]+dist[x][j];
                    }
                }
            }
        }


        int k = Integer.parseInt(br.readLine());
        StringBuilder answer = new StringBuilder();

        for(int i=0; i<k; i++){
            st=new StringTokenizer(br.readLine());
            int x= Integer.parseInt(st.nextToken())-1;
            int y= Integer.parseInt(st.nextToken())-1;

            answer.append(dist[x][y]).append("\n");

        }
        System.out.println(answer);

    }

}