import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N, K;
    static boolean[][] visit = new boolean[500001][2];

    public static void main(String[] args) throws Exception {
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        System.out.println(bfs());
    }

    static int bfs() {
        Deque<Integer> queue = new ArrayDeque<>();
        queue.add(N);
        int depth = 0;
        visit[N][0] = true;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int num = queue.poll();
                if (num == K || visit[K][depth % 2]) {
                    return depth;
                }

                int next = num - 1;
                if (canGo(next) && !visit[next][(depth + 1) % 2]) {
                    visit[next][(depth + 1) % 2] = true;
                    queue.add(next);
                }

                next = num + 1;
                if (canGo(next) && !visit[next][(depth + 1) % 2]) {
                    visit[next][(depth + 1) % 2] = true;
                    queue.add(next);
                }

                next = num * 2;
                if (canGo(next) && !visit[next][(depth + 1) % 2]) {
                    visit[next][(depth + 1) % 2] = true;
                    queue.add(next);
                }
            }

            K += ++depth;

            if (K > 500000)
                return -1;
        }

        return -1;
    }

    static boolean canGo(int num) {
        return num >= 0 && num <= 500000;
    }
}
