//SWEA_마법의숲탐색
import java.util.*;
import java.io.*;

public class Main {
    static int R, C, K;
    static int[][] board;
    static int[][] door;
    static int[][] area;
    static int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;


        st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken()) + 3;
        C = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        board = new int[R][C];
        door = new int[R][C];
        area = new int[R][C];


        List<Node> fairy = new ArrayList<>();
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken());
            int dir = Integer.parseInt(st.nextToken());
            fairy.add(new Node(1, y - 1, dir));
        }


        int number = 1;
        int answer = 0;
        for (Node node : fairy) {
            int result = sol(node, number++);
            answer+=result;
//            System.out.println(result);
//            print();
//            printDoor();

        }
        System.out.println(answer);


    }

    public static int sol(Node node, int number) {
//        System.out.println(node.x+" "+node.y);
        while (true) {
            if (isEnd(node)) break;
            if(down(node)) continue;

            if(rotateLeft(node)) continue;

            if(rotateRight(node)) continue;

            break;
        }

        door[node.x + dx[node.dir]][node.y + dy[node.dir]] = 1;

        area[node.x][node.y] = number;
        for (int i = 0; i < 4; i++) {
            int nx = node.x + dx[i];
            int ny = node.y + dy[i];
            area[nx][ny] = number;
        }


        if (node.x < 4) {
            for (int i = 0; i < R; i++) Arrays.fill(board[i], 0);
            for (int i = 0; i < R; i++) Arrays.fill(door[i], 0);
            for (int i = 0; i < R; i++) Arrays.fill(area[i], 0);
            return 0;
        } else {
//            printArea();
//            System.out.println();
//            printDoor();
            return bfs(node.x, node.y, number);
        }

    }


    public static int bfs(int x, int y, int number) {
        Queue<Node> q = new LinkedList<>();
        q.offer(new Node(x, y, number, false));
        boolean[][] v = new boolean[R][C];
        v[x][y] = true;

        int result = 0;
        while (!q.isEmpty()) {
            Node node = q.poll();
            result = Math.max(result, node.x);

            for (int i = 0; i < 4; i++) {
                int nx = node.x + dx[i];
                int ny = node.y + dy[i];
                if (nx < 3 || ny < 0 || nx >= R || ny >= C || area[nx][ny] == 0) continue;

                if (!v[nx][ny] && area[nx][ny] == node.number) {
                    v[nx][ny]= true;
                    if (door[nx][ny] == 1) {
                        q.offer(new Node(nx, ny, node.number, true));
                    } else {
                        q.offer(new Node(nx, ny, node.number, false));
                    }
                }
                else if (!v[nx][ny] && node.cheat) {
                    v[nx][ny] = true;
                    if(door[nx][ny]==1){
                        q.offer(new Node(nx, ny, area[nx][ny], true));
                    }else{
                        q.offer(new Node(nx, ny, area[nx][ny], false));
                    }


                }


            }
        }
        return result-2;

    }

    //초기 [x : -1], [y : c]
    public static boolean down(Node node) {
        int x = node.x;
        int y = node.y;
        boolean result = true;

        while (true) {
            if (x + 2 < R && board[x + 1][y - 1] == 0 && board[x + 2][y] == 0 && board[x + 1][y + 1] == 0) {
                x++;
            } else {
                break;
            }
        }

        setBoard(node.x, node.y, 0);
        setBoard(x, y, 1);

        result = (node.x==x) && (node.y==y);
        node.x = x;
        node.y = y;

       return !result;
    }

    //아! 회전하고 내려갈수 있어야한다.
    public static boolean rotateLeft(Node node) {
        int x = node.x;
        int y = node.y;
        boolean result = true;
        if (y - 2 >= 0 && board[x][y - 2] == 0 && board[x - 1][y - 1] == 0 && board[x + 1][y - 1] == 0) {
            y -= 1;

            if (!(x + 2 < R && board[x + 1][y - 1] == 0 && board[x + 2][y] == 0)) return false;


            y+=1;
            setBoard(node.x, node.y, 0);

            y -= 1;
            setBoard(x, y, 1);
            result = (node.x==x)&&(node.y==y);
            node.x = x;
            node.y = y;
            node.dir = ((node.dir - 1) + 4) % 4;
        }

        return !result;
    }


    public static boolean rotateRight(Node node) {
        int x = node.x;
        int y = node.y;
        boolean result = true;
        if (y + 2 < C && board[x][y + 2] == 0 && board[x - 1][y + 1] == 0 && board[x + 1][y + 1] == 0) {
            y += 1;

            if (!(x + 2 < R  && board[x + 2][y] == 0 && board[x + 1][y + 1] == 0)) return false;

            y-=1;
            setBoard(node.x, node.y, 0);

            y += 1;
            setBoard(x, y, 1);


            result = (node.x==x)&&(node.y==y);

            node.x = x;
            node.y = y;
            node.dir = (node.dir + 1) % 4;

        }
        return !result;
    }

    public static void setBoard(int x, int y, int flag) {
        if (x >= 0 && y >= 0) board[x][y] = flag;
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (nx < 0 || ny < 0 || nx >= R || ny >= C) continue;

            board[nx][ny] = flag;
        }
    }

    public static boolean isEnd(Node node) {
        return node.x + 1 == R - 1;
    }

    public static boolean isStart(Node node) {

        for (int i = 0; i < 4; i++) {
            int nx = node.x + dx[i];
            int ny = node.y + dy[i];
            if (nx < 0 || ny < 0 || nx >= R || ny >= C) return false;
        }
        return true;
    }


    public static void print() {
        for (int[] i : board) System.out.println(Arrays.toString(i));
        System.out.println();
    }

    public static void printDoor() {
        for (int[] i : door) System.out.println(Arrays.toString(i));
        System.out.println();
    }

    public static void printArea() {
        for (int[] i : area) System.out.println(Arrays.toString(i));
        System.out.println();
    }


    static class Node {
        int x, y, dir, number;
        boolean cheat;

        public Node(int x, int y, int number, boolean cheat) {
            this.x = x;
            this.y = y;
            this.number=number;
            this.cheat = cheat;
        }

        public Node(int x, int y, int dir) {
            this.x = x;
            this.y = y;
            this.dir = dir;
        }
    }
}