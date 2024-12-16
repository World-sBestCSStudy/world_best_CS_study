//CodeTree_미지의공간탈출
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static int N, M, F;
    static int[][] board;
    static int[][] wall;
    static Node[] bug;
    static int[] d;
    static int[] v;
    static Node machine;
    static Node bridgeWall;
    static Node bridgeSpace;
    static int[] dx = {0, 0, 1, -1}, dy = {1, -1, 0, 0};
    static int[] dxE = {-1, -1, -1, 0, 1, 1, 1, 0}, dyE = {-1, 0, 1, 1, 1, 0, -1, -1};
    static int[][] bugTime;
    static int INF = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {
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

        wall = new int[M * 3][M * 3];
        for (int i = 0; i < M * 3; i++) Arrays.fill(wall[i], INF);
        int[][] loc = {{M, 2 * M}, {M, 0}, {2 * M, M}, {0, M}, {M, M}};
        for (int i = 0; i < 5; i++) {

            int[][] temp = new int[M][M];
            for (int x = 0; x < M; x++) {
                st = new StringTokenizer(br.readLine());
                for (int y = 0; y < M; y++) {
                    temp[x][y] = Integer.parseInt(st.nextToken());
                }
            }

            rotate(temp, i);

            fillWall(temp, loc[i][0], loc[i][1]);

        }


        bug = new Node[F];
        bugTime = new int[N][N];
        d = new int[F];
        v = new int[F];
        for (int i = 0; i < F; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int dir = Integer.parseInt(st.nextToken());
            int turn = Integer.parseInt(st.nextToken());
            bug[i] = new Node(x, y);
            d[i] = dir;
            v[i] = turn;
        }

        readyBug();

        bridgeSpace = findBridgeSpace();
        bridgeWall = findBridgeWall();

        int wallDist = wallMove();
        if (wallDist == INF) {
            System.out.println(-1);
            return;
        }
        int answer = spaceMove(wallDist);

        System.out.println(answer == INF ? -1 : answer);
    }

    public static int wallMove() {
        Queue<Node> q = new LinkedList<>();
        int[][] dist = new int[M * 3][M * 3];

        q.offer(new Node(machine.x, machine.y));
        for (int i = 0; i < M * 3; i++) Arrays.fill(dist[i], INF);
        dist[machine.x][machine.y] = 0;

        while (!q.isEmpty()) {
            Node node = q.poll();

            for (int i = 1; i < 8; i += 2) {
                int nx = node.x + dxE[i];
                int ny = node.y + dyE[i];

                if (nx < 0 || ny < 0 || nx >= M * 3 || ny >= M * 3 || wall[nx][ny] == 1) continue;

                if (wall[nx][ny] == INF) {
                    Node n1 = across(node.x, node.y, (i + 7) % 8);
                    Node n2 = across(node.x, node.y, (i + 1) % 8);

                    if (n1.x != -1 && n1.y != -1) {
                        nx = n1.x;
                        ny = n1.y;
                    } else if (n2.x != -1 && n2.y != -1) {
                        nx = n2.x;
                        ny = n2.y;
                    } else {
                        continue;
                    }
                }

                if (dist[nx][ny] > dist[node.x][node.y] + 1) {
                    dist[nx][ny] = dist[node.x][node.y] + 1;
                    q.offer(new Node(nx, ny));
                }

            }
        }

        return dist[bridgeWall.x][bridgeWall.y];
    }

    public static Node across(int x, int y, int d) {
        int nx = x;
        int ny = y;
        while (true) {
            nx += dxE[d];
            ny += dyE[d];

            if (nx < 0 || ny < 0 || nx >= M*3 || ny >= M*3 || wall[nx][ny] == 1) return new Node(-1, -1);
            if (wall[nx][ny] == 0) return new Node(nx, ny);
        }
    }

    public static int spaceMove(int startDist) {
        Queue<Node> q = new LinkedList<>();
        int[][] dist = new int[N][N];
        int result = INF;

        for (int i = 0; i < N; i++) Arrays.fill(dist[i], INF);
        q.offer(new Node(bridgeSpace.x, bridgeSpace.y));
        dist[bridgeSpace.x][bridgeSpace.y] = startDist + 1;

        while (!q.isEmpty()) {
            Node node = q.poll();

            if (board[node.x][node.y] == 4) {
                result = dist[node.x][node.y];
                break;
            }

            for (int i = 0; i < 4; i++) {
                int nx = node.x + dx[i];
                int ny = node.y + dy[i];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N || board[nx][ny] == 1 || board[nx][ny] == 3) continue;

                int nDist = dist[node.x][node.y] + 1;
                if (nDist >= bugTime[nx][ny]) continue;

                if (dist[nx][ny] > nDist) {
                    dist[nx][ny] = nDist;
                    q.offer(new Node(nx, ny));
                }
            }
        }

        return result;
    }

    public static void readyBug() {
        for (int i = 0; i < N; i++) Arrays.fill(bugTime[i], INF);
        for (int i = 0; i < F; i++) {
            bugSpread(i);
        }
    }

    public static void bugSpread(int id) {
        Queue<Node> q = new LinkedList<>();

        Node bNode = bug[id];
        q.offer(new Node(bNode.x, bNode.y));
        bugTime[bNode.x][bNode.y] = 0;
        int count = 1;

        while (!q.isEmpty()) {
            Node node = q.poll();

            int nx = node.x + dx[d[id]];
            int ny = node.y + dy[d[id]];

            if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;

            if (board[nx][ny] != 4 && board[nx][ny] != 1) {
                bugTime[nx][ny] = Math.min(bugTime[nx][ny], v[id] * count);
                count++;
                q.offer(new Node(nx, ny));
            }

        }

    }

    public static void fillWall(int[][] temp, int sx, int sy) {
        for (int i = sx; i < sx + M; i++) {
            for (int j = sy; j < sy + M; j++) {
                wall[i][j] = temp[i - sx][j - sy];
                if (wall[i][j] == 2) machine = new Node(i, j);
            }
        }

    }

    public static void rotate(int[][] arr, int dir) {
        Queue<Integer> q = new LinkedList<>();
        if (dir == 0) {
            for (int i = M - 1; i >= 0; i--) {
                for (int j = 0; j < M; j++) {
                    q.offer(arr[j][i]);
                }
            }
        }
        if (dir == 1) {
            for (int i = 0; i < M; i++) {
                for (int j = M - 1; j >= 0; j--) {
                    q.offer(arr[j][i]);
                }
            }
        }
        if (dir == 3) {
            for (int i = M - 1; i >= 0; i--) {
                for (int j = M - 1; j >= 0; j--) {
                    q.offer(arr[i][j]);
                }
            }
        }

        if (q.isEmpty()) return;
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < M; j++) {
                arr[i][j] = q.poll();
            }
        }
    }

    public static Node findBridgeSpace() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {

                if (board[i][j] != 0) continue;

                for (int x = 0; x < 4; x++) {
                    int nx = i + dx[x];
                    int ny = j + dy[x];
                    if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;

                    if (board[nx][ny] == 3) return new Node(i, j);
                }

            }
        }
        return new Node(-1, -1);
    }


    public static Node firstWallPoint(){
        for(int i =0; i<N; i++){
            for(int j =0; j<N; j++){
                if(board[i][j] == 3) return new Node(i,j);
            }
        }
        return new Node(-1,-1);
    }


    public static Node findBridgeWall() {

        Node sNode = firstWallPoint();

        int dir = 0;
        int nx = 0, ny = 0;
        for (int i = 0; i < 4; i++) {
            nx = bridgeSpace.x + dx[i];
            ny = bridgeSpace.y + dy[i];

            if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;

            if (board[nx][ny] == 3) {
                dir = i;
                break;
            }
        }
        
        if (dir == 0) {
            return new Node(M + nx -sNode.x, 0);
        } else if (dir == 1) {
            return new Node(M + nx - sNode.x, 3 * M - 1);
        } else if (dir == 2) {
            return new Node(0, M + ny - sNode.y);
        } else {
            return new Node(3 * M - 1, M + ny - sNode.y);
        }

    }

    static class Node {
        int x, y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }
}
