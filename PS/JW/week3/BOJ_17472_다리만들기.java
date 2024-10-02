import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ_17472_다리만들기 {

	static int N, M, min;
	static int[][] map;

	// dfs
	static boolean[][] visit;
	static int[] dy = { -1, 1, 0, 0 };
	static int[] dx = { 0, 0, -1, 1 };

	static int V; // 정점
	static PriorityQueue<Edge> pqueue = new PriorityQueue<>((e1, e2) -> e1.cost - e2.cost);

	// 서로소
	static int[] parent;

	static class Edge {
		int v1, v2, cost;

		Edge(int v1, int v2, int cost) {
			this.v1 = v1;
			this.v2 = v2;
			this.cost = cost;
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		map = new int[N][M];
		visit = new boolean[N][M];
		// map input <= 섬은 -1로
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken()) * (-1); // 바다: 0 섬:-1로 초기화
			}
		}

		// dfs
		int num = 1; // 정점 1번부터
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (map[i][j] == -1 && !visit[i][j]) {
					// 새로운 섬(정점)
					dfs(i, j, num);
					num++;
				}
			}
		}

		// 정점 수
		V = num - 1; // dfs 후 마지막 증가분 고려

		parent = new int[V + 1]; // 0 dummy
		makeSet();

		// map[][] 로부터 간선 정보 계산
		// 가로로 쭉
		hr();
		// 세로로 쭉
		vr();

		// 크루스칼 풀이
		int cnt = 0;
		while (!pqueue.isEmpty()) {
			Edge e = pqueue.poll();
			// 사이클 여부
			if (union(e.v1, e.v2)) { // 선택해도 된다.
				cnt++;
				min += e.cost; // 최소값 누적 계산
			}

			if (cnt == V - 1)
				break;
		}

		if (cnt != V - 1 || min == 0)
			min = -1;
		System.out.println(min);
	}

	static void addEdge(int v1, int v2, int cost) {
		// 뒤져서 중복인 항목을 제거하는 것이 손해가 될 수도 있고 이득이 될 수도 있다.
		boolean same = false;

		for (Edge edge : pqueue) {
			// 같은 정점을 연결하는 간선이면 최소값으로 갱신
			if (edge.v1 == v1 && edge.v2 == v2) {
				same = true;
				edge.cost = Math.min(edge.cost, cost);
				break;
			}
		}
		// 새로운 간선이면 추가
		if (!same)
			pqueue.offer(new Edge(v1, v2, cost));
	}

	static void hr() {
		for (int i = 0; i < N; i++) { // 행으로 내려오면서
			int prev = 0;
			int curr = 0;
			int v1 = 0;
			int v2 = 0;
			int cost = 0;

			// 옆으로 쭉 가면서
			for (int j = 0; j < M; j++) {
				curr = map[i][j];
				if (prev == 0 && curr != 0) { // 0 -> 0 아닌 좌표(바다 ->섬, 최초 시작점 -> 섬

					if (v1 == 0)
						v1 = curr;
					else {
						// 간선 발생
						v2 = curr;
						if (cost > 1) {
							// 간선 추가 (v1, v2, cost)
							addEdge(v1, v2, cost);
						}
						v1 = v2;
						v2 = 0;
						cost = 0;
					}
				} else if (v1 != 0 && curr == 0) { // 섬에서 시작 아직 바다
					cost++;
				}

				prev = curr;
			}
		}
	}

	static void vr() {
		for (int i = 0; i < M; i++) { // 옆으로 이동하면서
			int prev = 0;
			int curr = 0;
			int v1 = 0;
			int v2 = 0;
			int cost = 0;

			// 열에서 밑으로 내려가면서
			for (int j = 0; j < N; j++) {
				curr = map[j][i];
				if (prev == 0 && curr != 0) { // 0 -> 0 아닌 좌표(바다 ->섬, 최초 시작점 -> 섬

					if (v1 == 0)
						v1 = curr;
					else {
						// 간선 발생
						v2 = curr;
						if (cost > 1) {
							// 간선 추가 (v1, v2, cost)
							addEdge(v1, v2, cost);
						}
						v1 = v2;
						v2 = 0;
						cost = 0;
					}
				} else if (v1 != 0 && curr == 0) { // 섬에서 시작 아직 바다
					cost++;
				}

				prev = curr;
			}
		}
	}

	// makeSet
	static void makeSet() {
		for (int i = 1; i <= V; i++) {
			parent[i] = i;
		}
	}

	// findSet
	static int findSet(int x) {
		if (parent[x] == x)
			return x;
		else
			return parent[x] = findSet(parent[x]);
	}

	// union
	static boolean union(int x, int y) {
		int px = findSet(x);
		int py = findSet(y);

		if (py == px)
			return false; // 사이클 존재
		if (px < py)
			parent[py] = px;
		else
			parent[px] = py;
		return true;
	}

	// dfs
	static void dfs(int y, int x, int num) {
		visit[y][x] = true;
		map[y][x] = num;

		for (int d = 0; d < 4; d++) {
			int ny = y + dy[d];
			int nx = x + dx[d];
			if (ny < 0 || nx < 0 || ny >= N || nx >= M || visit[ny][nx])
				continue;
			if (map[ny][nx] == -1)
				dfs(ny, nx, num);
		}
	}
}