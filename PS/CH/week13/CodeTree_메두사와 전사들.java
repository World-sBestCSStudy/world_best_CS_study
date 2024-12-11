//CodeTree_메두사와 전사들
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int N, M;
    static Node[] warrior;
    static Node monster, target;
    static int[][] board, route;
    static int[][] warriorBoard;
    static boolean[] isDead;
    static boolean[] isStone;
    static int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};
    static int[] p1 = {0, 2, 3, 1}, p2 = {3, 1, 0, 2};
    static int[] dxM = {-1, -1, -1, 0, 1, 1, 1, 0}, dyM = {-1, 0, 1, 1, 1, 0, -1, -1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        monster = new Node(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        target = new Node(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));

        warrior = new Node[M];
        warriorBoard = new int[N][N];
        isDead = new boolean[M];
        isStone = new boolean[M];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < M; i++) {
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            warrior[i] = new Node(x, y);

            warriorBoard[x][y]++;
        }

        board = new int[N][N];
        route = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }


        monsterRoute();

        if (route[monster.x][monster.y] == Integer.MAX_VALUE) {
            System.out.println(-1);
            return;
        }


        StringBuilder answer = new StringBuilder();
        while (true) {
            Node node = monsterNextPoint();
            monster.x = node.x;
            monster.y = node.y;

            //메두사가 이동한 칸에 전사가 있으면 사망
            warriorTurn();

            if (monster.x == target.x && monster.y == target.y){
                answer.append("0");
                break;
            }

            boolean[][] v = monsterTurn();
            int stoneCount = getCount(v);
            int lenSum =moveWarrior(v);

            int deadCount = warriorTurn();

            dispel();
            answer.append(lenSum).append(" ").append(stoneCount).append(" ").append(deadCount).append("\n");

        }
        System.out.println(answer);


    }

    //===================메두사 함수 START===========================//

    //메두사 함수 1. 가장 많이 돌로 만들수 있는 경우를 반환받아서 전사를 돌로 바꾸기
    public static boolean[][] monsterTurn() {
        int dir = tryWatchWarrior();
        boolean[][] v = watchWarrior(dir);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (v[i][j] && warriorBoard[i][j] > 0){
                    changStone(i, j);
                }
            }
        }
        return v;
    }


    //메두사 함수 2. 상,하,좌,우를 보고 가장 많이 돌로 만들수 있는 경우를 찾기 위해 시도해보기
    public static int tryWatchWarrior() {
        int[] count = new int[4];
        int idx = 0;
        for (int i = 1; i < 8; i += 2) {
            count[idx++] = getCount(watchWarrior(i));
        }

        int max = 0;
        int maxDir = 1;

        //상하좌우 우선순위
        for (int i = 0; i < 4; i++) {
            if (max < count[p1[i]]) {
                max = count[p1[i]];
                if (p1[i] == 0) {
                    maxDir = 1;
                } else if (p1[i] == 1) {
                    maxDir = 3;
                } else if (p1[i] == 2) {
                    maxDir = 5;
                } else {
                    maxDir = 7;
                }
            }
        }

        return maxDir;
    }

    //메두사 함수 3. 실제 BFS, 하나의 방향으로 시작해서 전사를 돌로 만들수 있는 수를 반환
    public static boolean[][] watchWarrior(int dir) {
        Queue<Node> q = new LinkedList<>();
        Queue<Node> shadow = new LinkedList<>();


        boolean[][] v = new boolean[N][N];

        q.offer(new Node(monster.x, monster.y));

        while (!q.isEmpty()) {
            Node node = q.poll();

            for (int i = 0; i < 3; i++) {
                int nx = node.x + dxM[(dir - 1 + i) % 8];
                int ny = node.y + dyM[(dir - 1 + i) % 8];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N || v[nx][ny]) continue;

                v[nx][ny] = true;
                q.offer(new Node(nx, ny));

                if (warriorBoard[nx][ny] > 0) {
                    if (dir == 1) {
                        if (monster.y > ny) {
                            shadow.offer(new Node(nx, ny, 0, 1));
                        } else if (monster.y < ny) {
                            shadow.offer(new Node(nx, ny, 1, 2));
                        } else {
                            shadow.offer(new Node(nx, ny, 1, 1));
                        }
                    } else if (dir == 3) {
                        if (monster.x > nx) {
                            shadow.offer(new Node(nx, ny, 2, 3));
                        } else if (monster.x < nx) {
                            shadow.offer(new Node(nx, ny, 3, 4));
                        } else {
                            shadow.offer(new Node(nx, ny, 3, 3));
                        }
                    } else if (dir == 5) {
                        if (monster.y < ny) {
                            shadow.offer(new Node(nx, ny, 4, 5));
                        } else if (monster.y > ny) {
                            shadow.offer(new Node(nx, ny, 5, 6));
                        } else {
                            shadow.offer(new Node(nx, ny, 5, 5));
                        }
                    } else if (dir == 7) {
                        if (monster.x < nx) {
                            shadow.offer(new Node(nx, ny, 6, 7));
                        } else if (monster.x > nx) {
                            shadow.offer(new Node(nx, ny, 7, 8));
                        } else {
                            shadow.offer(new Node(nx, ny, 7, 7));
                        }
                    }

                }


            }

        }

        while (!shadow.isEmpty()) {
            Node node = shadow.poll();
            for (int i = node.d1; i <= node.d2; i++) {
                int nx = node.x + dxM[i % 8];
                int ny = node.y + dyM[i % 8];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N || !v[nx][ny]) continue;

                v[nx][ny] = false;
                shadow.offer(new Node(nx, ny, node.d1, node.d2));

            }
        }

        return v;
    }

    //메두사 함수 4. 목적지를 시작으로 메두사가 있는 곳까지 최단거리를 이차원배열에 표시
    public static void monsterRoute() {
        Queue<Node> q = new LinkedList<>();
        boolean[][] v = new boolean[N][N];

        q.offer(new Node(target.x, target.y));
        v[target.x][target.y] = true;
        for (int i = 0; i < N; i++) Arrays.fill(route[i], Integer.MAX_VALUE);
        route[target.x][target.y] = 0;

        while (!q.isEmpty()) {
            Node node = q.poll();
            if (node.x == monster.x && node.y == monster.y) break;
            for (int i = 0; i < 4; i++) {
                int nx = node.x + dx[i];
                int ny = node.y + dy[i];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;

                if (!v[nx][ny] && board[nx][ny] == 0) {
                    v[nx][ny] = true;
                    route[nx][ny] = route[node.x][node.y] + 1;
                    q.offer(new Node(nx, ny));
                }
            }
        }

    }

    //메두사 함수 5. 현재 메두사 위치에서 작아지는 쪽으로, 다음 이동하는 좌표 반환
    public static Node monsterNextPoint() {
        for (int i = 0; i < 4; i++) {
            int nx = monster.x + dx[p1[i]];
            int ny = monster.y + dy[p1[i]];

            if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;

            if (board[nx][ny] == 0 && route[monster.x][monster.y] > route[nx][ny]) {
                return new Node(nx, ny);
            }
        }
        return new Node(-1, -1);
    }

    //메두사 함수 6. 가장 많이 돌로 바꿀수 있는 경우를 구해서, 전사가 돌로 변환됬다는걸 표시
    public static void changStone(int x, int y) {
        for (int i = 0; i < M; i++) {
            if (isDead[i] || isStone[i]) continue;
            if (warrior[i].x == x && warrior[i].y == y) isStone[i] = true;
        }
    }


    //===================메두사 함수 END===========================//


    //===================전사 함수 END===========================//
    //전사 함수 1. 이동한 전사의 위치가 메두사랑 동일한지 보고, 죽었다는걸 표시
    public static int warriorTurn(){
        int count =0;
        for(int i =0; i<M; i++){
            if(isDead[i] || isStone[i]) continue;

            if(monster.x == warrior[i].x && monster.y==warrior[i].y){
                isDead[i] = true;
                count++;
                warriorBoard[warrior[i].x][warrior[i].y]--;
            }
        }
        return count;
    }

    //전사 함수 2. 우선순위에 따라 전사를 두번 이동시킨다. 첫번째에 이동시켜야 두번째도 이동시킬수 있다.
    public static int moveWarrior(boolean[][] v) {
        int len = 0;
        for (int id = 0; id < M; id++) {
            if (isDead[id] || isStone[id]) continue;

            Node node = warrior[id];

            int minDist = getDist(node.x, node.y, monster.x, monster.y);
            int dir = -1;

            for (int i = 0; i < 4; i++) {
                int nx = node.x + dx[p1[i]];
                int ny = node.y + dy[p1[i]];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;

                int dist = getDist(nx, ny, monster.x, monster.y);
                if (!v[nx][ny] && minDist > dist) {
                    minDist = dist;
                    dir = p1[i];
                }
            }

            if (dir == -1) continue;

            warriorBoard[node.x][node.y] --;
            node.x += dx[dir];
            node.y += dy[dir];
            warriorBoard[node.x][node.y] ++;
            len++;

            minDist = getDist(node.x, node.y, monster.x, monster.y);
            dir = -1;

            for (int i = 0; i < 4; i++) {
                int nx = node.x + dx[p2[i]];
                int ny = node.y + dy[p2[i]];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;

                int dist = getDist(nx, ny, monster.x, monster.y);
                if (!v[nx][ny] && minDist > dist) {
                    minDist = dist;
                    dir = p2[i];
                }
            }

            if (dir == -1) continue;

            warriorBoard[node.x][node.y] --;
            node.x += dx[dir];
            node.y += dy[dir];
            warriorBoard[node.x][node.y] ++;
            len++;

        }

        return len;

    }

    //전사 함수 3. 돌이 된 전사는 해당 턴이 끝나면 다시 움직일 수 있다.
    public static void dispel() {
        Arrays.fill(isStone, false);
    }

    //전사 함수 4. 메두사가 돌로 만들수 있는 전사의 수, v배열은 메두사의 시선범위를 boolean으로 표시한 배열
    public static int getCount(boolean[][] v) {
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (v[i][j]) count += warriorBoard[i][j];
            }
        }
        return count;
    }

    //전사 함수 5. 전사가 이동할때 맨해튼 거리가 줄어드는 방향으로 이동할수 있다.
    public static int getDist(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    //===================전사 함수 END===========================//

    //x,y를 나타내기 위한 클래스
    //메두사가 보는 방향에 따라 범위가 달라진다. 즉, d1 부터 d2까지가 시선의 범위(ex. 0 ~ 2)
    static class Node {
        int x, y;
        int d1, d2;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Node(int x, int y, int d1, int d2) {
            this.x = x;
            this.y = y;
            this.d1 = d1;
            this.d2 = d2;
        }
    }
}