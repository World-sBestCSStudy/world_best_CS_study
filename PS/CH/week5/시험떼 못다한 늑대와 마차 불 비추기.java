//시험떼 못다한 늑대와 마차 불 비추기

import java.io.*;
import java.util.*;

public class Main {
    static int N;
    static int[] dxE = {-1, -1, -1, 0, 1, 1, 1, 0}, dyE = {-1, 0, 1, 1, 1, 0, -1, -1};
    static int[] dxF = {-1, 0, 1, 0}, dyF = {0, 1, 0, -1};
    static Node car;
    static int[][] wolf;

    public static void main(String[] args) throws IOException {

        N = 11;
        car = new Node(N / 2, N / 2);

        wolf = new int[N][N];
        wolf[3][4] = 1;
        wolf[3][5] = 1;
        wolf[1][8] = 1;
        wolf[1][9] = 1;
        wolf[6][9] = 1;
        wolf[7][5] = 1;
        wolf[8][3] = 1;
        wolf[4][4] = 1;
        light();
    }

    public static void light() {
        int[][] range = {{0, 2}, {2, 4}, {4, 6}, {6, 8}};
        for (int i = 0; i < 4; i++) {
            int[] ri = range[i];

            bfs(ri[0], ri[1]);

        }
    }

    public static void bfs(int d1, int d2) {
        Queue<Node> q = new LinkedList<>();
        Queue<Node> remover = new LinkedList<>();
        boolean[][] v = new boolean[N][N];
        int[][] board = new int[N][N];
        q.offer(new Node(car.x, car.y));
        v[car.x][car.y] = true;
        while (!q.isEmpty()) {
            Node node = q.poll();

            for (int i = d1; i <= d2; i++) {
                int dir = i % 8;
                int nx = node.x + dxE[dir];
                int ny = node.y + dyE[dir];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;

                if (!v[nx][ny]) {
                    v[nx][ny] = true;
                    board[nx][ny] = 1;
                    q.offer(new Node(nx, ny));


                    if (wolf[nx][ny] == 1) {
                        if (dir >= 0 && dir <= 2 && car.x > nx && car.y > ny) remover.offer(new Node(nx, ny, 0, 1));
                        if (dir >= 0 && dir <= 2 && car.y == ny) remover.offer(new Node(nx, ny, 1, 1));
                        if (dir >= 0 && dir <= 2 && car.x > nx && car.y < ny) remover.offer(new Node(nx, ny, 1, 2));

                        if(dir>=2&& dir<=4  && car.x>nx && car.y<ny) remover.offer(new Node(nx,ny,2,3));
                        if(dir>=2&& dir<=4  && car.x==nx) remover.offer(new Node(nx,ny,3,3));
                        if(dir>=2&& dir<=4  && car.x<nx && car.y<ny) remover.offer(new Node(nx,ny,3,4));

                        if(dir>=4&& dir<=6  && car.x<nx && car.y<ny) remover.offer(new Node(nx,ny,4,5));
                        if(dir>=4&& dir<=6  && car.y==ny) remover.offer(new Node(nx,ny,5,5));
                        if(dir>=4&& dir<=6  && car.x<nx&& car.y>ny ) remover.offer(new Node(nx,ny,5,6));


                        if((dir==6 || dir==7 || dir==0)  && car.x<nx && car.y>ny) remover.offer(new Node(nx,ny,6,7));
                        if((dir==6 || dir==7 || dir==0) && car.x==nx) remover.offer(new Node(nx,ny,7,7));
                        if((dir==6 || dir==7 || dir==0) && car.x>nx&& car.y>ny ) remover.offer(new Node(nx,ny,7,8));


                    }

                }
            }
        }


        while (!remover.isEmpty()) {
            Node node = remover.poll();
            for (int i = node.d1; i <= node.d2; i++) {
                int dir = i % 8;
                int nx = node.x + dxE[dir];
                int ny = node.y + dyE[dir];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;

                if (v[nx][ny]) {
                    board[nx][ny] = 0;
                    remover.offer(new Node(nx, ny, node.d1, node.d2));
                }
            }
        }
        print(board);
    }

    public static void print(int[][] board) {
        for (int i = 0; i < N; i++) System.out.println(Arrays.toString(board[i]));
        System.out.println();
    }

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


