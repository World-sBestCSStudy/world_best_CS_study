import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ_4485_녹색젤다 {
	static int N;
	static int[][] map, cost;
	static PriorityQueue<Edge> pq = new PriorityQueue<>((e1, e2) -> e1.c - e2.c);
	
	// delta
	static int[] dy = { -1, 1, 0, 0 };
	static int[] dx = { 0, 0, -1, 1 };
	static final int INF = Integer.MAX_VALUE;	// 충분히 큰 값으로 cost 배열 초기화
	static StringBuilder sb = new StringBuilder();
	static class Edge {
		int y, x, c;

		public Edge(int y, int x, int c) {
			this.y = y;
			this.x = x;
			this.c = c;
		}
		
	}
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int t = 1;
		while (true) {
			N = Integer.parseInt(br.readLine());
			if (N==0) break;
			
			map = new int[N][N];
			cost = new int[N][N];	// 시작점부터 현재 좌표까지의 최소비용을 관리
			
			// 입력처리 + cost 초기화
			for (int i = 0; i < N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
					cost[i][j] = INF;
				}
			}
			
			// 다익스트라 풀이
			dijkstra();
			
			sb.append("Problem ").append(t).append(": ").append(cost[N-1][N-1]).append("\n");
		}
		System.out.println(sb);
	}
	
	static void dijkstra() {
		cost[0][0] = map[0][0];
		pq.offer(new Edge(0, 0, map[0][0]));
		
		// pq에서 비용이 가장 작은 Edge를 선택해서 갈 수 있는 새로운 좌표의 cost[][]를 줄일 수 있으면 갱신
		while (!pq.isEmpty()) {
			Edge e = pq.poll();	// 꺼낸 녀석에서부터 갈 수 있는 새로운 좌표 획득
			// -> 시작점에서 획득된 좌표로 이동할 때 최소비용과 꺼낸 녀석을 거쳐서 가는 최소비용을 비교
			for (int d = 0; d < 4; d++) {
				int ny = e.y + dy[d];
				int nx = e.x + dx[d];
				
				if (ny < 0 || nx < 0 || ny >= N || nx >= N) continue;
				
				// 비용 비교
				// 현재 기준 시작점 (0,0) -> 고려 좌표 (ny, nx)로 가는 최소비용은 cost[ny][nx]에 있음
				// 꺼낸 좌표를 거쳐가는 비용 e.c + map[ny][nx]
				if (e.c + map[ny][nx] < cost[ny][nx]) {
					cost[ny][nx] = e.c + map[ny][nx];
					pq.offer(new Edge(ny, nx, cost[ny][nx]));
				}
			}
		}
	}

}