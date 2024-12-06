
//BOJ_17144_미세먼지 안녕!
import java.io.*;
import java.util.*;

public class Main {
	static int N, M, T;
	static int[][] board;
	static List<Node> upCmd;
	static List<Node> bottomCmd;
	static int[] dx = { 0, -1, 0, 1 }, dy = { 1, 0, -1, 0 };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());

		board = new int[N][M];
		upCmd = new ArrayList<>();
		bottomCmd = new ArrayList<>();

		Node up=null, bottom = null;
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());

				if (board[i][j] == -1 && up == null) {
					up = new Node(i, j);
				} else if (board[i][j] == -1 && bottom == null) {
					bottom = new Node(i, j);
				}
			}
		}
		
		
		for(int i =0; i<T; i++) {
			spread();
			clean(up.x,up.y,1);
			clean(bottom.x,bottom.y,-1);
		}
	
		int answer = 0;
		for(int i =0; i<N; i++) {
			for(int j =0; j<M; j++) {
				if(board[i][j]>0) answer+=board[i][j];
			}
		}
		System.out.println(answer);

	}
	
	public static void clean(int x, int y, int w) {
		Queue<Integer> q = new LinkedList<>();
		
		int dir = 0;
		int nx = x + dx[dir];
		int ny = y+dy[dir];
		while(true) {
			if(x == nx && y== ny) break;
			
			q.offer(board[nx][ny]);
			board[nx][ny] =0;
			
			nx += dx[dir];
			ny += dy[dir];
			
			
			if(nx<0||ny<0||nx>=N||ny>=M) {
				nx -= dx[dir];
				ny-=dy[dir];
				
				dir = (dir+w+4) %4;
				
				nx += dx[dir];
				ny += dy[dir];
			}	
		}
		
		
		dir = 0;
		nx = x + dx[dir];
		ny = y+dy[dir];
		while(true) {			
			
			nx += dx[dir];
			ny += dy[dir];
			
			
			if(nx<0||ny<0||nx>=N||ny>=M) {
				nx -= dx[dir];
				ny-=dy[dir];
				
				dir = (dir+w+4) %4;
				
				nx += dx[dir];
				ny += dy[dir];
			}
			if(x == nx && y== ny) break;
			board[nx][ny] = q.poll();
		}
		
		
	}

	public static void spread() {
		Queue<Node> q = new LinkedList<>();
		Queue<Node> temp = new LinkedList<>();

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (board[i][j] > 0) {
					q.offer(new Node(i, j, board[i][j]));
					board[i][j] = 0;
				}
			}
		}

		while (!q.isEmpty()) {
			Node node = q.poll();

			int count = 0;
			for (int i = 0; i < 4; i++) {
				int nx = node.x + dx[i];
				int ny = node.y + dy[i];

				if (nx < 0 || ny < 0 || nx >= N || ny >= M || board[nx][ny] == -1)
					continue;

				count++;
				temp.offer(new Node(nx, ny));
			}

			int sd = node.cost / 5;
			while (!temp.isEmpty()) {
				Node sNode = temp.poll();
				board[sNode.x][sNode.y] += sd;
			}
			board[node.x][node.y] += node.cost - sd * count;

		}
	}
	
	public static void print() {
		for(int i =0; i<N; i++) System.out.println(Arrays.toString(board[i]));
		System.out.println();
	}

	static class Node {
		int x, y, cost;

		public Node(int x, int y, int cost) {
			this.x = x;
			this.y = y;
			this.cost = cost;
		}

		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}
