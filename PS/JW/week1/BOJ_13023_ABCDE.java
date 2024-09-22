import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_13023_ABCDE {
	static int N, M;
	static boolean[][] friends;
	static boolean[] visit;
	static boolean done;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
	
		friends = new boolean[N][N];
		visit = new boolean[N];
		
		// 입력
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			friends[a][b] = friends[b][a] = true;
		}
		
		for (int i = 0; i < N; i++) {
			visit[i] = true;
			dfs(i, 0);	
			if (done) {
				System.out.println(1);
				return;
			}
			visit[i] = false;	
		}
		
		System.out.println(0);
	}
	
	static void dfs(int num, int cnt) {	
		// 기저 조건
		if (done) return;
		
		if (cnt == 4) {	
			done = true;
			return;
		}
		
		for (int i = 0; i < N; i++) {
			if ( !friends[num][i] || visit[i]) continue;	
			visit[i] = true;
			dfs(i, cnt + 1);
			visit[i] = false;	
		}
	}

}