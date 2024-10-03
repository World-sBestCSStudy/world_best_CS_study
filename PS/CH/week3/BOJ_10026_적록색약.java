// BOJ_10026_적록색약

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int n;
    static int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        char[][] board1 = new char[n][n];
        char[][] board2 = new char[n][n];

        for (int i = 0; i < n; i++) {
            char[] temp = br.readLine().toCharArray();
            for (int j = 0; j < n; j++) {
                board1[i][j] = temp[j];
                board2[i][j]=temp[j];
                if (board2[i][j] == 'G') board2[i][j] = 'R';
            }
        }


        boolean[][] v1= new boolean[n][n];
        int count1=0;
        for(int i =0; i<n; i++){
            for(int j =0; j<n; j++){
                if(!v1[i][j]){
                    count1++;
                    bfs(v1, board1, i, j);
                }
            }
        }

        boolean[][] v2= new boolean[n][n];
        int count2=0;
        for(int i =0; i<n; i++){
            for(int j =0; j<n; j++){
                if(!v2[i][j]){
                    count2++;
                    bfs(v2, board2, i, j);
                }
            }
        }

        String answer = count1+" "+count2;
        System.out.println(answer);

    }
    public static void bfs(boolean[][] v, char[][] board,int x, int y){
        Queue<Node> q= new LinkedList<>();
        q.offer(new Node(x, y));

        while(!q.isEmpty()){
            Node node =q.poll();
            for(int i =0; i<4; i++){
                int nx = node.x+dx[i];
                int ny = node.y+dy[i];

                if(nx<0||ny<0||nx>=n||ny>=n) continue;

                if(!v[nx][ny] && board[x][y] == board[nx][ny]){
                    v[nx][ny] = true;
                    q.offer(new Node(nx,ny));
                }
            }
        }
    }
    static class Node{
        int x, y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}
