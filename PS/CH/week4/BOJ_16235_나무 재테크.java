//BOJ_16235_나무 재테크

import java.io.*;
import java.util.*;

public class Main {
    static int N, M, K;
    static int[][] board;
    static int[][] energy;
    static int[] dx = {1, 0, -1, -1, -1, 0, 1, 1}, dy = {-1, -1, -1, 0, 1, 1, 1, 0};
    static PriorityQueue<Node> trees;
    static int[][] deadTrees;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        board = new int[N][N];
        energy = new int[N][N];
        deadTrees=new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < N; i++) Arrays.fill(energy[i], 5);

        trees = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.age, o2.age));

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken())-1;
            int y = Integer.parseInt(st.nextToken())-1;
            int z = Integer.parseInt(st.nextToken());
            trees.offer(new Node(x, y, z));
        }

        for(int i =0; i<K; i++){
            spring();
            fall();
            winter();
        }
        System.out.println(trees.size());
    }

    public static void spring() {
        List<Node> temp = new ArrayList<>();
        while(!trees.isEmpty()){
            Node node = trees.poll();

            if (energy[node.x][node.y] >= node.age) {
                energy[node.x][node.y] -= node.age;
                node.age += 1;
                temp.add(node);
            } else {
                deadTrees[node.x][node.y] +=(node.age / 2);
            }
        }

        trees.addAll(temp);
    }



    public static void fall() {
        List<Node> temp = new ArrayList<>();
        for(Node node : trees){
            if (node.age % 5 != 0) continue;

            for (int i = 0; i < 8; i++) {
                int nx = node.x + dx[i];
                int ny = node.y + dy[i];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;

                temp.add(new Node(nx,ny,1));
            }
        }
        trees.addAll(temp);
    }

    public static void winter(){
        for(int i =0; i<N; i++){
            for(int j =0; j<N; j++){
                energy[i][j] += board[i][j];
                energy[i][j] += deadTrees[i][j];
                deadTrees[i][j] = 0;
            }
        }
    }

    static class Node {
        int x, y, age;

        public Node(int x, int y, int age) {
            this.x = x;
            this.y = y;
            this.age = age;
        }
    }

}