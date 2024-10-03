// BOJ_2842_집배원 한상덕

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] dx = {-1, -1, -1, 0, 1, 1, 1, 0}, dy = {-1, 0, 1, 1, 1, 0, -1, -1};
    static int n;
    static char[][] board;
    static List<Integer> height=new ArrayList<>();
    static int[][] diff;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        board = new char[n][n];
        diff = new int[n][n];

        Node start = null;
        int target = 0;
        for (int i = 0; i < n; i++) {
            char[] temp = br.readLine().toCharArray();
            for (int j = 0; j < n; j++) {
                board[i][j] = temp[j];
                if (board[i][j] == 'P') start = new Node(i, j);
                if (board[i][j] == 'K') target++;
            }
        }

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                diff[i][j] = Integer.parseInt(st.nextToken());
                if(!height.contains(diff[i][j])) height.add(diff[i][j]);
            }
        }

        Collections.sort(height);
        int left = 0, right = 0;
        int answer = Integer.MAX_VALUE;
        while(right<height.size() && left<height.size()){
            boolean result = bfs(start,target, height.get(left), height.get(right));
            if(result){
                answer =Math.min(answer, Math.abs(height.get(right) - height.get(left)));
                left++;
            }else{
                right++;
            }
        }
        System.out.println(answer);

    }

    public static boolean bfs(Node start, int target, int min, int max) {
        if (diff[start.x][start.y] < min || diff[start.x][start.y] > max) {
            return false;
        }
        Queue<Node> q = new LinkedList<>();
        boolean[][] v = new boolean[n][n];

        q.offer(new Node(start.x, start.y));
        v[start.x][start.y] = true;
        int count = 0;

        while (!q.isEmpty()) {
            Node node = q.poll();
            for (int i = 0; i < 8; i++) {
                int nx = node.x + dx[i];
                int ny = node.y + dy[i];

                if (nx < 0 || ny < 0 || nx >= n || ny >= n) continue;

                if(!v[nx][ny] && diff[nx][ny]>= min && diff[nx][ny]<=max){
                    v[nx][ny] = true;
                    q.offer(new Node(nx,ny));
                    if(board[nx][ny] =='K') count++;

                }
            }
        }
        return count == target;
    }

    static class Node {
        int x, y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }

}
