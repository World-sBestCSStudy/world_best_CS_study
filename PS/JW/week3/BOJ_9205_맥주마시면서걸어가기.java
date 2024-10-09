import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_9205_맥주마시면서걸어가기 {
	static int T, N, V;
	static int[][] input;
	static ArrayList<ArrayList<Integer>> adjList;
	
	public static void main(String[] args) throws Exception{

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++) {
		
			N = Integer.parseInt(br.readLine());
			V = N + 2; // 편의점 + 집 + 공연장
			
			input = new int[V][2];
			adjList = new ArrayList<>();
			for (int i = 0; i < V; i++) {
				adjList.add(new ArrayList<>());
			}
			
			// 입력 처리
			for (int i = 0; i < V; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				for (int j = 0; j < 2; j++) {
					input[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			// 입력으로부터 인접행렬을 만든다.
			for (int i = 0; i < V; i++) {
				int vy = input[i][0];
				int vx = input[i][1];
				
				for (int j = 0; j < V; j++) {
					if( i == j ) continue;
					
					int ty = input[j][0];
					int tx = input[j][1];
					int dis = Math.abs(ty-vy) + Math.abs(tx-vx); // 맨하튼 거리
					
					if( dis <= 1000 ) { // 맥주를 마시면서 갈 수 있는 거리
						adjList.get(i).add(j);
					}
				}
			}
			
			// 집 -> 락페스티벌
			bfs();
			
		}
	}
	
	static void bfs() {
		Queue<Node> queue = new ArrayDeque<>();
		boolean[] visit = new boolean[V];
		
		// 시작점(집) 추가
		visit[0] = true;
		queue.offer(new Node(0));
		
		while(!queue.isEmpty()) {
			Node node = queue.poll();
			if( node.v == V-1) { // 종점 락페스트벌에 도착하면 종료
				System.out.println("happy");
				return;
			}
			
			for (int v : adjList.get(node.v)) {
				
				if( visit[v] ) continue;
				visit[v] = true;
				queue.offer(new Node(v));
			}
		}
		System.out.println("sad"); // 여기까지 왔으면 종점 락페스티벌에 도착하지 못함.
	}
	
	static class Node{
		int v;
		Node(int v){
			this.v = v;
		}
	}
}