import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int A, B, C;
    static Set<Integer> ans = new TreeSet<>();
    static boolean[][][] visit;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {
        StringTokenizer st = new StringTokenizer(br.readLine());
        A = Integer.parseInt(st.nextToken());
        B = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        visit = new boolean[A + 1][B + 1][C + 1];

        bfs();

        for (Integer num : ans) {
            sb.append(num).append(" ");
        }
        System.out.println(sb);
    }

    static void bfs() {
        Deque<int[]> queue = new ArrayDeque<>();
        visit[0][0][C] = true;
        queue.add(new int[]{0, 0, C});

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int cA = cur[0], cB = cur[1], cC = cur[2];

            //A물통이 비어 있는 상황
            if (cA == 0) {
                ans.add(cC);
            }

            //A통에서 물을 다른 곳으로 옮기는 경우
            if (cA != 0) {
                //B로 옮기는 경우
                int move = Math.min(cA, B - cB);
                int nA = cA - move;
                int nB = cB + move;
                if (!visit[nA][nB][cC]) {
                    visit[nA][nB][cC] = true;
                    queue.add(new int[]{nA, nB, cC});
                }

                //C로 옮기는 경우
                move = Math.min(cA, C - cC);
                nA = cA - move;
                int nC = cC + move;
                if (!visit[nA][cB][nC]) {
                    visit[nA][cB][nC] = true;
                    queue.add(new int[]{nA, cB, nC});
                }
            }

            //B통에서 물을 다른 곳으로 옮기는 경우
            if (cB != 0) {
                //A로 옮기는 경우
                int move = Math.min(cB, A - cA);
                int nB = cB - move;
                int nA = cA + move;
                if (!visit[nA][nB][cC]) {
                    visit[nA][nB][cC] = true;
                    queue.add(new int[]{nA, nB, cC});
                }

                //C로 옮기는 경우
                move = Math.min(cB, C - cC);
                nB = cB - move;
                int nC = cC + move;
                if (!visit[cA][nB][nC]) {
                    visit[cA][nB][nC] = true;
                    queue.add(new int[]{cA, nB, nC});
                }

            }

            //C통에서 물을 다른 곳으로 옮기는 경우
            if (cC != 0) {
                //A로 옮기는 경우
                int move = Math.min(cC, A - cA);
                int nC = cC - move;
                int nA = cA + move;
                if (!visit[nA][cB][nC]) {
                    visit[nA][cB][nC] = true;
                    queue.add(new int[]{nA, cB, nC});
                }

                //B로 옮기는 경우
                move = Math.min(cC, B - cB);
                nC = cC - move;
                int nB = cB + move;
                if (!visit[cA][nB][nC]) {
                    visit[cA][nB][nC] = true;
                    queue.add(new int[]{cA, nB, nC});
                }
            }
        }
    }
}
