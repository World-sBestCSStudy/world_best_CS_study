// CT. 고대 문명 유적 탐사

import java.io.*;
import java.util.*;

public class CT_고대문명유적탐사 {
    static int K, M, max;
    static int[][] origin = new int[5][5], map = new int[5][5];
    static int[] dry = {-1,-1,-1,0,1,1,1,0}, drx = {-1,0,1,1,1,0,-1,-1};    // 회전에 사용하는 배열
    static int[] dy = {-1,1,0,0}, dx = {0,0,-1,1};  // bfs에 사용하는 배열
    static Queue<Integer> wall = new ArrayDeque<>();

    static class Node{
        int y, x;
        public Node(int y, int x){
            this.y = y;
            this.x = x;
        }
    }

    static class Area {
        int y, x, profit, angle;
        public Area(int y, int x, int profit, int angle){
            this.y = y;
            this.x = x;
            this.profit = profit;
            this.angle = angle;
        }
    }

    // 최대 이익을 주는 -> 회전 각 작은 -> 중심 열 작은 -> 행 작은 ... 3x3 회전구간 선택
    static PriorityQueue<Area> areas = new PriorityQueue<>(
            new Comparator<Area>() {
                @Override
                public int compare(Area o1, Area o2) {
                    if(o1.profit != o2.profit) return o2.profit-o1.profit;
                    if(o1.angle != o2.angle) return o1.angle-o2.angle;
                    if(o1.x!=o2.x) return o1.x-o2.x;
                    if(o1.y!=o2.y) return o1.y-o2.y;
                    return 0;
                }
            }
    );

    // 유물이 되어 사라진 칸들 ... 열은 작고 -> 행은 큰 순서로 ... 다시 채워야 함
    static PriorityQueue<Node> removes = new PriorityQueue<>((n1, n2) ->
            n1.x == n2.x ? n2.y - n1.y : n1.x - n2.x);

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        K = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        for (int i = 0; i < 5; i++){
            st = new StringTokenizer(br.readLine().trim());
            for (int j = 0; j < 5; j++){
                origin[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(br.readLine().trim());
        for (int i = 0; i < M; i++){
            wall.offer(Integer.parseInt(st.nextToken()));
        }

        for (int k = 0; k < K; k++){
            copyArray(origin, map);     // map을 이용해서 돌릴 구역 찾기
            areas.clear();
            selectArea();

            if (max == 0)
                break;
            else {
                Area maxArea = areas.poll();
//                System.out.println(maxArea.y+","+maxArea.x+"를 "+maxArea.angle+" 회전 후 연쇄 탐색 시작");
                // 이후 연쇄 탐사에는 origin 사용해서 유물 캐기
                for (int i = 90; i <= maxArea.angle; i += 90) {
                    spin(origin, maxArea.y, maxArea.x);
                }

                int sum = 0;
                max = 0;
                while ((sum = searchAndRemove(origin)) != 0){
                    max += sum;
                    while (!removes.isEmpty()) {
                        Node cur = removes.poll();
                        origin[cur.y][cur.x] = wall.poll();
                    }
                }

                System.out.print(max+" ");
            }
        }

    }

    static void copyArray(int[][] from, int[][] to){
        for (int i = 0; i < from.length; i++){
            for (int j = 0; j < from[i].length; j++){
                to[i][j] = from[i][j];
            }
        }
    }

    static void selectArea(){
        // 중심
        max = -1;
        for (int i = 1; i <= 3; i++){
            for (int j = 1; j <= 3; j++){
                for (int a = 90; a < 360; a += 90){
                    spin(map, i, j);
                    int profit = search();
                    if (profit >= max){
                        areas.offer(new Area(i, j, profit, a));
                        max = profit;
                    }
                }
                spin(map, i, j);
            }
        }
    }

    static void spin(int[][] map, int y, int x){    // 시계방향 90도 회전
        int[] temp = new int[8];
        for (int i = 0; i < 8; i++){
            temp[i] = map[y+dry[i]][x+drx[i]];
        }
        int idx = 6;
        for (int i = 0; i < 8; i++){
            map[y+dry[i]][x+drx[i]] = temp[(idx+i) % 8];
        }
    }

    static int search(){
        int sum = 0;
        boolean[][] visit = new boolean[5][5];
        Queue<Node> q = new ArrayDeque<>();

        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                if (visit[i][j]) continue;

                int cnt = 1;
                visit[i][j] = true;
                q.offer(new Node(i,j));

                while (!q.isEmpty()){
                    Node cur = q.poll();
                    for (int d = 0; d < 4; d++){
                        int ny = cur.y + dy[d];
                        int nx = cur.x + dx[d];

                        if (ny >= 0 && ny < 5 && nx >= 0 && nx < 5 && !visit[ny][nx] && map[cur.y][cur.x] == map[ny][nx]){
                            cnt ++;
                            visit[ny][nx] = true;
                            q.offer(new Node(ny, nx));
                        }
                    }
                }

                sum += cnt >= 3 ? cnt : 0;
            }
        }

        return sum;
    }

    static int searchAndRemove(int[][] map){
        boolean[][] visit = new boolean[5][5];
        Queue<Node> q = new ArrayDeque<>();
        Queue<Node> nodes = new ArrayDeque<>();

        int getProfit = 0;
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 5; i++) {
                if (visit[i][j]) continue;

                int cnt = 0;
                nodes.clear();

                visit[i][j] = true;
                q.offer(new Node(i, j));
                nodes.offer(new Node(i, j));

                while (!q.isEmpty()) {
                    Node cur = q.poll();
                    cnt++;
                    for (int d = 0; d < 4; d++) {
                        int ny = cur.y + dy[d];
                        int nx = cur.x + dx[d];

                        if (ny >= 0 && ny < 5 && nx >= 0 && nx < 5 && !visit[ny][nx] && map[cur.y][cur.x] == map[ny][nx]) {
                            visit[ny][nx] = true;
                            q.offer(new Node(ny, nx));
                            nodes.offer(new Node(ny, nx));
                        }
                    }
                }

                if (cnt >= 3) {
                    for (Node node : nodes){
                        removes.offer(node);
                    }
                    getProfit += cnt;
                }
            }
        }

        return getProfit;
    }
}
