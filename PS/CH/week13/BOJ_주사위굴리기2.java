//BOJ_주사위굴리기2
import java.io.*;
import java.util.*;

public class Main {

	static int N, M, K, answer;
	static int[][] board;
	static int[] number = { 2, 1, 5, 6, 4, 3 };
	static int[] dx = { 0, 1, 0, -1 }, dy = { 1, 0, -1, 0 };
	static Node dice = new Node(0, 0, 0);

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		board = new int[N][M];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		answer = 0;

		for (int i = 0; i < K; i++) {
			sol();
		}
		System.out.println(answer);

	}

	public static void sol() {
		int nx = dice.x + dx[dice.dir];
		int ny = dice.y + dy[dice.dir];

		if (nx < 0 || ny < 0 || nx >= N || ny >= M) {
			dice.dir = (dice.dir + 2) % 4;
			nx = dice.x + dx[dice.dir];
			ny = dice.y + dy[dice.dir];
		}

		if (dice.dir == 0) {
			rotateRight();
		} else if (dice.dir == 1) {
			rotateBottom();
		} else if (dice.dir == 2) {
			rotateLeft();
		} else if (dice.dir == 3) {
			rotateUp();
		}

		int cost = board[nx][ny];
		if (number[3] > cost) {
			dice.dir = (dice.dir + 1) % 4;
		} else if (number[3] < cost) {
			dice.dir = (dice.dir + 3) % 4;
		} else {

		}

		dice.x = nx;
		dice.y = ny;

		answer += getScore(board[nx][ny]);

	}

	public static int getScore(int target) {
		Queue<Node> q = new LinkedList<>();
		boolean[][] v = new boolean[N][M];
		int count = 1;

		q.offer(new Node(dice.x, dice.y));
		v[dice.x][dice.y] = true;

		while (!q.isEmpty()) {
			Node node = q.poll();

			for (int i = 0; i < 4; i++) {
				int nx = node.x + dx[i];
				int ny = node.y + dy[i];
				if (nx < 0 || ny < 0 || nx >= N || ny >= M)
					continue;

				if (!v[nx][ny] && board[nx][ny] == target) {
					v[nx][ny] = true;
					count++;
					q.offer(new Node(nx, ny));
				}

			}
		}

		return count * target;

	}

	public static void rotateRight() {
		int[] temp = new int[6];
		temp[1] = number[1];
		temp[3] = number[3];
		temp[4] = number[4];
		temp[5] = number[5];

		number[1] = temp[4];
		number[3] = temp[5];
		number[4] = temp[3];
		number[5] = temp[1];
	}

	public static void rotateLeft() {
		int[] temp = new int[6];
		temp[1] = number[1];
		temp[3] = number[3];
		temp[4] = number[4];
		temp[5] = number[5];

		number[1] = temp[5];
		number[3] = temp[4];
		number[4] = temp[1];
		number[5] = temp[3];
	}

	public static void rotateUp() {
		int[] temp = new int[6];
		temp[0] = number[0];
		temp[1] = number[1];
		temp[2] = number[2];
		temp[3] = number[3];

		number[0] = temp[1];
		number[1] = temp[2];
		number[2] = temp[3];
		number[3] = temp[0];
	}

	public static void rotateBottom() {
		int[] temp = new int[6];
		temp[0] = number[0];
		temp[1] = number[1];
		temp[2] = number[2];
		temp[3] = number[3];

		number[0] = temp[3];
		number[1] = temp[0];
		number[2] = temp[1];
		number[3] = temp[2];
	}

	static class Node {
		int x, y, dir;

		public Node(int x, int y, int dir) {
			this.x = x;
			this.y = y;
			this.dir = dir;
		}

		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}
