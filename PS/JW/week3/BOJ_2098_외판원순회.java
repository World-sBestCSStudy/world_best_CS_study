import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_2098_외판원순회 {

	static int N, allMask, INF = 999_999_999;
	static int[][] W;
	static int[][] memoi;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		W = new int[N][N];
		
		allMask = ( 1 << N ) - 1;	// N이 5이면 : 100000 ==> -1 ==> 11111 (5자리가 모두 1인 마스크)
		memoi = new int[N][allMask];
		
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				W[i][j] = Integer.parseInt(st.nextToken());
			}
		}
	
		// 0번째 도시에서 출발
		System.out.println(tsp(0, 1));
	
		for (int i = 0; i < N; i++) {
			Arrays.toString(memoi[i]);
		}
	}
	
	static int tsp(int idx, int mask) {
		// 기저조건
		if (mask == allMask) {	// 모든 도시를 방문한 상태이므로, 처음(0번째 도시)으로 되돌아가는 비용
			if (W[idx][0] == 0) return INF;
			else return W[idx][0];
		}
		
		// 도시를 더 방문해야 함
		if (memoi[idx][mask] != 0) return memoi[idx][mask];
		
		// 처음
		memoi[idx][mask] = INF;
		
		// 방문하지 않은 도시를 방문 (재귀)
		for (int i = 0; i < N; i++) {
			// 갈 수 없거나, 이미 방문한 경우는 skip
			if (W[idx][i] == 0 || (mask & 1 << i) != 0) continue;
			
			memoi[idx][mask] = Math.min(memoi[idx][mask], tsp(i, mask | 1 << i) + W[idx][i]);
		}
		
		return memoi[idx][mask];
	}

}