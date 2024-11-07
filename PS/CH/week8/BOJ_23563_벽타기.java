//BOJ_23563_벽타기

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N, M;
    static char[][] board;
    static int[] dx= {-1,0,1,0}, dy={0,1,0,-1};

    static int[][] cost;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st=new StringTokenizer(br.readLine());
        N=Integer.parseInt(st.nextToken());
        M=Integer.parseInt(st.nextToken());
        board=new char[N][M];
        cost=new int[N][M];
        for(int i =0; i<N; i++) Arrays.fill(cost[i],Integer.MAX_VALUE);

        Node start = null;
        Node end = null;
        for(int i=0;i<N; i++){
            board[i] = br.readLine().toCharArray();
            for(int j =0; j<M; j++){
                if(board[i][j] == 'S'){
                    start = new Node(i,j,0);
                }
                if(board[i][j] == 'E'){
                    end = new Node(i,j,0);
                }
            }
        }

        int answer = bfs(start.x,start.y,end.x,end.y);
        System.out.println(answer);

    }
    public static int bfs(int sx, int sy, int ex,int ey){
//        Queue<Node> q= new LinkedList<>();
//        PriorityQueue<Node> q= new PriorityQueue<>((o1,o2)->Integer.compare(o1.cost,o2.cost));

        Deque<Node> q = new ArrayDeque<>();

        int[][] dist =new int[N][M];
        boolean[][] v= new boolean[N][M];

        for(int i =0; i<N; i++) Arrays.fill(dist[i],Integer.MAX_VALUE);

        q.offer(new Node(sx,sy,0));
        dist[sx][sy]=0;


        while(!q.isEmpty()){
            Node node =q.pollFirst();


            if(node.x == ex && node.y==ey) return dist[ex][ey];

            if(v[node.x][node.y]) continue;;
            v[node.x][node.y] = true;

            boolean xFlag =isNearWall(node.x, node.y);
            for(int i =0; i<4; i++){
                int nx = node.x+dx[i];
                int ny = node.y+dy[i];

                if(nx<0||ny<0||nx>=N|ny>=M||board[nx][ny]=='#') continue;

                boolean nxFlag = isNearWall(nx,ny);

                if(xFlag && nxFlag){
                    if(dist[nx][ny] > dist[node.x][node.y]){
                        dist[nx][ny] = dist[node.x][node.y];
                        q.addFirst(new Node(nx,ny,dist[nx][ny]));
                    }
                }else{
                    if(dist[nx][ny] > dist[node.x][node.y]+1){
                        dist[nx][ny] = dist[node.x][node.y]+1;
                        q.addLast(new Node(nx,ny,dist[nx][ny]+1));
                    }
                }


            }
        }

        return dist[ex][ey];
    }

    public static boolean isNearWall(int x, int y){
        for(int i =0; i<4; i++){
            int nx = x+dx[i];
            int ny = y+dy[i];

            if(nx<0||ny<0||nx>=N||ny>=M) continue;

            if(board[nx][ny]=='#') return true;
        }
        return false;

    }
    static class Node{
        int x, y ,cost;

        public Node(int x, int y, int cost) {
            this.x = x;
            this.y = y;
            this.cost = cost;
        }
    }
}
