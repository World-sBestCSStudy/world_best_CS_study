//CodeTree_청소는즐거워
import java.io.*;
import java.util.*;

public class Main {
	static int N, answer;
	static int[][] board;
	static Queue<Node> cmd;
	static int[] dx = { 0, 1, 0, -1 }, dy = { 1, 0, -1, 0 };
	static int[] dxDust = { 0, -2, -1, -1, -1, 2, 1, 1, 1, 0 }, dyDust = { -2, 0, -1, 0, 1, 0, -1, 0, 1, -1 };
	static int[] rate = { 5, 2, 10, 7, 1, 2, 10, 7, 1, 0 };

	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());
		board = new int[N][N];
		cmd = new LinkedList<>();

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		boolean[][] v = new boolean[N][N];
		v[0][0] = true;
		setCmd(v, 0, 0, 0);

		answer = 0;

		while (!cmd.isEmpty()) {
			Node node = cmd.poll();
			int x = node.x;
			int y = node.y;
			int dir = node.dir;
			int dust = board[x][y];
			int sum = 0;

			board[x][y] = 0;

			for (int i = 0; i < 10; i++) {

				if (dir == 0) {
					x += dxDust[i] * -1;
					y += dyDust[i] * -1;
				} else if (dir == 1) {
					x += dyDust[i] * -1;
					y += dxDust[i] * -1;
				} else if (dir == 2) {
					x += dxDust[i];
					y += dyDust[i];
				} else if (dir == 3) {
					x += dyDust[i];
					y += dxDust[i];
				}

				float r = (float) rate[i] / 100;
				int move = (int) (dust * r);

				sum += move;

				if (x < 0 || y < 0 || x >= N || y >= N) {
					if (i == 9)
						answer += dust - sum;
					else
						answer += move;
					continue;
				}

				if (i == 9)
					board[x][y] += dust - sum;
				else
					board[x][y] += move;

			}

		}

		System.out.println(answer);

	}

	public static void setCmd(boolean[][] v, int x, int y, int dir) {
		if (x == N / 2 && y == N / 2) {
			return;
		}

		int nx = x + dx[dir];
		int ny = y + dy[dir];

		if (nx < 0 || ny < 0 || nx >= N || ny >= N || v[nx][ny]) {
			dir = (dir + 1) % 4;
			nx = x + dx[dir];
			ny = y + dy[dir];
		}

		v[nx][ny] = true;
		setCmd(v, nx, ny, dir);
		cmd.offer(new Node(x, y, (dir + 2) % 4));
	}

	static class Node {
		int x, y, dir;

		public Node(int x, int y, int dir) {
			this.x = x;
			this.y = y;
			this.dir = dir;
		}
	}

}
