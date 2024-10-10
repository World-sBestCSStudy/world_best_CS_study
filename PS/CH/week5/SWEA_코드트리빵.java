//SWEA_코드트리빵

import java.io.*;
import java.util.*;

public class Main {
    static int N, M;

    //베이스캠프가 어디 있는지 표시
    static int[][] board;

    //가게의 정보를 표시
    static int[][] storeBoard;

    //유저가 어떤 베이스캠프를 선점했는지
    static int[][] baseCampBoard;

    //사람이 베이스캠프나 상점에 도달했을대 그 지역은 더이상 가지 못한다.
    static boolean[][] canGo;
    static int[] dx = {-1, 0, 0, 1}, dy = {0, -1, 1, 0};

    //i시간에 움직일수 있는 사람의 위치
    static Node[] peoples;

    //가게의 정보를 각기 다른 id로 표시
    static Node[] stores;

    //현재 움직일 수 있는 사람여부, 시간이 되지 않았거나 상점에 도달했을 경우 움직이지 않는다.
    static boolean[] isActive;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        board = new int[N][N];
        canGo = new boolean[N][N];
        storeBoard = new int[N][N];
        baseCampBoard = new int[N][N];

        peoples = new Node[M + 1];
        stores = new Node[M + 1];
        isActive = new boolean[M + 1];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int id = 1;
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            storeBoard[x][y] = id++;
            stores[i + 1] = new Node(x, y);
        }

        int t = 0;
        int count = 0;
        while (true) {
            t++;

            for (int i = 1; i <= M; i++) {
                if (!isActive[i]) continue;

                //사람을 이동시킨다. 만약 상점에 도달했으면 더이상 움직이 않아도 된다.
                moveStore(i);
                if (peoples[i].x == stores[i].x && peoples[i].y == stores[i].y) {
                    isActive[i] = false;
                    count++;
                    if (count == M) break;
                }

            }
            updateCanGo();


            if (t <= M) {
                peoples[t] = goBasecamp(t);
                baseCampBoard[peoples[t].x][peoples[t].y] = t;
                isActive[t] = true;
                updateCanGo();
            }

            if (count == M) break;

        }
        System.out.println(t);
    }

    public static void moveStore(int id) {
        Node people = peoples[id];
        Node store = stores[id];

        Queue<Node> q = new LinkedList<>();
        int[][] dist = new int[N][N];
        int[][] routeX = new int[N][N];
        int[][] routeY = new int[N][N];


        for (int i = 0; i < N; i++) Arrays.fill(dist[i], Integer.MAX_VALUE);
        q.offer(new Node(store.x, store.y));
        dist[store.x][store.y] = 0;
        boolean[][] v= new boolean[N][N];
        v[store.x][store.y]= true;

        while (!q.isEmpty()) {
            Node node = q.poll();

            for (int i = 0; i < 4; i++) {
                int nx = node.x + dx[i];
                int ny = node.y + dy[i];
                if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;

                if ((!canGo[nx][ny] || (nx == people.x && ny == people.y)) && dist[nx][ny] > dist[node.x][node.y] + 1) {
                    routeX[nx][ny] = node.x;
                    routeY[nx][ny] = node.y;
                    dist[nx][ny] = dist[node.x][node.y] + 1;
                    q.offer(new Node(nx, ny));
                }
            }
        }

        int nx = routeX[people.x][people.y];
        int ny = routeY[people.x][people.y];
        peoples[id].x = nx;
        peoples[id].y = ny;
    }

    public static Node goBasecamp(int id) {
        Node store = stores[id];
        int[][] dist=new int[N][N];
        for(int i=0; i<N; i++)Arrays.fill(dist[i] ,Integer.MAX_VALUE);
        Queue<Node> q = new LinkedList<>();
        q.offer(new Node(store.x, store.y,0));
        boolean[][] v = new boolean[N][N];
        v[store.x][store.y] = true;
        dist[store.x][store.y] = 0;
        while (!q.isEmpty()) {
            Node node = q.poll();
            dist[node.x][node.y] = Math.min(dist[node.x][node.y], node.cost);
            //행, 열 순으로 작은걸 먼저 선택해야한다. 델타로 우선순위를 두었으니 여기서는 체크하지 않아도 된다.

            for (int i = 0; i < 4; i++) {
                int nx = node.x + dx[i];
                int ny = node.y + dy[i];
                if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;

                if (!v[nx][ny] && !canGo[nx][ny]) {
                    v[nx][ny] = true;
                    q.offer(new Node(nx, ny, node.cost+1));
                }
            }
        }

        Node node = new Node(N,N,Integer.MAX_VALUE);
        for(int i =0; i<N; i++){
            for(int j =0; j<N; j++){
                if(board[i][j]==1&&dist[i][j]<node.cost){
                    node.x=i;
                    node.y=j;
                    node.cost=dist[i][j];
                }
            }
        }

        //가지 못하는 경우는 없다 했으니 null 반환
        return node;
    }

    public static void updateCanGo() {
        for (int i = 1; i < M + 1; i++) {
            Node node = peoples[i];
            if (node == null) continue;
            //canGo는 어떤 사람이 베이스캠프나 상점에 도달했을때 true로 변경하며 그 지역은 더이상 지나가지 못한다.
            //아 근데 도착한거랑 지나가는거를 따로 구별해야한다
            if (baseCampBoard[node.x][node.y] == i || storeBoard[node.x][node.y] == i) {
                canGo[node.x][node.y] = true;
            }
        }
    }

    static class Node {
        int x, y,cost;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Node(int x, int y, int cost) {
            this.x = x;
            this.y = y;
            this.cost = cost;
        }
    }
}