// BOJ_2206_벽부수고이동하기

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int n, m;
    static char[][] board;

    static int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;


        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        board=new char[n][m];
        for (int i = 0; i < n; i++) {
            board[i] = br.readLine().toCharArray();
        }

        int answer = bfs(0,0);
        System.out.println(answer);


    }

    public static int bfs(int x, int y) {
        Queue<Node> q = new LinkedList<>();
        q.offer(new Node(x, y,1,false));

        //벽을 뚫고 지나갈 경우, 안뚫고 지나갈경우
        boolean[][][] v= new boolean[2][n][m];
        v[0][0][0] = true;

        while (!q.isEmpty()) {
            Node node = q.poll();
            if(node.x==n-1&&node.y==m-1) return node.cost;
            for (int i = 0; i < 4; i++) {
                int nx = node.x + dx[i];
                int ny = node.y + dy[i];

                if (nx < 0 || ny < 0 || nx >= n || ny >= m) continue;

                //벽을 뚫고 오는 경우
                if(node.crush){
                    if(!v[1][nx][ny] && board[nx][ny] == '0'){
                        v[1][nx][ny]=true;
                        q.offer(new Node(nx,ny,node.cost+1,node.crush));
                    }
                }else {
                    if(!v[0][nx][ny] && board[nx][ny] == '0'){
                        v[0][nx][ny]=true;
                        q.offer(new Node(nx,ny,node.cost+1, false));
                    }

                    if(!v[0][nx][ny] && board[nx][ny] == '1'){
                        v[1][nx][ny]=true;
                        q.offer(new Node(nx,ny,node.cost+1,true));
                    }
                }

            }
        }
        return -1;
    }

    static class Node {
        int x, y,cost;
        boolean crush;

        public Node(int x, int y, int cost, boolean crush) {
            this.x = x;
            this.y = y;
            this.cost=cost;
            this.crush=crush;
        }
    }

}
