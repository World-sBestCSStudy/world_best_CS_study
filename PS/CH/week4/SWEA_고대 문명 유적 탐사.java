//SWEA_고대 문명 유적 탐사

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N, K, M, mIdx;

    static int[][] board;
    static int[] wall;
    static int[] dx = {1, 0, -1, -1, -1, 0, 1, 1}, dy = {-1, -1, -1, 0, 1, 1, 1, 0};

    static int[] dxW = {-1, 0, 1, 0}, dyW = {0, -1, 0, 1};
    static int[] round;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;


        st = new StringTokenizer(br.readLine());
        N = 5;
        K = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        mIdx = 0;

        board = new int[N][N];
        wall = new int[M];
        round = new int[K];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < M; i++) {
            wall[i] = Integer.parseInt(st.nextToken());
        }

        StringBuilder answer = new StringBuilder();
        for(int i =0; i<K; i++){
            if(!sol(i)) break;
        }

        for(int i : round){
            if(i==0) break;
            answer.append(i).append(" ");
        }
        System.out.println(answer);


    }

    public static boolean sol(int id) {
        int maxCount = 0;
        Node node = null;
        int minRotate = 4;

        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {

                for (int r = 1; r <= 3; r++) {
                    rotateRight(j, i);

                    boolean[][] v = new boolean[N][N];
                    int count = 0;
                    for (int x = 0; x < N; x++) {
                        for (int y = 0; y < N; y++) {
                            if (!v[x][y]) {
                                int tempCount = findPiece(v, x, y);
                                if (tempCount >= 3) count += tempCount;
                            }
                        }
                    }

                    if (count > maxCount) {
                        maxCount = count;
                        minRotate = r;
                        node = new Node(j, i);

                    } else if (count == maxCount && minRotate > r) {
                        minRotate = r;
                        node = new Node(j, i);
                    }
                }

                for (int r = 1; r <= 3; r++) {
                    rotateLeft(j, i);
                }
            }
        }

        if (maxCount == 0) return false;

        for(int i =1; i<=minRotate; i++){
            rotateRight(node.x,node.y);
        }

        while (true) {
            int count = 0;
            boolean[][] v= new boolean[N][N];
            for (int i = 0; i<N; i++){
                for(int j =0; j<N; j++){
                    if(!v[i][j] && findPiece(v,i,j)>=3){
                        count+=getPiece(i,j);
                    }
                }
            }
            if(count==0) break;
            round[id] += count;
            fillBoard();

        }
        return true;
    }

    public static void fillBoard(){
        for(int i =0; i<N; i++){
            for(int j =N-1; j>=0; j--){
                if(board[j][i]==0){
                    board[j][i] = wall[mIdx++];
                }
            }
        }
    }

    public static void rotateRight(int x, int y) {
        List<Integer> temp = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            temp.add(board[nx][ny]);
        }


        for (int i = 0; i < 8; i++) {
            int nx = x + dx[(i + 2) % 8];
            int ny = y + dy[(i + 2) % 8];
            board[nx][ny] = temp.get(i);
        }


    }

    public static void rotateLeft(int x, int y) {
        List<Integer> temp = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            int nx = x + dx[(i + 4) % 8];
            int ny = y + dy[(i + 4) % 8];
            temp.add(board[nx][ny]);
        }

        for (int i = 0; i < 8; i++) {
            int nx = x + dx[(i + 2) % 8];
            int ny = y + dy[(i + 2) % 8];
            board[nx][ny] = temp.get(i);
        }
    }


    public static int findPiece(boolean[][] v, int x, int y) {
        Queue<Node> q = new LinkedList<>();
        q.offer(new Node(x, y));
        v[x][y] = true;
        int id = board[x][y];
        int count = 1;
        while (!q.isEmpty()) {
            Node node = q.poll();

            for (int i = 0; i < 4; i++) {
                int nx = node.x + dxW[i];
                int ny = node.y + dyW[i];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;

                if (!v[nx][ny] && board[nx][ny] == id) {
                    v[nx][ny] = true;
                    count++;
                    q.offer(new Node(nx, ny));
                }

            }

        }
        return count;
    }

    public static int getPiece(int x, int y) {
        Queue<Node> q = new LinkedList<>();
        q.offer(new Node(x, y));
        boolean[][] v=new boolean[N][N];
        int id = board[x][y];
        int count = 1;

        v[x][y] = true;
        board[x][y]=0;

        while (!q.isEmpty()) {
            Node node = q.poll();

            for (int i = 0; i < 4; i++) {
                int nx = node.x + dxW[i];
                int ny = node.y + dyW[i];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;

                if (!v[nx][ny] && board[nx][ny] == id) {
                    v[nx][ny] = true;
                    board[nx][ny]=0;
                    count++;
                    q.offer(new Node(nx, ny));
                }

            }

        }
        return count;
    }

    public static void print() {
        for (int i = 0; i < N; i++) System.out.println(Arrays.toString(board[i]));
        System.out.println();
    }

    static class Node {
        int x, y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}