import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class BOJ_2493_탑 {
	static Deque<int[]> stack = new ArrayDeque();
	static int N;
	static StringBuilder sb = new StringBuilder();
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		for (int i = 1; i <= N; i++) {
			int height = Integer.parseInt(st.nextToken());
			
			// stack에서 현재 높이보다 작은 애들은 제거, 큰 것이 나오면 그 큰 것의 번호를 출력해주기
			while ( !stack.isEmpty() ) {
				// 자신보다 큰 경우
				if ( stack.peek()[1] >= height ) {
					sb.append(stack.peek()[0]).append(" ");
					break;
				}
				stack.pop();
			}
			
			// 내가 제일 높으면
			if ( stack.isEmpty() ) {
				sb.append("0 ");
			}
			
			stack.push(new int[] {i, height});
		}
		
		System.out.println(sb);

	}

}