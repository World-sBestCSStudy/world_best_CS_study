//CodeTree_놀이기구 탑승
import java.io.*;
import java.util.*;

public class Main {

	static int N;
	static int[][] board, empty, like;
	static Map<Integer, Set<Integer>> map;
	static int[] dx = { -1, 0, 1, 0 }, dy = { 0, -1, 0, 1 };
	static int[] scores= {0,1,10,100,1000};

	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());

		board = new int[N][N];
		empty = new int[N][N];
		like = new int[N][N];
		map = new HashMap<Integer, Set<Integer>>();

		for (int i = 0; i < N * N; i++) {
			st = new StringTokenizer(br.readLine());
			int me = Integer.parseInt(st.nextToken());
			map.put(me, new HashSet<>());

			for (int f = 0; f < 4; f++) {
				int friend = Integer.parseInt(st.nextToken());
				map.get(me).add(friend);
			}
			
			ride(me);

		}
		
		int answer = 0;
		
		for(int i =0; i<N; i++) {
			for(int j =0; j<N; j++) {
				
				int me = board[i][j];
				int count = 0;
				
				for (int d = 0; d < 4; d++) {
					int nx = i + dx[d];
					int ny = j + dy[d];

					if (nx < 0 || ny < 0 || nx >= N || ny >= N)
						continue;
						
					int friend = board[nx][ny];
					if (map.get(me).contains(friend)) {
						count+=1;
					}
				}
				
				answer += scores[count];
				
			}
		}
		
		System.out.println(answer);
		
	

	}

	public static void ride(int me) {

		for (int i = 0; i < N; i++)
			Arrays.fill(like[i], 0);
		for (int i = 0; i < N; i++)
			Arrays.fill(empty[i], 0);

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {

				for (int d = 0; d < 4; d++) {
					int nx = i + dx[d];
					int ny = j + dy[d];

					if (nx < 0 || ny < 0 || nx >= N || ny >= N)
						continue;

					int friend = board[nx][ny];
					if (friend == 0) {
						empty[i][j] += 1;
					} else if (map.get(me).contains(friend)) {
						like[i][j] += 1;
					}
				}

			}
		}

		int maxLike = -1;
		int maxEmpty = -1;
		int[] xy = new int[2];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if(board[i][j]>0)continue;
				
				if (like[i][j] > maxLike) {
					xy[0] = i;
					xy[1] = j;
					maxLike = like[i][j];
					maxEmpty = empty[i][j];
				} else if (like[i][j] == maxLike && empty[i][j] > maxEmpty) {
					xy[0] = i;
					xy[1] = j;
					maxEmpty = empty[i][j];
				}

			}
		}
		
	
		board[xy[0]][xy[1]] = me;
		
	}

}
