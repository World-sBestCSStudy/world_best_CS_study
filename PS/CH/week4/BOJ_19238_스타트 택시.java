//BOJ_19238_스타트 택시

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static int N, M, F;
    static int[][] board;
    static int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};
    static Node taxi;

    static boolean[] passenger;
    static Node[][] point;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        F = Integer.parseInt(st.nextToken());

        board = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }


        st = new StringTokenizer(br.readLine());
        taxi = new Node(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1);

        point = new Node[M][2];
        passenger = new boolean[M];
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            Node start = new Node(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1);
            Node end = new Node(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1);

            point[i][0] = start;
            point[i][1] = end;
        }


        //목적지가 같을떄?
        Arrays.sort(point, new Comparator<Node[]>() {
            @Override
            public int compare(Node[] o1, Node[] o2) {
                if(o1[0].x==o2[0].x){
                    return o1[0].y-o2[0].y;
                }
                return o1[0].x-o2[0].x;
            }
        });

        while (true) {
            int[] minPassenger = findMinPassenger();

            int idx = minPassenger[0];
            int fuel = minPassenger[1];

            if (idx == -1 || F - fuel < 0) break;

            F-=fuel;

            Node start = point[idx][0];
            Node end = point[idx][1];

            fuel = drive(start, end);


            if(fuel==-1 || F<fuel) break;

            taxi.x = end.x;
            taxi.y = end.y;

            passenger[idx] = true;

            F+=fuel;

        }

        for(int i =0; i<M; i++){
            if(!passenger[i]){
                F=-1;
                break;
            }
        }
        System.out.println(F);


    }

    public static int drive(Node start, Node end) {
        PriorityQueue<Node> q = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.cost, o2.cost));
        int[][] dist = new int[N][N];

        for (int i = 0; i < N; i++) Arrays.fill(dist[i], Integer.MAX_VALUE);
        dist[start.x][start.y] = 0;
        q.offer(new Node(start.x, start.y, 0));

        while (!q.isEmpty()) {
            Node node = q.poll();

            for (int i = 0; i < 4; i++) {
                int nx = node.x + dx[i];
                int ny = node.y + dy[i];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N || board[nx][ny] == 1) continue;

                if (dist[nx][ny] > dist[node.x][node.y] + 1) {
                    dist[nx][ny] = dist[node.x][node.y] + 1;
                    q.offer(new Node(nx, ny, dist[nx][ny]));
                }
            }
        }

        return dist[end.x][end.y] == Integer.MAX_VALUE ? -1 : dist[end.x][end.y];

    }


    public static int[] findMinPassenger() {
        PriorityQueue<Node> q = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.cost, o2.cost));
        int[][] dist = new int[N][N];

        for (int i = 0; i < N; i++) Arrays.fill(dist[i], Integer.MAX_VALUE);
        dist[taxi.x][taxi.y] = 0;
        q.offer(new Node(taxi.x, taxi.y, 0));

        while (!q.isEmpty()) {
            Node node = q.poll();

            for (int i = 0; i < 4; i++) {
                int nx = node.x + dx[i];
                int ny = node.y + dy[i];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N || board[nx][ny] == 1) continue;

                if (dist[nx][ny] > dist[node.x][node.y] + 1) {
                    dist[nx][ny] = dist[node.x][node.y] + 1;
                    q.offer(new Node(nx, ny, dist[nx][ny]));
                }
            }
        }

        int nowPassenger = -1;
        int minDist = Integer.MAX_VALUE;
        for (int i = 0; i < M; i++) {
            if (passenger[i]) continue;
            Node node = point[i][0];
            if (minDist > dist[node.x][node.y]) {
                minDist = dist[node.x][node.y];
                nowPassenger = i;
            }
        }

        if (minDist > F) nowPassenger = -1;


        return new int[]{nowPassenger, minDist};
    }

    static class Node {
        int x, y, cost;

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


