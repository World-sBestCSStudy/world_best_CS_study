
//CodeTree_승자독식 모노폴리
import java.io.*;
import java.util.*;

public class Main {
	static int N, M, K;
	static int[][] board;
	static Node[] player;
	static int[][][] dirs;
	static int[][] owner;
	static int[][] turn;
	static int[] dx = { -1, 1, 0, 0 }, dy = { 0, 0, -1, 1 };
	static boolean[] isDead;
	static int[][] race;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		board = new int[N][N];

		player = new Node[M + 1];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
				if (board[i][j] > 0)
					player[board[i][j]] = new Node(i, j);
			}
		}

		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= M; i++) {
			int d = Integer.parseInt(st.nextToken()) - 1;
			player[i].dir = d;
		}

		dirs = new int[M + 1][4][4];
		for (int i = 1; i <= M; i++) {
			for (int j = 0; j < 4; j++) {
				st = new StringTokenizer(br.readLine());

				for (int d = 0; d < 4; d++) {
					dirs[i][j][d] = Integer.parseInt(st.nextToken()) - 1;
				}

			}
		}

		owner = new int[N][N];
		turn = new int[N][N];
		isDead = new boolean[M + 1];
		race = new int[N][N];

		for (int i = 1; i <= M; i++) {
			buy(i);
		}

		int count = 0;
		while (true) {
			count++;
			move();
			update();
			if (isWin() || count > 1000)
				break;
			
		}

		System.out.println(count > 1000 ? -1 : count);

	}

	public static void move() {

		for (int i = 0; i < N; i++)
			Arrays.fill(race[i], Integer.MAX_VALUE);

		for (int i = 1; i <= M; i++) {
			if (isDead[i])
				continue;

			Node node = player[i];
			int[] xy = raceGround(i, node.x, node.y, node.dir);

			if (xy[0] != -1 && xy[1] != -1) {
				node.x = xy[0];
				node.y = xy[1];
				node.dir = xy[2];
				continue;
			}

			xy = moveMyZone(i, node.x, node.y, node.dir, i);

			if (xy[0] != -1 && xy[1] != -1) {
				node.x = xy[0];
				node.y = xy[1];
				node.dir = xy[2];
				buy(i);
				continue;
			}

		}
	}

	public static boolean isWin() {
		for (int i = 2; i <= M; i++) {
			if (!isDead[i])
				return false;
		}
		return true;
	}

	public static void update() {
		turnOver();
		boolean[] alive = new boolean[M + 1];

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (race[i][j] == Integer.MAX_VALUE)
					continue;
				if (race[i][j] > 0) {
					alive[race[i][j]] = true;
					buy(race[i][j]);
				}
			}
		}

		for (int i = 1; i <= M; i++) {
			if (!alive[i]) {
				isDead[i] = true;
			}
		}
	}

	public static int[] raceGround(int id, int x, int y, int dir) {
		for (int i = 0; i < 4; i++) {
			int nx = x + dx[dirs[id][dir][i]];
			int ny = y + dy[dirs[id][dir][i]];

			if (nx < 0 || ny < 0 || nx >= N || ny >= N)
				continue;

			if (owner[nx][ny] == 0) {
				race[nx][ny] = Math.min(race[nx][ny], id);
				return new int[] { nx, ny, dirs[id][dir][i] };
			}

		}
		return new int[] { -1, -1 };
	}

	public static int[] moveMyZone(int id, int x, int y, int dir, int condition) {
		for (int i = 0; i < 4; i++) {
			int nx = x + dx[dirs[id][dir][i]];
			int ny = y + dy[dirs[id][dir][i]];

			if (nx < 0 || ny < 0 || nx >= N || ny >= N)
				continue;

			if (owner[nx][ny] == condition) {
				race[nx][ny] = id;
				return new int[] { nx, ny, dirs[id][dir][i] };
			}

		}

		return new int[] { -1, -1 };
	}

	public static void buy(int id) {
		Node p = player[id];
		turn[p.x][p.y] = K;
		owner[p.x][p.y] = id;
	}

	public static void turnOver() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (turn[i][j] > 0 && --turn[i][j] == 0) {
					owner[i][j] = 0;
				}
			}
		}
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
