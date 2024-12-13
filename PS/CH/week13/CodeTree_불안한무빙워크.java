
//CodeTree_불안한무빙워크
import java.io.*;
import java.util.*;

public class Main {
	static int N, K;
	static List<Integer> rail;
	static List<Boolean> user;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		rail = new ArrayList<>();
		user = new ArrayList<>();

		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N * 2; i++) {
			rail.add(Integer.parseInt(st.nextToken()));
		}
		for (int i = 0; i < N; i++) {
			user.add(false);
		}

		int r = 0;

		do {
			r += 1;

			rotateRail();
			moveUser();
			ride();

		} while (isSafe());
		System.out.println(r);

	}

	public static void moveUser() {
		boolean[] temp = new boolean[N + 1];

		for (int i = N - 1; i >= 0; i--) {
			if (!user.get(i))
				continue;

			int next = i + 1;

			if (next == N) {
				temp[next] = true;
				continue;
			}

			if (!temp[next] && rail.get(next) > 0) {
				int s = rail.get(next) - 1;
				rail.remove(next);
				rail.add(next, s);
				temp[next] = true;
			} else {
				temp[i] = true;
			}
		}

		user.clear();
		for (int i = 0; i < N; i++) {			
			user.add(temp[i]);
		}

	}

	public static void ride() {
		int s = rail.get(0);

		if (s == 0)
			return;

		rail.remove(0);
		rail.add(0, s - 1);

		user.remove(0);
		user.add(0, true);

	}

	public static boolean isSafe() {
		int count = 0;

		for (int i : rail) {
			count += i <= 0 ? 1 : 0;
		}

		return count < K;
	}

	public static void rotateRail() {
		int next = rail.remove(rail.size() - 1);
		rail.add(0, next);

		user.remove(user.size() - 1);
		user.add(0, false);
	}
}
