//BOJ_2539_모자이크 
import java.io.*;
import java.util.*;

public class Main {
	static int N, M, S, K;
	static List<Node> points;

	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		S = Integer.parseInt(br.readLine());

		K = Integer.parseInt(br.readLine());

		points = new ArrayList<>();

		for (int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine());

			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());

			points.add(new Node(x, y));
		}

		Collections.sort(points, new Comparator<Node>() {
			public int compare(Node o1, Node o2) {

				return o1.y - o2.y;

			}

		});

		int left = 1;
		int right = Math.max(N, M);

		int answer = lower(left, right);
		System.out.println(answer);

	}

	public static int lower(int left, int right) {
		while (left <= right) {
			int mid = (left + right) / 2;

			if (attach(mid)) {
				right = mid - 1;

			} else {
				left = mid + 1;
			}
		}

		return left;
	}

	public static boolean attach(int size) {
		int count = 1;
		int prev = points.get(0).y;
		if (points.get(0).x > size)
			return false;

		for (Node node : points) {
			if (node.x > size)
				return false;

			if (node.y > prev + size) {
				count++;
				prev = node.y;
			}
		}

		return count <= S;
	}

	static class Node {
		int x, y;

		public Node(int x, int y) {
			this.x = x;
			this.y = y;

		}
	}

}
