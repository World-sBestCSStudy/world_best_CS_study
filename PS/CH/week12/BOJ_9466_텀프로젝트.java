//BOJ_9466_텀프로젝트
import java.io.*;
import java.util.*;

public class Main {
	
	static int N,count;
	static int[] team;
	static boolean[] v;
	static boolean[]cycle;
	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder answer = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		for(int t= 0;t <T; t++) {
			int N = Integer.parseInt(br.readLine());
			team = new int[N+1];
			v=new boolean[N+1];
			cycle=new boolean[N+1];
			
			st=new StringTokenizer(br.readLine());
			for(int i =1; i<=N; i++) {
				team[i] = Integer.parseInt(st.nextToken());
			}
			
			count=0;
			
			for(int i =1; i<N+1; i++) {
				
				search(i);
				
				
			}

			answer.append(N-count).append("\n");
			
			
		}
		System.out.println(answer);
		

	}
	
	public static void search(int x) {
		if(cycle[x]) return;
		
		//cycle occur
		if(v[x]) {
			count++;
			cycle[x] = true;
			
			
		}
		v[x] = true;
		search(team[x]);
		v[x] = false;
		cycle[x] = true;
		
	}

}
   