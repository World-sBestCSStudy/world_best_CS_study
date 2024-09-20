import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ_15686_치킨배달 {
	static int N, M, min, houseSize, srcSize;
	static List<int[]> house, src, tgt;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		house = new ArrayList<>();
		src = new ArrayList<>();
		tgt = new ArrayList<>();

		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		// 2차원 배열 입력을 받으면서 집&치킨집에 대해서 자료구조를 정리
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				int n = Integer.parseInt(st.nextToken());
				if (n==1) house.add(new int[] {i, j});	// 집
				else if (n==2) src.add(new int[] {i,j});	// 치킨집 전체
			}
		}
		
		// 풀이
		min = Integer.MAX_VALUE;
		houseSize = house.size();
		srcSize = src.size();
		
		// 조합
		comb(0,0);
		System.out.println(min);
	}
	
	static void comb(int srcIdx, int tgtIdx) {
		// 기저 조건
		if (tgtIdx == M) {
			for (int i = 0; i < houseSize; i++) {
				int dist = Integer.MAX_VALUE;
				int[] h = house.get(i);
				
				for (int j = 0; j < M; j++) {
					int[] c = tgt.get(j);
					dist = Math.min(dist, Math.abs(h[0]-c[0]) + Math.abs(h[1]-c[1]));
				}
			
				sum += dist;
			}
			
			min = Math.min(min, sum);
			return;
		}
		
		if (srcIdx == srcSize) return;
		
		tgt.add(src.get(srcIdx));	
		comb(srcIdx + 1, tgtIdx + 1);
		tgt.remove(src.get(srcIdx));
		comb(srcIdx + 1, tgtIdx);	
	}

}