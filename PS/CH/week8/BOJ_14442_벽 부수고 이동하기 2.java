//BOJ_14442_벽 부수고 이동하기 2
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N, M, K;
    static char[][] board;
    static int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        board = new char[N][M];
        for (int i = 0; i < N; i++) {
            board[i] = br.readLine().toCharArray();
        }

        int answer = bfs();
        System.out.println(answer);


    }

    public static int bfs() {
        Queue<Node> q =new LinkedList<>();
        boolean[][][] v= new boolean[K+1][N][M];

        q.offer(new Node(0,0,1,0));
        v[0][0][0] = true;

        int min = Integer.MAX_VALUE;

        while(!q.isEmpty()){
            Node node = q.poll();

            if(node.x==N-1 && node.y==M-1){
                min = Math.min(min,node.cost);
            }

            for(int i =0; i<4; i++){
                int nx = node.x+dx[i];
                int ny = node.y+dy[i];

                if(nx<0||ny<0||nx>=N||ny>=M) continue;

                if(board[nx][ny] == '0' && !v[node.crush][nx][ny]){
                    v[node.crush][nx][ny] = true;
                    q.offer(new Node(nx,ny,node.cost+1, node.crush));
                }

                if(board[nx][ny] == '1'){
                    if(node.crush>=K) continue;
                    if(!v[node.crush+1][nx][ny]){
                        v[node.crush+1][nx][ny]=true;
                        q.offer(new Node(nx,ny,node.cost+1,node.crush+1));
                    }
                }
            }
        }

        return min==Integer.MAX_VALUE?-1:min;

    }

    static class Node {
        int x, y, cost, crush;

        public Node(int x, int y, int cost, int crush) {
            this.x = x;
            this.y = y;
            this.cost = cost;
            this.crush = crush;
        }
    }
}
